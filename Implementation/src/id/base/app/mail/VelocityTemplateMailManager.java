package id.base.app.mail;
import id.base.app.SystemConstant;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.mail.MailManager;
import id.base.app.util.DateTimeFunction;
import id.base.app.util.NumericFunction;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class VelocityTemplateMailManager<T> implements MailManager<T>  {
	 private JavaMailSender 	mailSender;
	 private VelocityEngine 	velocityEngine;
     static Logger logger = LoggerFactory.getLogger(VelocityTemplateMailManager.class);
	
	public void sendMail(final String to, final Long mailTemplate, final T objModel, boolean throwException) throws SystemException {
		sendMail(to,null,null,mailTemplate,objModel, throwException);
	};
     
	public void sendMail(final String to,final Long mailTemplate ,final T objModel){
		sendMail(to, mailTemplate, objModel, false);
	}
	
    @Override
    public void sendMail(final String from, final String to, final String subject, final String content) {
    	try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
	    	    public void prepare(MimeMessage mimeMessage) throws Exception {
//	    	    	 EncryptionUtils smimeUtils = EncryptionManager.getEncryptionUtils(EncryptionManager.SMIME);
//	    	         // load the S/MIME keystore from the given file.
//	    	         char[] smimePw = new String("hello world").toCharArray();
//	    	         EncryptionKeyManager smimeKeyMgr = smimeUtils.createKeyManager();
//	    	         smimeKeyMgr.loadPublicKeystore(new FileInputStream(new File("./id.p12")),
//	    	    smimePw);
//	    	         // get the S/MIME public key for encryption
//	    	         java.security.Key smimeKey = smimeKeyMgr.getPublicKey("Eric's Key");
//	    	         // encrypt the message
//	    	         MimeMessage smimeEncryptedMsg = smimeUtils.encryptMessage(mailSession, newMessage, smimeKey);
		            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		            message.setFrom(from);
		    		message.setTo(to);
		            message.setSubject(subject);
		            message.setText(content);
	 	        }
			};
 	      this.mailSender.send(preparator);
   	 	} catch (Exception e) {
   	 		logger.error(e.getMessage(),e);
   	 		throw new SystemException(new ErrorHolder("error.email.access"));
  		}
    }
    
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	@Override
	public void sendMail(final String to, final String subject, final Long mailTemplate,
			final T objModel, boolean throwException) throws SystemException {
		sendMail(to, subject, null, mailTemplate, objModel, throwException);
	}

	@Override
	public void sendMail(final String to, final String subject, final String from,
			final Long mailTemplate, final T objModel, boolean throwException)
			throws SystemException {
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
	    	    public void prepare(MimeMessage mimeMessage) throws Exception {
//	    	    	 EncryptionUtils smimeUtils = EncryptionManager.getEncryptionUtils(EncryptionManager.SMIME);
//	    	         // load the S/MIME keystore from the given file.
//	    	         char[] smimePw = new String("hello world").toCharArray();
//	    	         EncryptionKeyManager smimeKeyMgr = smimeUtils.createKeyManager();
//	    	         smimeKeyMgr.loadPublicKeystore(new FileInputStream(new File("./id.p12")),
//	    	    smimePw);
//	    	         // get the S/MIME public key for encryption
//	    	         java.security.Key smimeKey = smimeKeyMgr.getPublicKey("Eric's Key");
//	    	         // encrypt the message
//	    	         MimeMessage smimeEncryptedMsg = smimeUtils.encryptMessage(mailSession, newMessage, smimeKey);

		            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		            message.setFrom(from);
		    		message.setTo(to);
		    		message.setSubject(subject);
		            Map<String, Object> model = new HashMap<String, Object>();
		            VelocityContext context = new VelocityContext(model);
		            context.put("dateTool", DateTimeFunction.class);
		            context.put("systemConstant", SystemConstant.class);
		            context.put("numericTool", NumericFunction.class);
	 	        }
			};
 	      this.mailSender.send(preparator);
   	 	} catch (Exception e) {
   	 		logger.error(e.getMessage(),e);
   	 		
   	 		if (throwException) {
   	 			throw new SystemException(new ErrorHolder("error.email.access"));
   	 		}
  		}
		
	}
		
}
