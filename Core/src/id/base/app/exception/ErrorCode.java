package id.base.app.exception;

public interface ErrorCode {
	public String ERRSYS_00000 = "ERRSYS00000"; // unknown exception
	public String ERRSYS_NSOPE = "ERRSYSNSOPE"; // not supported operation exception
	public String ERRSYS_00001 = "ERRSYS00001"; // constraint exception
	public String ERRSYS_00002 = "ERRSYS00002"; // Object property must not be null
	public String ERRSYS_HDAE = "ERRSYS_HDAE"; 	// hibernate dataaccess exception
	public String ERRSYS_ONFE = "ERRSYS_ONFE"; 	// Object not found exception
	

	public String ERR_00000 = "ERR00000"; 	// Login Error
	public String ERR_00001 = "ERR00001"; 	// when trying to delete object, object is already deleted by other user
	public String ERR_00002 = "ERR00002"; 	// when trying to update object, object is already updated by other user
	public String ERR_00003 = "ERR00003"; 	// User does not exist
	public String ERR_00004 = "ERR00004"; 	// Login expired
	public String ERR_00005 = "ERR00005"; 	// User is inactive
	public String ERR_00006 = "ERR00006"; 	// User is locked
	public String ERR_00007 = "ERR00007"; 	// You are not authorized to logged in
	public String ERR_00008 = "ERR00008"; 	// Login expired, user should login before now
	public String ERR_00009 = "ERR00009"; 	// This is your first time login
	public String ERR_00010 = "ERR00010"; 	// Login blocked, login trying limit exceed
	public String ERR_00011 = "ERR00011"; 	// Password expired
	public String ERR_00012 = "ERR00012"; 	// Password reset by admin
	public String ERR_00013 = "ERR00013"; 	// Password is not match
	public String ERR_00014 = "ERR00014"; 	// Max to change password is exceeded
	public String ERR_00015 = "ERR00015"; 	// Min/max length of password is exceeded (for enterprise)
	public String ERR_00016 = "ERR00016"; 	// Min/max length of password is exceeded (for express)
	public String ERR_00017 = "ERR00017"; 	// Password can not be the same as username (for enterprise)
	public String ERR_00018 = "ERR00018"; 	// Password can not be the same as username (for express)
	public String ERR_00019 = "ERR00019"; 	// Password cannot be the same as old password
	public String ERR_00020 = "ERR00020"; 	// Password cannot be the same as {0} old password
	public String ERR_00021 = "ERR00021"; 	// Party with code {0} is already exist
	public String ERR_00022 = "ERR00022"; 	// Job Assignment Error
	public String ERR_00023 = "ERR00023"; 	// Job Assignment is already exist
	public String ERR_00024 = "ERR00024"; 	// Change password error

	public String ERR_00100 = "ERR00100"; 	// Payment Slip Book Error
	public String ERR_00101 = "ERR00101"; 	// Payment Slip Error
	public String ERR_00102 = "ERR00102"; 	// Book series does not exist
	public String ERR_00103 = "ERR00103"; 	// Series no should be equal to or greater than 1
	public String ERR_00104 = "ERR00104"; 	// Number of slip is less than {0}
	public String ERR_00105 = "ERR00105"; 	// It is too late to register. Date can not be less than {0} day(s) before now
	public String ERR_00106 = "ERR00106"; 	// Max length of payment slip series is {0} digits
	public String ERR_00107 = "ERR00107"; 	// Payment slip book already exist with deleted status
	public String ERR_00108 = "ERR00108"; 	// Payment slip book already exist
	public String ERR_00109 = "ERR00109"; 	// Duplicate Payment Slip Book found in the system. You may have registered the same payment slip Book before
	public String ERR_00110 = "ERR00110"; 	// Payment Slip book with series {0} no {1}-{2} is not in 'Returned' or 'New' state
	public String ERR_00111 = "ERR00111"; 	// Max Assigned Payment Slip limit reached , max : {0}, already assigned : {1}, new assigned : {2}
	public String ERR_00112 = "ERR00112"; 	// Payment slip should be in 'New' state
	public String ERR_00113 = "ERR00113"; 	// Payment slip book should be in 'Assign' state
	public String ERR_00114 = "ERR00114"; 	// Cannot cancel allocate one or more payment slip books in new state. {0}
	public String ERR_00115 = "ERR00115"; 	// Cannot print one or more payment slip books in new state. {0}
	public String ERR_00116 = "ERR00116"; 	// This Payment Slip books  {0} does not contain slip with ASSIGN State

	public String ERR_SYSUG = "ERR_SYSUG"; 	// Error System User Group Maintenance
	
	public String ESB_00001 = "ESB_00001"; 	// Production Root Already Exist
	public String ESB_00002 = "ESB_00002"; 	// Simulation Root exceed permitted number 
	public String ESB_00003 = "ESB_00003";  // cannot modify production root to non production root type
	public String ESB_00004 = "ESB_00004";  // Production root can not be deleted. Switch production flag to another root first.
	public String ESB_00005 = "ESB_00005";  // Invalid Root ID. Root is either not found or not a root.
	public String ESB_00006 = "ESB_00006";  // Production Root not found or have not been inititalized 
	public String SQL_ERROR = "SQL_ERROR";  // SQL Error / SQL Exception
}
