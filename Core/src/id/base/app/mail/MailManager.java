package id.base.app.mail;

import id.base.app.exception.SystemException;


public interface MailManager<T> {
	/**
	 * Sending mail, catching any exception found and not rethrowing it
	 * @param to
	 * @param mailTemplate
	 * @param model
	 * @throws SystemException
	 */
	void sendMail(String to,Long mailTemplate,T model) throws SystemException;
	
	/**
	 * Sending mail and allowing the method to not throwing exception if it found one
	 * @param to
	 * @param mailTemplate
	 * @param model
	 * @param throwException
	 * @throws SystemException
	 */
	void sendMail(String to,Long mailTemplate,T model, boolean throwException) throws SystemException;
	void sendMail(String from, String to, String subject, String content) throws SystemException;
	void sendMail(String to, String subject, Long mailTemplate, T objModel,
			boolean throwException) throws SystemException;
	void sendMail(String to, String subject, String from, Long mailTemplate, T objModel,
			boolean throwException) throws SystemException;
	
	
}
