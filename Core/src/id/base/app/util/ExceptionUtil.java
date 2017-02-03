package id.base.app.util;

import java.sql.BatchUpdateException;
import java.sql.SQLException;

import org.slf4j.Logger;

public class ExceptionUtil {
	
	public static void extractNativeSqlException(Logger logger, Exception e) {
		// try to find BatchUpdateException and call getNextException
		while ((e = (Exception) e.getCause()) != null && !(e instanceof BatchUpdateException));
		
		if (e != null && e instanceof BatchUpdateException) {
			SQLException nextException = ((BatchUpdateException)e).getNextException();
			logger.error("Error during querying database. Error message is: " + 
					nextException.getMessage(),nextException);
		}
	}
}
