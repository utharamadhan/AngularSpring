package id.base.app.service.passwordhistory;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.PasswordHistory;

import java.util.List;

public interface IPasswordHistoryService {
	
	public boolean isPasswordExistHistory(String Password,Long fkAppUser) throws SystemException;
	public void addHistory(String password, Long fkAppUser) throws SystemException;
	public abstract List<PasswordHistory> getTotalPasswordByfkAppuser(Long fkAppUser) throws SystemException;
}
