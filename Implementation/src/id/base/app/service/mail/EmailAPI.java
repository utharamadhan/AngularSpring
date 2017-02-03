package id.base.app.service.mail;

import java.util.List;

public interface EmailAPI {
	
	public void sendMail(List<String> toIds, String fromId, String subject, String emailContent, String[] attachmentLocation) throws Exception;
	
}
