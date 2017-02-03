package id.base.app.service.authentication;

import id.base.app.LoginSession;
import id.base.app.SystemConstant;
import id.base.app.SystemParameter;
import id.base.app.dao.user.IUserDAO;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.security.IStringDigester;
import id.base.app.valueobject.AppUser;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DatabaseAuthenticationService extends BaseAuthentication {

	@Autowired
	private IUserDAO userDao;
	
	@Autowired
	IStringDigester digester;
	
	public DatabaseAuthenticationService() {
		super();
	}
	
	public DatabaseAuthenticationService(IStringDigester digester, IUserDAO userDao) {
		super();
		this.digester = digester;
		this.userDao = userDao;
	}
	
	public LoginSession authenticateLogin(AppUser appUser) throws SystemException {
		validateUser(appUser);
		validateActiveStatus(appUser);
		validateLockStatus(appUser);	
		return buildLoginSession(appUser);
	}

	private void validateUser(AppUser appUser) {
		if(appUser==null) {
			throw new SystemException(new ErrorHolder("error.user.not.found"));
		}
	}
	private void validateActiveStatus(AppUser appUser) {
		if(appUser.getStatus() == SystemConstant.UserStatus.INACTIVE) {
			throw new SystemException(new ErrorHolder("error.user.inactive"));
		}
	}
	
	private void validateLockStatus(AppUser appUser) {
		if(appUser.getLock() == true) {
			throw new SystemException(new ErrorHolder("error.user.lock"));
		}
	}

	public boolean isNotificationPeriod(Date expiredDate, Date currentDate)throws SystemException {
		Calendar dt1 = Calendar.getInstance();
		Calendar dt2=Calendar.getInstance();
		dt1.setTime(currentDate);
		dt2.setTime(expiredDate);
		Long milldiff=dt2.getTimeInMillis()-dt1.getTimeInMillis();
		long days=milldiff / (24 * 60 * 60 * 1000);
		if (days<=SystemParameter.PASSWORD_EXPIRE_INTERVAL){
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public LoginSession authenticateLogin(AppUser appUser, String pass)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
