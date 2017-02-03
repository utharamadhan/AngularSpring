package id.base.app;


import id.base.app.util.ReflectionFunction;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the default value for system parameter.
 * THis class will be used in run time.
 * TO update this value, Sysparam maintenance will call updateSystemEnvironment(String,String)
 * Before use, please load using SystemParameterLoader.
 */
public class SystemParameter {
	
    private static final Logger logger = LoggerFactory.getLogger(SystemParameter.class);

	public static String EMAIL_SENDER = "postmaster@base.id";
	public static String MAIL_USERNAME = "SMTP_Injection";
	public static String MAIL_PASSWORD = "efbabe7b51e09a87a9c5d63877e4a331db7e368f";
	public static String MAIL_HOST = "smtp.sparkpostmail.com";
	public static int MAIL_PORT = 587;
	
	public static class SparkPostConfiguration {
		public static final String API_URL = "https://api.sparkpost.com/api/v1/transmissions";
		public static final String API_KEY = "efbabe7b51e09a87a9c5d63877e4a331db7e368f";
		public static final Map<String, Boolean> DEFAULT_OPTIONS = new HashMap<>();
		static {
			DEFAULT_OPTIONS.put("open_tracking", true);
			DEFAULT_OPTIONS.put("click_tracking", true);
		}
		
		public static final Map<String, String> DEFAULT_METADATA = new HashMap<>();
		static {
			DEFAULT_METADATA.put("some_useful_metadata", "base.id");
		}
		
		public static final Map<String, String> DEFAULT_SUBSTITUTION_DATA = new HashMap<>();
		static {
			DEFAULT_SUBSTITUTION_DATA.put("signature", "base");
		}
		
		public static final Map<String, String> DEFAULT_RECIPIENT_SUBSTITUTION_DATA = new HashMap<>();
		static {
			DEFAULT_RECIPIENT_SUBSTITUTION_DATA.put("customer_type", "Platinum");
			DEFAULT_RECIPIENT_SUBSTITUTION_DATA.put("first_name", "base");
		}
		
		public static final String DEFAULT_FROM_NAME = "BASE.ID";
		public static final String DEFAULT_FROM_EMAIL = "hello@base.id";
	
	}
	
	public static String SHORT_MESSAGE_SERVICE_URL = "https://reguler.zenziva.net/apps/smsapi.php?";
	public static String SHORT_MESSAGE_SERVICE_USER_KEY = "zmgqvn";
	public static String SHORT_MESSAGE_SERVICE_PASS_KEY = "Base123";

	public static String FORECAST_API_URL = "https://api.forecast.io/forecast/{apiKey}/{coordinate}";
	public static String FORECAST_API_KEY = "c9b0d03ad555b2f836f7da53cdc76c72";
	
	public static Integer FORECAST_PURGING_LIMIT = 2;
	
	public static boolean STRICT_EMAIL_ADDRESS = true;

	public static String TEMPLATE_DIRECTORY = "/opt/template/";
	public static String IMAGE_SOURCE_DIRECTORY="/opt/images/source/";
	
	/** session max inactive interval in seconds */
    public static int SESSION_EXPIRED_INTERVAL =  30000;//5 * 60;
    
    public static String NAME_SESSION_EXPIRED_INTERVAL = "SESSION_EXPIRED_INTERVAL";
    
    /** time interval in days which is login still valid */
    public static int VALID_LOGIN_INTERVAL = 90;
    
    /** interval (DAY) to notify user that password will be expired **/
    public static int PASSWORD_EXPIRE_INTERVAL = 4;
    
    public static int MAX_PASSWORD_LENGTH = 16;
    public static int MIN_PASSWORD_LENGTH = 7;
    
    /** maximum days interval user not login to the system */
    public static int MAX_DAYS_NOT_LOGIN = 100;
    public static String NAME_MAX_DAYS_NOT_LOGIN = "MAX_DAYS_NOT_LOGIN";
    
    public static int MAX_PASSWORD_ERROR = 3;
    
    /** no of days before password become expired */
    public static int PASSWORD_LIFETIME = 120;
    
    /** max no of password change error -> lock **/
    public static int MAX_PASSWORD_CHANGE_ERROR = 3;
    
    /** default password for new user */
    public static String DEFAULT_PASSWORD = "password";
    
    /** max number of record to be shown in screen */
    public static int MAX_RECORD_PER_SCREEN = 15;
    
    public static String NAME_MAX_RECORD_PER_SCREEN 	= "MAX_RECORD_PER_SCREEN";
    public static String NAME_MAX_UPLOAD_FILE_SIZE		= "MAX_UPLOAD_FILE_SIZE"; 
        
    public static int MAX_PASSWORD_HISTORY_CHECK=5;
    
    public static int  AUDIT_TRAIL_EXPORT_MAX_ROW=10;
    
    public static String DIGESTER_ALGORITHM = "MD5";
    
    public static int MAX_UPLOAD_FILE_SIZE = 2000000;
    
	public static String LOCALE = "in_ID";
	
	public static synchronized void updateSystemEnvironment(String name, String value) {
    	ReflectionFunction.setProperties(SystemParameter.class, name, value);
    }

	public static String REST_SERVICE = "http://localhost:8181/ApplicationService";
	
	public static int ENCRYPT_ITERATION_NUMBER = 1;
}
