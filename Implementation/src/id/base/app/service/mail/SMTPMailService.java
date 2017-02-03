package id.base.app.service.mail;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Qualifier("SMTPMailService")
public class SMTPMailService implements EmailAPI{
	
	private static Logger LOGGER = LoggerFactory.getLogger(SMTPMailService.class);
	
	@Autowired
	private MailSender mailSender;
	
	private static final String UTF_8 = "utf-8";
	private static final String TEXT_HTML = "text/html";

	@Override
	public void sendMail(List<String> toIds, String fromId, String subject, String emailContent, String[] attachmentLocation) throws Exception {
		LOGGER.debug("Start sending email using SMTP");
		MimeMessage mimeMessage = ((JavaMailSender) mailSender).createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, UTF_8);
		helper.setTo(toIds.toArray(new String[toIds.size()]));
		helper.setSubject(subject);
		helper.setFrom(fromId);
		
		//set email content
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailContent, TEXT_HTML);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        //set email attachment (if exists)
        if(attachmentLocation != null && attachmentLocation.length > 0){
        	 for(int i=0;i<attachmentLocation.length;i++){
    	        try{
    	        	Path testPath = Paths.get(attachmentLocation[i]);
    	        	if(Files.exists(testPath)){
    	        		messageBodyPart = new MimeBodyPart();
    	        		DataSource source = new FileDataSource(testPath.toString());
    	        		messageBodyPart.setDataHandler(new DataHandler(source));
    	        		messageBodyPart.setFileName(testPath.getFileName().toString());
    	        		multipart.addBodyPart(messageBodyPart);
    	        	}
    	        }catch(Exception e){
    	        	LOGGER.error(e.getMessage(), e);
    	        }
        	 }
        }

        mimeMessage.setContent(multipart);
		((JavaMailSender) mailSender).send(mimeMessage);
	}

}
