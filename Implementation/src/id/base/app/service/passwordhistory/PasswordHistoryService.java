package id.base.app.service.passwordhistory;

import id.base.app.SystemParameter;
import id.base.app.dao.passwordhistory.IPasswordHistoryDAO;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.util.DateTimeFunction;
import id.base.app.valueobject.PasswordHistory;

import java.util.List;

import org.jasypt.digest.StringDigester;

public class PasswordHistoryService implements IPasswordHistoryService{
	private IPasswordHistoryDAO historyDao;
	private StringDigester digester;
	
	@Override
	public void addHistory(String password, Long fkAppUser)
			throws SystemException {
		PasswordHistory history = new PasswordHistory();
		String digestedPassword=digester.digest(password);
		if( isPasswordExistHistory(password, fkAppUser) ){
			List<PasswordHistory> listPasshist=historyDao.getTotalPasswordByPasswordAndfkAppuser(digestedPassword, fkAppUser);
			if (listPasshist.size()>=SystemParameter.MAX_PASSWORD_HISTORY_CHECK) {
				throw new SystemException(new ErrorHolder("error.password.too.often.used",new Object[] { SystemParameter.MAX_PASSWORD_HISTORY_CHECK }));
			}
			else
			{
				history.setPasswordValue(digester.digest(password));
				history.setRecordedDate(DateTimeFunction.getCurrentDate());
				history.setFkAppUser(fkAppUser);
				historyDao.saveOrUpdate(history);
			}
		}
		else
		{
			history.setPasswordValue(digester.digest(password));
			history.setRecordedDate(DateTimeFunction.getCurrentDate());
			history.setFkAppUser(fkAppUser);
			historyDao.saveOrUpdate(history);
		}
		/*if( !isPasswordExistHistory(password, fkAppUser) )
		{
			List <PasswordHistory> listPassHist = historyDao.getTotalPasswordByfkAppuser(fkAppUser);
			PasswordHistory history = new PasswordHistory();
	
			if (listPassHist.size() >= SystemParameter.MAX_PASSWORD_HISTORY_CHECK)
				historyDao.delete(listPassHist.get(0));
	
			history.setPasswordValue(digester.digest(password));
			history.setRecordedDate(DateTimeFunction.getCurrentDate());
			history.setFkAppUser(fkAppUser);
			historyDao.saveOrUpdate(history);
		}
		else
		{
			throw new SystemException(new ErrorHolder("error.new.password.is.found.on.history"));	
		}*/
	}

	@Override
	public List<PasswordHistory> getTotalPasswordByfkAppuser(Long fkAppUser)
			throws SystemException {
		return historyDao.getTotalPasswordByfkAppuser(fkAppUser);
	}

	@Override
	public boolean isPasswordExistHistory(String password, Long fkAppUser)
			throws SystemException {
		return historyDao.isPasswordExistsHistory(digester.digest(password), fkAppUser);
	}

	public void setDigester(StringDigester digester) {
		this.digester = digester;
	}

	public StringDigester getDigester() {
		return this.digester;
	}

	public void setHistoryDao(IPasswordHistoryDAO historyDao) {
		this.historyDao = historyDao;
	}

	public IPasswordHistoryDAO getHistoryDao() {
		return historyDao;
	}

}
