package id.base.app.csv;

public class ExportException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExportException(String message) {
		super(message);
	}
	
	public ExportException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ExportException(Throwable cause) {
		super(cause);
	}

}
