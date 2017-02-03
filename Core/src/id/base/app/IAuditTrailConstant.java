package id.base.app;

import java.util.HashMap;
import java.util.Map;

public class IAuditTrailConstant {

    boolean ENABLE_LOG_AUDIT_TRAIL = true;

    
    public static final String LOG_SUCCESS= "SUCCESS";
    public static final String LOG_FAIL= "FAIL";

    /* Audit trail message level */
    public static final String LOG_LEVEL_INFO  = "INFO";
    public static final String LOG_LEVEL_ERROR = "ERROR"; // Exception occur
    public static final String LOG_LEVEL_DEBUG = "DEBUG";
    public static final String LOG_LEVEL_WARN  = "WARNING";

	public static final String USER_SYSTEM = "SYSTEM"; 
    
	//User Login/Logout
	public static final int LOG_CODE_LOGIN = 44;
		public static final int LOG_SUB_CODE_LOGIN 	= 441;
		public static final int LOG_SUB_CODE_LOGOUT	= 442;
	
//	public static final int LOG_CODE_GANTI_PASSWORD = 3;
//	public static final int LOG_CODE_RESET_PASSWORD = 5;
	
	//User
	public static final int LOG_CODE_USER = 49;
		public static final int LOG_CODE_USER_ADD = 491;
		public static final int LOG_CODE_USER_EDIT = 492;
	
	//User Role
	public static final int LOG_CODE_USER_ROLE = 50;
		public static final int LOG_CODE_USER_ROLE_ADD = 501;
		public static final int LOG_CODE_USER_ROLE_EDIT = 502;
		public static final int LOG_CODE_USER_ROLE_DELETE = 503;
	
	//Lookup
	public static final int LOG_CODE_SYSADM_LOOKUP = 52;
		public static final int LOG_CODE_SYSADM_LOOKUP_ADD = 521;
		public static final int LOG_CODE_SYSADM_LOOKUP_EDIT = 522;
		public static final int LOG_CODE_SYSADM_LOOKUP_DELETE = 523;
	
	//System Parameter
	public static final int LOG_CODE_SYSADM_SYSTEM_PARAMETER = 53;
		public static final int LOG_CODE_SYSADM_SYSTEM_PARAMETER_EDIT = 531;
	
	//Audit Trail
	public static final int LOG_CODE_SYSADM_AUDIT_TRAIL = 3;
		public static final int LOG_CODE_SYSADM_AUDIT_TRAIL_EXPORT = 31;
	
				
	// Report
	public static final int LOG_CODE_REPORT = 47;
		public static final int LOG_SUB_CODE_EXPORT_NEW_BUSINESS 	= 471;
		public static final int LOG_SUB_CODE_EXPORT_TRIAL_BALANCE	= 472;
	
	public static Map<Integer,String> LOG_GROUP = new HashMap<Integer, String>();
	static {
		LOG_GROUP.put(LOG_CODE_LOGIN, "LOG_GROUP_LOGIN");
		LOG_GROUP.put(LOG_CODE_REPORT, "LOG_GROUP_REPORT");
		LOG_GROUP.put(LOG_CODE_USER, "LOG_GROUP_USER");
		LOG_GROUP.put(LOG_CODE_USER_ROLE, "LOG_GROUP_USER_ROLE");
		LOG_GROUP.put(LOG_CODE_SYSADM_LOOKUP, "LOG_GROUP_SYSADM_LOOKUP");
		LOG_GROUP.put(LOG_CODE_SYSADM_SYSTEM_PARAMETER, "LOG_GROUP_SYSADM_SYSTEM_PARAMETER");
		LOG_GROUP.put(LOG_CODE_SYSADM_AUDIT_TRAIL, "LOG_CODE_SYSADM_AUDIT_TRAIL");
	};
	
	public static Map<Integer, Integer> LOG_PARENT_MAPPING = new HashMap<>();
	static{
		// Login/Logout
		LOG_PARENT_MAPPING.put(LOG_SUB_CODE_LOGIN, LOG_CODE_LOGIN);
		LOG_PARENT_MAPPING.put(LOG_SUB_CODE_LOGOUT, LOG_CODE_LOGIN);
		
		//Lookup
		LOG_PARENT_MAPPING.put(LOG_CODE_SYSADM_LOOKUP_ADD, LOG_CODE_SYSADM_LOOKUP);
		LOG_PARENT_MAPPING.put(LOG_CODE_SYSADM_LOOKUP_EDIT, LOG_CODE_SYSADM_LOOKUP);
		LOG_PARENT_MAPPING.put(LOG_CODE_SYSADM_LOOKUP_DELETE, LOG_CODE_SYSADM_LOOKUP);
		
		//Audit Trail
		LOG_PARENT_MAPPING.put(LOG_CODE_SYSADM_AUDIT_TRAIL_EXPORT, LOG_CODE_SYSADM_AUDIT_TRAIL);
	}
}