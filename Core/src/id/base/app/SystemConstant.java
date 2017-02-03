package id.base.app;

import id.base.app.util.FileManager;

import java.io.File;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Application Constant
 */
public class SystemConstant  {
	static Logger logger = LoggerFactory.getLogger(SystemConstant.class);
	
	public static final String SALT = "...-base-...";
	
	/***  Field Definition Constant***/
	public final static String FIELD_DEFINITION_REFRENCE = "Reference";

    /***  Date time format ***/
    public static final String SYSTEM_DATE_DAY = "dd";
    public static final String SYSTEM_DATE_MONTH = "MM";
    public static final String SYSTEM_DATE_MONTH_NAME = "MMM";
    public static final String SYSTEM_DATE_YEAR = "yyyy";
    public static final String SYSTEM_DATE_YEAR_SHORT = "yy";
    public static final String SYSTEM_DATE_MASK = "dd-MM-yyyy";
    public static final String SYSTEM_DATE_MONTH_DETAIL = "dd-MMM-yyyy";
    public static final String SYSTEM_DATE_MONTH_POINT = "dd.MMM.yyyy";
    public static final String SYSTEM_DATE_TIME_MASK="dd-MMM-yyyy HH:mm";
    public static final String SYSTEM_DATE_TIME_SECOND_MASK="dd-MMM-yyyy HH:mm:ss";
    public static final String SYSTEM_MONTH_YEAR_MASK = "MM-yyyy";
    public static final String SYSTEM_YEAR_MONTH_MASK = "yyyy-MM";
    public static final String SYSTEM_FULL_MONTH_YEAR_MASK = "MMMMM yyyy";
    public static final String SYSTEM_FULL_MONTH_FULL_DATE_MASK = "dd MMMMM yyyy";
    public static final String SYSTEM_TIME_MASK = "dd-MM-yyyy HH:mm:ss";
    public static final String SYSTEM_FULL_TIME_MASK = "dd MMMMM yyyy HH:mm:ss";
    public static final String SYSTEM_TIME_MASK_SECONDHAND = "dd-MM-yyyy HH:mm";
    public static final String HOUR_MINUTE_MASK = "HH:mm";
    public static final String HOUR_MINUTE_SECOND_MASK = "HH:mm:ss";
    public static final String HOUR_MINUTE_SECOND_MASK_NO_DELIMITER = "HHmmss";
    public static final String HOUR_MINUTE_MILISECOND_MASK_NO_DELIMITER = "HHmmssSSS";
    public static final String SYSTEM_DATE_MASK_NO_DELIMITER = "yyyyMMdd";
    public static final String SYSTEM_DATE_MASK_YEAR_MONTH = "yyyyMM";
    public static final String HOUR_MINUTE_MASK_NO_DELIMITER = "HHmm";
    public static final String DATABASE_DATE_FORMAT_STD = "MM/dd/yyyy";
    public static final String HOUR_MINUTE_SECOND_AMPM= "hh:mm:ss a";
    public static final String SYSTEM_FULL_DATE = "dd MM yyyy";
    public static final String SYSTEM_REPORT_DATE = "yyyyMMdd_HHmm";
    public static final String SYSTEM_REPORT_DATE_TIME = "yyyyMMdd_HHmmssSSS";
    public static final String SYSTEM_REPORT_MONTH="MMMMMMMMMM";
    public static final String SYSTEM_DATE_HOUR_MINUTE_NO_DELIMITER = "yyyyMMddHHmm";
    public static final String SYSTEM_DATE_TIME_NO_DELIMITER = "yyyyMMddhhmmss";
    
    
    public static final String DECIMAL_FORMAT = "#,##0.########";
    
    public static final String WEB_SERVICE_DATETIME = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String WEB_SERVICE_DATE = "yyyy-MM-dd";
    public static final String WEB_SERVICE_TIME = "HH:mm:ss.SSS";
    public static final String SYSTEM_DATE_MASK_2 = "dd/MM/yyyy";
    
    public static final String SYSTEM_DATE_TIME_FORMAT = "EEE MMM d HH:mm:ss Z yyyy";
    
    public static final String EXPORT_DATETIME = "yyyy-MM-dd.HH-mm-ss-SSS";
    
    /*** Currency format ***/
    public static final String SYSTEM_CURRENCY_MASK = "#,##0.00";
    public static final String DECIMAL_MASK = "###0.00";
    public static final String SYSTEM_NUMBER_MASK = "#,##0";

   public static final String CLOSE_ON_HOLIDAY="TIDAK";
   public static final String OPEN_ON_HOLIDAY="YA";

    public static final Integer FIELD_TYPE_INT = 0;
    public static final Integer FIELD_TYPE_LONG = 1;
    public static final Integer FIELD_TYPE_DOUBLE = 2;
    public static final Integer FIELD_TYPE_STRING = 3;
    public static final Integer FIELD_TYPE_DATE = 4;
    public static final Integer FIELD_TYPE_BOOLEAN = 5;
    public static final Integer FIELD_TYPE_CRON = 6;

    /*** Data type for profile builder and FCS ***/
    public static final int DATATYPE_NUMBER = 0;
    public static final int DATATYPE_STRING = 1;
    public static final int DATATYPE_DATE = 2;
    public static final int DATATYPE_BOOLEAN = 3;
    
    public static final int BIGDECIMAL_SCALE = 4;
    public static final RoundingMode BIGDECIMAL_ROUNDING = RoundingMode.CEILING;

    //Values to support boolean value in database
    public static final int iTRUE       = 1;
    public static final int iFALSE      = 0;

    public static final String _TRUE    = String.valueOf(SystemConstant.iTRUE);
    public static final String _FALSE    = String.valueOf(SystemConstant.iFALSE);

    /*** List / table view sorting constant ***/
    public static final int SORT_ASCENDING = 0;
    public static final int SORT_DESCENDING = 1;

    public static final String AJAX_SUCCESS = "true";  
    public static final String AJAX_FAILED = "false";  
    public static final String AJAX_NOT_APPROVAL="notApproval";
    
    public static final String METHOD_NAME_ADD="add";
	public static final String METHOD_NAME_EDIT="edit";
	public static final String METHOD_NAME_SHOW="show";
	
	public static final int USER_TYPE_INTERNAL=1;
	public static final int USER_TYPE_EXTERNAL=2;
	public static final String USER_TYPE_INTERNAL_STR = "Internal";
	public static final String USER_TYPE_EXTERNAL_STR = "External";
    public static final String[] STR_USER_TYPE={"","Internal","Eksternal"};
    public static Map<String, String> userTypeMaps = new LinkedHashMap<>();
	static{
		userTypeMaps.put(String.valueOf(USER_TYPE_INTERNAL), USER_TYPE_INTERNAL_STR);
	}
	
	public static final String RECAPCHA_PUBLIC_KEY ="6LdFYsESAAAAADc5B5e9brgINvhCEuYwt5QceKPK";
	public static final String RECAPCHA_PRIVATE_KEY="6LdFYsESAAAAAHSS-lJDGrgcGrvzdSfaLs-iUSZZ";
	public static final String RECAPCHA_CHALLENGE_FIELD="recaptcha_challenge_field";
	public static final String RECAPCHA_RESPONSE_FIELD="recaptcha_response_field";
	
	
	public static final String USER_TYPES="userType";
    
    public static final String DEFAULT_PASSWORD="master";
    
    public static final int HIBERNATE_JDBC_BATCH_SIZE = 20;
    
    public static final int SEX_TYPE_MAN=1;
    public static final int SEX_TYPE_WOMAN=2;
    
    public static final String [] JENIS_KELAMIN={"","Laki-laki","Perempuan"};
    
    public static final String AJAX_INDICATOR = "isAJAX";

    public static final String PATH_SEPARATOR="/";
    public static final String SEPARATOR_FILE_NAME = "__";
	
	public static final String MAIL_SEPERATOR = ";";

	public static final String SYSTEM_USER = "SYSTEM";
	
	public static final String _YES="YES";
	public static final String _NO="NO";

	public static final String EMAIL_DELIMITER = ";";
	public static final String REPORT_DELIMETER_NEW_LINE="_#N";
	public static final String PERCENTAGE_HTML_NUMBER="&#37;";
	public static final String PERCENTAGE_STRING="%";
	
	public static final String ERROR_LIST	= "errorList";
	public static final String REDIRECT = "loginPage";
	
	public static final String VIOLATION_EXCEPTION_MESSAGES = "could not execute batch";
	
	public static final String DEFAULT_REPORT_TEMPLATE_LOCATION = "template";
	
	public static final class ActivationMethod {
		public static final Integer EMAIL = 1;
		public static final Integer SHORT_MESSAGE_SERVICE = 2;
	}
	
	public static final class PROTOCOL{
		public static final int LOCAL 	= 0;
		public static final int FTP 	= 1;
		public static final int SFTP 	= 2;
		
		public static final int FTP_DEF_PORT = 21;
		public static final int SFTP_DEF_PORT = 21;
	}
	
	public static final List<String> arithmeticOperations = new LinkedList<String>();
	static{
		arithmeticOperations.add("+");
		arithmeticOperations.add("-");
		arithmeticOperations.add("*");
		arithmeticOperations.add("/");
	}
	
	
	public static String ACTIVATION_URL = "/Web/do/registration/activationPage";
	public static String LOGIN_URL = "http://utharamadhan.com";
	public static void setUrl(String url){
		LOGIN_URL = url;
	}
	
	public static String ADMIN_URL = "http://app.base.id/WebAdmin";
	public static void setGoToUrl(String adminUrl){
		ADMIN_URL = adminUrl;
	}
	
	public static String WEB_TRANS_COOKIE_NAME = "token";
	public static String WEB_ADMIN_COOKIE_NAME = "token";
	public static String COOKIE_SEPARATOR = "%";
	public static String TOKEN_SEPARATOR = "|";
	
	public static String UNIQUE_TOKEN = "sSs";
	
	public static String SHARED_FOLDER_LOCATION 		= "/base";
	public static String AUDIT_TRAIL_EXPORT_DIR 		= "D:/base/auditTrail/";
	public static String FILE_DIRECTORY_TEMP 			= "D:/base";
    public static String RESULT_DIRECTORY				= "/Result";
    public static String REMOTE_DIRECTORY 				= "/uploadFile/";
    public static String LOCAL_TEMP_DIR_EXPORT 			= FILE_DIRECTORY_TEMP + "/tempExport/";
    public static String LOCAL_TEMP_DIRECTORY_REPORT	= FILE_DIRECTORY_TEMP + "/report/";
    public static String LOCAL_TEMP_DIRECTORY_USER		= FILE_DIRECTORY_TEMP + "/user/";
    public static String FILE_STORAGE					= "";
    public static String FILE_CONTENT_DIRECTORY			= "contentDirectory" + File.separator;
    public static String FILE_FEATURED_IMAGE_DIRECTORY	= "featuredImage" + File.separator;
    public static String FILE_EBOOK_DIRECTORY			= "ebook" + File.separator;
	
	public static void setSharedFolderLocation(String sharedFolderLoc) throws Exception {
		SHARED_FOLDER_LOCATION = sharedFolderLoc;
		File file = new File(sharedFolderLoc);
		// if shared folder not exists/mounted and not writable, quit!!!
		if (!file.exists() || !file.canWrite()) {
			logger.error("Shared folder are not configured yet, can't start app");
			throw new Exception("Shared folder are not configured yet");
		}
		
		AUDIT_TRAIL_EXPORT_DIR = sharedFolderLoc + "/auditTrail/";
		FileManager.createDir(AUDIT_TRAIL_EXPORT_DIR);
		
		FILE_DIRECTORY_TEMP = sharedFolderLoc + "/temp";
		FileManager.createDir(FILE_DIRECTORY_TEMP);
		
		RESULT_DIRECTORY = sharedFolderLoc + "/result/";
		FileManager.createDir(RESULT_DIRECTORY);
		
		REMOTE_DIRECTORY = sharedFolderLoc + "/uploadFile/";
		FileManager.createDir(REMOTE_DIRECTORY);
		
		LOCAL_TEMP_DIR_EXPORT = FILE_DIRECTORY_TEMP + "/export/";
		FileManager.createDir(LOCAL_TEMP_DIR_EXPORT);
		
		LOCAL_TEMP_DIRECTORY_REPORT = FILE_DIRECTORY_TEMP + "/report/";
		FileManager.createDir(LOCAL_TEMP_DIRECTORY_REPORT);
		
		LOCAL_TEMP_DIRECTORY_USER = FILE_DIRECTORY_TEMP + "/user/";
		FileManager.createDir(LOCAL_TEMP_DIRECTORY_USER);
		
		FILE_STORAGE = sharedFolderLoc + File.separator + "fileStorage" + File.separator;
		FileManager.createDir(FILE_STORAGE);
		
	}
	
	public static final class UserStatus {
		public static final String ACTIVE_STR = "Active";
		public static final String INACTIVE_STR	= "Inactive";
		
		public static int ACTIVE = 1;
		public static int INACTIVE = 2;
		public static HashMap<Integer, String> statusMaps = new HashMap<Integer, String>();
		static{
			statusMaps.put(ACTIVE, ACTIVE_STR);
			statusMaps.put(INACTIVE, INACTIVE_STR);
		}
	}
	
	public static final class ValidFlag {
		public static Integer INVALID 			= 0;
		public static Integer VALID 			= 1;
		public static Integer VALID_MODIFIED 	= 2;
		
		public static String INVALID_STR	= "Invalid";
		public static String VALID_STR		= "Valid";
		public static String VALID_MODIFIED_STR	= "Valid Modified";
		
		public static HashMap<Integer, String> validFlagMap = new HashMap<>();
		static{
			validFlagMap.put(INVALID, INVALID_STR);
			validFlagMap.put(VALID, VALID_STR);
			validFlagMap.put(VALID_MODIFIED, VALID_MODIFIED_STR);
		}
	}
	
	public static String IMAGE_SHARING_URL = "http://";
	
	public static void setImageSharingURL(String imageSharingURL) {
		IMAGE_SHARING_URL = imageSharingURL;
	}
	
	public static final class StatusTransInItem {
		public static Integer INVALID 			= 0;
		public static Integer VALID 			= 1;
		public static Integer EMPTY 			= 2;
	}
	
	public static final class StatusProd {
		public static final Integer INVALID = 0;
		public static final Integer VALID = 1;
		public static final Integer COMPLETE = 2;
		public static final Integer PAST = 3;
	}
	
	public static final String USER_OBJECT_KEY = "user";
	
	public static final String NAME = "name";
	public static final String CODE = "code";
	
	public static final class CurrencyDataType {
		public static int PRECISION = 18;
		public static int SCALE = 4;
	}
	
	public static final class UserRole {
		public static final String SUPER_ADMIN 			= "SA";
		public static final String HEAD_MEMBER 			= "HM";
		public static final String TRANSACTION_MEMBER 	= "MT";
	}
	
	public static final String EMPTY_KEYWORD = "";
	
	public static final class UsageItemType {
		public static final String BAHAN_BAKU_PRODUKSI	= "BBP";
		public static final String BARANG_PENUNJANG_PRODUKSI = "BPP";
	}
	
	public static final String USAGE_THIRD_PARTY = "TP";
	
	public static final class TransInSourceType {
		public static final String BUYING	= "BUY";
		public static final String MAKLON	= "MKN";
		
		public static final String BUYING_DESCR = "Pembelian";
		public static final String MAKLON_DESCR = "Maklon";
		
		public static final HashMap<String, String> TRANS_IN_SOURCE_TYPE_DESCR = new HashMap<>();
		static {
			TRANS_IN_SOURCE_TYPE_DESCR.put(BUYING, BUYING_DESCR);
			TRANS_IN_SOURCE_TYPE_DESCR.put(MAKLON, MAKLON_DESCR);
		}
	}
	
	public static final class TransOutSourceType {
		public static final String SALES	= "SLS";
	}
	
	public static final class TransOutFilter {
		public static final String ALL = "ALL";
		public static final String IN = "IN";
		public static final String PROD = "PROD";
		
		public static final String ALL_DESCR = "Semua";
		public static final String IN_DESCR = "Pembelian";
		public static final String PROD_DESCR = "Produksi";
		
		public static final HashMap<String, String> TRANS_OUT_DESCRIPTION = new HashMap<>();
		static {
			TRANS_OUT_DESCRIPTION.put(ALL, ALL_DESCR);
			TRANS_OUT_DESCRIPTION.put(IN, IN_DESCR);
			TRANS_OUT_DESCRIPTION.put(PROD, PROD_DESCR);
		}
	}
	
	public static final class InitialWizard {
		
		public static final Integer FIRST_COMPANY = 1;
		public static final Integer SECOND_THIRD_PARTY = 2;
		public static final Integer THIRD_PRODUCT = 3;
		public static final Integer FOURTH_WAREHOUSE = 4;
		public static final Integer FIFTH_MACHINERY = 5;
		public static final Integer SIXTH_TRANSPORTER = 6;
		public static final Integer SEVENTH_MASTER_FEE = 7;
		public static final Integer EIGHTH_LOOKUP = 8;
		
		public static final Integer DONE = 9;
		
		public static final String FIRST_COMPANY_REDIRECT_URL = "/do/initialWizard/showCompany?initialWizardDecorator";
		public static final String SECOND_THIRD_PARTY_REDIRECT_URL = "/do/initialWizard/showThirdParty?initialWizardDecorator";
		public static final String THIRD_PRODUCT_REDIRECT_URL = "/do/initialWizard/showProduct?initialWizardDecorator";
		public static final String FOURTH_WAREHOUSE_REDIRECT_URL = "/do/initialWizard/showWarehouse?initialWizardDecorator";
		public static final String FIFTH_MACHINERY_REDIRECT_URL = "/do/initialWizard/showMachinery?initialWizardDecorator";
		public static final String SIXTH_TRANSPORTER_REDIRECT_URL = "/do/initialWizard/showTransporter?initialWizardDecorator";
		public static final String SEVENTH_MASTER_FEE_REDIRECT_URL = "/do/initialWizard/showMasterFee?initialWizardDecorator";
		public static final String EIGHTH_LOOKUP_REDIRECT_URL = "/do/initialWizard/showLookup?initialWizardDecorator";
		
		public static final HashMap<Integer, String> REDIRECT_MAP = new HashMap<>();
		static {
			REDIRECT_MAP.put(FIRST_COMPANY, FIRST_COMPANY_REDIRECT_URL);
			REDIRECT_MAP.put(SECOND_THIRD_PARTY, SECOND_THIRD_PARTY_REDIRECT_URL);
			REDIRECT_MAP.put(THIRD_PRODUCT, THIRD_PRODUCT_REDIRECT_URL);
			REDIRECT_MAP.put(FOURTH_WAREHOUSE, FOURTH_WAREHOUSE_REDIRECT_URL);
			REDIRECT_MAP.put(FIFTH_MACHINERY, FIFTH_MACHINERY_REDIRECT_URL);
			REDIRECT_MAP.put(SIXTH_TRANSPORTER, SIXTH_TRANSPORTER_REDIRECT_URL);
			REDIRECT_MAP.put(SEVENTH_MASTER_FEE, SEVENTH_MASTER_FEE_REDIRECT_URL);
			REDIRECT_MAP.put(EIGHTH_LOOKUP, EIGHTH_LOOKUP_REDIRECT_URL);
		}
		
	}
}
