package id.base.app.service.audittrail;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.LogAuditTrail;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.List;

import net.sf.jasperreports.engine.JRException;

public interface IAuditService {

	public void createAudit(LogAuditTrail logAuditTrail) ;
	public void createAuditBulk(List<LogAuditTrail> logAuditTrails) ;
	public void createAudit(int code,String descr,String userName,String status,String clientHost)throws SystemException;
	public void createAuditWithSubCode(int code,String descr,String userName,String status,String clientHost, int subCode)throws SystemException;
	public OutputStream getCsvBackupFile(OutputStream os, Integer code) throws SystemException;
	public String exportReport(Integer logGroupCode) throws JRException, FileNotFoundException;
	
}
