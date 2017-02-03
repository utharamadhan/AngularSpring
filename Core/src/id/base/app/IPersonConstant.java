package id.base.app;

public interface IPersonConstant {

    public static final int STATUS_INACTIVE = 0;
    public static final int STATUS_ACTIVE   = 1;

    public static final int STATUS_LOGIN_LOCKED   = 1;

   
    public static final int LOCK_REASON_BY_ADMIN            = 1;
    public static final int LOCK_REASON_LIMIT_LOGIN_TRY     = 2;
    public static final int LOCK_REASON_NOT_LOGIN_TOO_LONG  = 3;
    public static final int LOCK_REASON_LIMIT_CHANGEPWD_TRY = 4;
    public static final int LOCK_REASON_FIRST_LOGIN_TOO_LONG = 5;

    public static final boolean NEW_USER = true;
	public static final boolean OLD_USER = false;

   
}
