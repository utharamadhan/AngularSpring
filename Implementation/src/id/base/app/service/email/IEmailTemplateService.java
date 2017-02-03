package id.base.app.service.email;

import id.base.app.exception.SystemException;
import id.base.app.service.MaintenanceService;
import id.base.app.valueobject.email.EmailTemplate;

public interface IEmailTemplateService extends MaintenanceService<EmailTemplate>{
	
	public EmailTemplate findByCode(String templateCode) throws SystemException;

}
