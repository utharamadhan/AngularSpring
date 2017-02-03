package id.base.app.service.audittrail;

import id.base.app.CSVUtil;
import id.base.app.IAuditTrailConstant;
import id.base.app.ILookupGroupConstant;
import id.base.app.SystemConstant;
import id.base.app.dao.audittrail.IAuditTrailDAO;
import id.base.app.dao.lookup.ILookupDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.report.ReportUtils;
import id.base.app.service.MaintenanceService;
import id.base.app.util.DateTimeFunction;
import id.base.app.util.FileManager;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.util.dao.SearchOrder.Sort;
import id.base.app.valueobject.LogAuditTrail;
import id.base.app.valueobject.Lookup;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softtech.kismiss.constant.ReportFactory;
import com.softtech.kismiss.main.KismissReport;
import com.softtech.kismiss.model.SubTitle;

@Service
@Transactional
public class AuditTrailService implements 
     MaintenanceService<LogAuditTrail> , IAuditService {

	@Autowired
	private  IAuditTrailDAO auditTrailDAO;
	@Autowired
	private ILookupDAO lookupDAO;
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	private Logger logger=LoggerFactory.getLogger(AuditTrailService.class);
    
    public AuditTrailService(){};
    
    public AuditTrailService(IAuditTrailDAO auditTrailDAO){
    	this.auditTrailDAO = auditTrailDAO;
    }
  
	public PagingWrapper<LogAuditTrail> findAll(int startNo, int offset)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	public LogAuditTrail findById(Long id) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveOrUpdate(LogAuditTrail anObject) throws SystemException {
		this.auditTrailDAO.create(anObject);
	}

	public void delete(Long[] objectPKs) throws SystemException {
		// TODO Auto-generated method stub
	}	

	public void createAudit(LogAuditTrail logAuditTrail) {
		if(logAuditTrail!=null){
			this.auditTrailDAO.create(logAuditTrail);
		}
	}

	public void createAuditBulk(List<LogAuditTrail> logAuditTrails) {
		if (logAuditTrails != null) {
			for(LogAuditTrail l:logAuditTrails){
				this.auditTrailDAO.create(l);
			}
		}
	}

	public LogAuditTrail findByReferencesId(Long referencesPK)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	public PagingWrapper<LogAuditTrail> findAllByFilter(int startNo,
			int offset, List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		if (order == null) {
			order = new ArrayList<SearchOrder>();
		}
		if (order.size()==0){
			order.add(new SearchOrder(LogAuditTrail.ACCESS_TIME, Sort.DESC));
		}
		return auditTrailDAO.findAuditTrailByCode(startNo, offset, filter, order);
	}
	
	public void createAudit(int code,String descr,String userName,String status,String clientHost)throws SystemException{
		Date d=DateTimeFunction.getCurrentDate();
		logger.info("DateTime [{}]",DateTimeFunction.date2String(d, SystemConstant.SYSTEM_DATE_TIME_MASK));
		LogAuditTrail logAuditTrail=new LogAuditTrail(d,  IAuditTrailConstant.LOG_GROUP.get(code), code, descr, userName, status, clientHost, code);
		auditTrailDAO.create(logAuditTrail);
	}
	
	@Override
	public void createAuditWithSubCode(int code, String descr, String userName, String status, String clientHost, int subCode) throws SystemException {
		Date d=DateTimeFunction.getCurrentDate();
		logger.info("DateTime [{}]",DateTimeFunction.date2String(d, SystemConstant.SYSTEM_DATE_TIME_MASK));
		LogAuditTrail logAuditTrail = new LogAuditTrail(d, IAuditTrailConstant.LOG_GROUP.get(code), code, descr, userName, status, clientHost, subCode);
		//auditTrailDAO.create(logAuditTrail);
	}

	@Override
	public List<LogAuditTrail> findObjects(Long[] objectPKs)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream getCsvBackupFile(OutputStream os, Integer code)
			throws SystemException {
		 CSVUtil p = new CSVUtil(os);
		 try {
			 List<?> logs=auditTrailDAO.findAuditTrailByCode(code);
			 LogAuditTrail logAuditTrailTO = null;
			 p.printlnComment("BOF");
			 p.printlnComment("Audit trail backup file @" + (DateTimeFunction.getCurrentDate()));
			 p.printlnComment("");
			 p.printlnComment("group, time, username, status, description");

			 for (int i = 0; i < logs.size(); i++) {
				 logAuditTrailTO = (LogAuditTrail) logs.get(i);
				 p.print(logAuditTrailTO.getLogGroup());
				 p.print(DateTimeFunction.date2String(logAuditTrailTO.getAccessTime(), SystemConstant.SYSTEM_TIME_MASK));
				 p.print(logAuditTrailTO.getUserName());
				 p.print(logAuditTrailTO.getStatus());
				 p.println(logAuditTrailTO.getDescr());
			 }
			 p.printlnComment("EOF");
			 return os;
		 }
		 catch (SystemException e) {
			 logger.error(e.getMessage(), e);
			 throw e;
		 }
	}

	@Override
	public List<LogAuditTrail> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String exportReport(Integer logGroupCode) throws JRException, FileNotFoundException {
		KismissReport report = KismissReport.getInstance();
		Lookup module = lookupDAO.findByCode(logGroupCode.toString(), ILookupGroupConstant.LOG_GROUP);
		
		JasperPrint jasperPrint1=null;
		Date exportDate = new Date();
		String timestamp = DateFormatUtils.ISO_DATE_FORMAT.format(exportDate);
		String filename = "LogAuditTrail-" + module.getDescr() + timestamp + "-" + System.currentTimeMillis();
		String auditFileName = filename.replace(" ", "");
		String dir = SystemConstant.AUDIT_TRAIL_EXPORT_DIR;
		
		 //dynamic report
		List<Object> subs = new ArrayList<Object>();
		subs.add(new SubTitle(module.getDescr()==null?messageSource.getMessage("label.audit.trail.export.module.name", null, Locale.ENGLISH):messageSource.getMessage("label.audit.trail.export.module.name", null, Locale.ENGLISH)+module.getDescr()));
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(ReportFactory.TITLE, messageSource.getMessage("label.audit.trail.export.title", null, Locale.ENGLISH));
		params.put(ReportFactory.REPORT_NAME, auditFileName);
		params.put(ReportFactory.SUB_TITLE, subs);
		
		report.setSubTitleFontSize(8);
		report.setSubTitleHeight(15);
		
		List<LogAuditTrail> logs = auditTrailDAO.findAuditTrailByCodeSortDateDesc(logGroupCode);
		String[] visibleFields = new String[]{LogAuditTrail.USERNAME, LogAuditTrail.DESCRIPTION, LogAuditTrail.ACCESS_TIME, LogAuditTrail.STATUS};
		report.setVisibleField(visibleFields);
		//dynamic report
		try {
			if(logs.size()>0){
				jasperPrint1 = report.getJasperPrint(LogAuditTrail.class, logs, params);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if(jasperPrint1 != null){
			List<JasperPrint> listJP = new ArrayList<>(); 
			listJP.add(jasperPrint1);
	       
			FileManager.createDir(dir);
			String path = dir + auditFileName+"." + ReportUtils.XLS_EXTENSION;
	        try {
				report.generateAnnotatedXlsFiles(listJP, path, params);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}else{
			auditFileName = "fail";
		}
		return auditFileName;
	}
}
