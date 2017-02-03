package id.base.app.service;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.AppUser;

import java.util.Date;

public interface AuthenticationService<T> {
	
	public T authenticateLogin(AppUser appUser) throws SystemException ;
	public T authenticateLogin(AppUser appUser, String pass) throws SystemException ;
	public abstract boolean isNotificationPeriod(Date expiredDate, Date currentDate)throws SystemException;

}
