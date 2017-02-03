package id.base.app;

import org.springframework.dao.DataAccessException;

public class HibernateDataAccessException extends DataAccessException {

	public HibernateDataAccessException(String msg) {
		super(msg);
	}

	public HibernateDataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
