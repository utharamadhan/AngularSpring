package id.base.app.controller;

import id.base.app.exception.SystemException;
import id.base.app.report.ReportUtils;
import id.base.app.rest.RestConstant;
import id.base.app.service.MaintenanceService;
import id.base.app.service.audittrail.IAuditService;
import id.base.app.valueobject.LogAuditTrail;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(RestConstant.RM_AUDIT)
public class AuditTrailController extends SuperController<LogAuditTrail>{
	
	@Autowired
	@Qualifier("auditTrailService")
	private MaintenanceService<LogAuditTrail> maintenanceService;
	
	@Autowired
	private IAuditService service;

	@Override
	public MaintenanceService<LogAuditTrail> getMaintenanceService() {
		return this.maintenanceService;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/createBulk")
	@ResponseStatus( HttpStatus.OK )
	public void createAuditBulk(@RequestParam(value="auditTrails") String auditTrailsJson) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			if(StringUtils.isNotEmpty(auditTrailsJson)){
				List<LogAuditTrail> logAuditTrails = mapper.readValue(auditTrailsJson, new TypeReference<List<LogAuditTrail>>(){});
				service.createAuditBulk(logAuditTrails);
			}
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("error finding your data",e);
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/createAudit")
	@ResponseStatus(HttpStatus.OK)
	public void createAudit(@RequestParam(value="code") int code,@RequestParam(value="descr") String descr,@RequestParam(value="userName") String userName,@RequestParam(value="status") String status,@RequestParam(value="clientHost") String clientHost)throws SystemException{
		service.createAudit(code, descr, userName, status, clientHost);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/createAuditWithSubCode")
	@ResponseStatus(HttpStatus.OK)
	public void createAuditWithSubCode(@RequestParam(value="code") int code,@RequestParam(value="descr") String descr,@RequestParam(value="userName") String userName,@RequestParam(value="status") String status,@RequestParam(value="clientHost") String clientHost, @RequestParam(value="subCode") int subCode)throws SystemException{
		service.createAuditWithSubCode(code, descr, userName, status, clientHost, subCode);
	}	
	
	@Deprecated
	private String generateLogAuditTrail(final Integer logGroupCode) {
		/*SpecificRestCaller<String> br = new SpecificRestCaller<String>(RestConstant.REPORT_SERVICE, RestConstant.RM_AUDIT, String.class);
		String fileName = br.executeGet(new PathInterfaceRestCaller() {
			
			@Override
			public String getPath() {
				return "/export/{logGroupCode}";
			}
			
			@Override
			public Map<String, Object> getParameters() {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("logGroupCode", logGroupCode);
				return map;
			}
		});
		return fileName;*/
		return "deprecated";
	}

	@Deprecated
	private void retrieveAuditTrailExcel(HttpServletResponse response, String fileName) {
		/*FileRestCaller frc = new FileRestCaller(RestConstant.REPORT_SERVICE+"/stream/excelReport/AUDIT_TRAIL_EXPORT_DIR/"+fileName, fileName);
		try {
			frc.exportXlsFile(response);
		} catch (SystemException e) {
			e.printStackTrace();
		}*/
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/export/{logGroupCode}")
	@ResponseBody
	public String exportReport(@PathVariable(value="logGroupCode") final Integer logGroupCode, HttpServletResponse response) {
		String fileName = generateLogAuditTrail(logGroupCode);
		fileName = fileName + "." + ReportUtils.XLS_EXTENSION;
		return fileName;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/exportExisting/{filename}")
	public void exportExistingReport(@PathVariable(value="filename") final String fileName, HttpServletResponse response) {
		retrieveAuditTrailExcel(response, fileName);
	}

	@Override
	public LogAuditTrail validate(LogAuditTrail anObject)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
}
