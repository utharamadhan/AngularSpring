package id.base.app.csv;

import id.base.app.SystemConstant;
import id.base.app.util.DateTimeFunction;

import java.beans.PropertyDescriptor;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
//import org.apache.el.util.ReflectionUtil;
import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVWriter;

public class BeanToCsv<T> extends CSVWriter {
	private static Logger logger = Logger.getLogger(BeanToCsv.class);
	public Collection<T> beans;
	private String[] columns;
	public BeanToCsv(Writer writer) {
		super(writer);
	}
	
	public BeanToCsv(Writer writer, char separator)
	{
		this(writer, separator, '"');
	}

	public BeanToCsv(Writer writer, char separator, char quotechar)
	{
		this(writer, separator, quotechar, '"');
	}

	public BeanToCsv(Writer writer, char separator, char quotechar, char escapechar)
	{
		this(writer, separator, quotechar, escapechar, "\n");
	}

	public BeanToCsv(Writer writer, char separator, char quotechar, String lineEnd)
	{
		this(writer, separator, quotechar, '"', lineEnd);
	}

	public BeanToCsv(Writer writer, char separator, char quotechar, char escapechar, String lineEnd)
	{
		super(writer, separator, quotechar, escapechar, lineEnd);
	}
	
	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public Collection<T> getBeans() {
		return beans;
	}

	public void setBeans(Collection<T> beans) {
		this.beans = beans;
	}

	public void writeCollections(Collection<T> beans) throws ExportException { 
		if (columns == null || columns.length == 0) {
			throw new ExportException("Columns are not set yet");
		}
		if (beans == null || beans.size() == 0) {
			return;
		}
			
		try {
			for (T bean: beans) {
				String[] data = new String[columns.length];
				for(int i = 0; i < columns.length; i++) {
					PropertyDescriptor descriptor = null;  //ReflectionUtils. getPropertyDescriptor(bean, columns[i]);
					if (descriptor.getPropertyType().isAssignableFrom(Date.class)) {
						Date date = (Date) descriptor.getReadMethod().invoke(bean);
						data[i] = DateTimeFunction.date2String(date, SystemConstant.SYSTEM_DATE_TIME_MASK);
					} else {
						data[i] = BeanUtils.getProperty(bean, columns[i]);
					}
				}
				writeNext(data);
			}
		} catch (IllegalAccessException e) {
			logger.error("Error during csv export: " + e.getMessage());
			e.printStackTrace();
			throw new ExportException("Error exporting csv",e);
		} catch (InvocationTargetException e) {
			logger.error("Error during csv export: " + e.getMessage());
			e.printStackTrace();
			throw new ExportException("Error exporting csv",e);
		} catch (NoSuchMethodException e) {
			logger.error("Error during csv export: " + e.getMessage());
			e.printStackTrace();
			throw new ExportException("Error exporting csv",e);
		}
	}
}
