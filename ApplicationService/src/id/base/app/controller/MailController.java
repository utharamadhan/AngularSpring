package id.base.app.controller;

import id.base.app.service.mail.EmailAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mailController")
public class MailController {
	
	@Autowired
	@Qualifier("SMTPMailService")
	private EmailAPI mailService;
	
	@RequestMapping(method=RequestMethod.POST, value="/sendMailPost")
	public void sendMailPost(){
		try{			
			String toAddress = "utharamadhan@yahoo.com";
			String fromAddress = "utharamadhan@gmail.com";
			String subject = "Test Send Email Using Spring & Gmail SMTP";
			String messageBody = "Message Body";
			//mailService.sendMail(toAddress, fromAddress, subject, messageBody);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
