package id.base.app.util;

import id.base.app.SystemParameter;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class EmailFunction {
	
	public static boolean isValid(String emailAddress) {
		String addr = StringFunction.trim(emailAddress);
		try {
			new InternetAddress(addr, SystemParameter.STRICT_EMAIL_ADDRESS);
		} catch (AddressException e) {
			return false;
		}
		return true;
	}
	
}
