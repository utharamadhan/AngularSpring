package id.base.app.test;

import id.base.app.service.mail.EmailAPI;

import javax.transaction.Transactional;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:serviceContext.xml"})
@WebAppConfiguration
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class MailTest extends TestCase{
	@Autowired
	@Qualifier(value="SMTPMailService")
	private EmailAPI smtpMailService; 
	
	
	@Test
	public void testMail() {
		try{
			//smtpMailService.sendMail("utharamadhan@yahoo.com", "utharamadhan@gmail.com", "Test Send Mail Use Spring", "Body Content");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
