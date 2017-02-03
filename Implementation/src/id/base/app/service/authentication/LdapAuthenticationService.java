package id.base.app.service.authentication;

import id.base.app.LoginSession;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.ldap.LdapAuthentication;
import id.base.app.valueobject.AppUser;

import java.util.Date;

public class LdapAuthenticationService extends BaseAuthentication {
	
	private LdapAuthentication<AppUser> ldapAuthentication;
	
	public LdapAuthenticationService(){
		super();
	}

	public LdapAuthenticationService(LdapAuthentication<AppUser> ldapAuthentication) {
		this.ldapAuthentication = ldapAuthentication;
	}
	
	public LoginSession authenticateLogin(AppUser appUser, String password) throws SystemException {
		boolean success = ldapAuthentication.authenticate(appUser, password);
		if (!success) {
			throw new SystemException(new ErrorHolder("error.password.mismatch")) ;
		}
			   
		return buildLoginSession(appUser);
	}

	@Override
	public boolean isNotificationPeriod(Date expiredDate, Date currentDate)
			throws SystemException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LoginSession authenticateLogin(AppUser appUser)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
	

	

	

}
