package id.base.app.dao.passwordhistory;

import id.base.app.exception.SystemException;
import id.base.app.valueobject.PasswordHistory;

import java.util.List;

public interface IPasswordHistoryDAO {
	
	public abstract void saveOrUpdate(PasswordHistory anObject)throws SystemException;	
	public abstract boolean isPasswordExistsHistory(String password,Long fkAppUser) throws SystemException;
	public abstract List<PasswordHistory> getTotalPasswordByfkAppuser(Long fkAppUser) throws SystemException;
	public abstract List<PasswordHistory> getTotalPasswordByPasswordAndfkAppuser(String password, Long fkAppUser)throws SystemException;
	public void delete(PasswordHistory history);

}
