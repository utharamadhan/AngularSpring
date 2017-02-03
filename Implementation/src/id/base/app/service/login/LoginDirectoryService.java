package id.base.app.service.login;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.RuntimeUserLogin;

public interface LoginDirectoryService {
	
	public void register(RuntimeUserLogin userLogin) throws SystemException ;
	public void unregister(Long userPK) throws SystemException ;
	public RuntimeUserLogin findById(Long id) throws SystemException;
	public void unregisterAll() throws SystemException;
	public abstract void unreqisterExpiredLoginSession()
			throws SystemException;
	public RuntimeUserLogin findByAccessInfoId(String accessInfoId) throws SystemException;
	public abstract RuntimeUserLogin findByUserName(String userName) throws SystemException;

}
