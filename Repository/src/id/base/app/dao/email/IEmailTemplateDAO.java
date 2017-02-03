package id.base.app.dao.email;

import id.base.app.IBaseDAO;
import id.base.app.exception.SystemException;
import id.base.app.valueobject.email.EmailTemplate;

public interface IEmailTemplateDAO extends IBaseDAO<EmailTemplate> {
	
	public EmailTemplate findByCode(String templateCode) throws SystemException;

}
