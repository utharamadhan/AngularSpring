package id.base.app.service.mail;

import id.base.app.SystemParameter;
import id.base.app.valueobject.email.sparkpost.SparkPostEmailWrapper;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Qualifier("SparkPostMailService")
public class SparkPostMailService implements EmailAPI{
	
	private static Logger LOGGER = LoggerFactory.getLogger(SparkPostMailService.class);
	
	private HttpHeaders headers = new HttpHeaders();
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private MailSender mailSender;
	
	private String resolveRecipient (List<String> toIds) {
		if(toIds != null) {
			StringBuilder recipients = new StringBuilder();
			boolean firstRecipient = true;
			for(String recipient : toIds) {
				if(firstRecipient) {
					recipients.append(recipient);
				}else{					
					recipients.append(";" + recipient);
				}
			}
			return recipients.toString();
		}
		return "";
	}

	@Override
	public void sendMail(List<String> toIds, String fromId, String subject, String emailContent, String[] attachmentLocation) throws Exception {
		LOGGER.debug("Start sending email using SMTP");
		try{
			for(String toId : toIds) {				
				SparkPostEmailWrapper wrapper = new SparkPostEmailWrapper(toId, subject, emailContent, emailContent);
				sendEmail(wrapper);
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new Exception(e);
		}
	}
	
	public void sendEmail(SparkPostEmailWrapper anObject) throws Exception {
		URI url;
		try {
			LOGGER.debug("Starting send email process, with [URL="+SystemParameter.SparkPostConfiguration.API_URL+"]");
			url = new URI(SystemParameter.SparkPostConfiguration.API_URL);
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("Authorization", SystemParameter.SparkPostConfiguration.API_KEY);
			LOGGER.debug("JSON Body : " + mapper.writeValueAsString(anObject));
			HttpEntity<SparkPostEmailWrapper> requestEntity = new HttpEntity<SparkPostEmailWrapper>(anObject, headers);
			RestTemplate rt = new RestTemplate();
			rt.exchange(url, HttpMethod.POST, requestEntity, anObject.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("findObjects rest error {}", e);
			throw new Exception(e);
		}
	}
	
	// send mail using sparkpost library, need more further research. problem : system always fail to send email to google domain recipient
	/*@Override
	public void sendMail(List<String> toIds, String fromId, String subject, String emailContent, String[] attachmentLocation) throws Exception {
		LOGGER.debug("Start sending email using SMTP");
		Client client = new Client(SystemParameter.SPARKPOST_API_KEY);
		try{			
			client.sendMessage(fromId, resolveRecipient(toIds), subject, emailContent, emailContent);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}*/
	
}
