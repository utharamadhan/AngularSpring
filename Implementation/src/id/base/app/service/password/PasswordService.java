package id.base.app.service.password;

import id.base.app.exception.SystemException;


public interface PasswordService {
	public boolean validateOldPassword(Long userPK, String password) throws SystemException;
	public String findOldPassword(Long userPK,String userName) throws SystemException;
	public void submitNewPassword(Long userPK,String oldPassword,String newPassword,String confirmationPassword,String userName) throws SystemException;
	public void changePassword(Long userPK,String oldPassword,String newPassword,String confirmationPassword,String userName) throws SystemException;
	
}
