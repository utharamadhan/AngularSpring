package id.base.app.report;

import id.base.app.util.DateTimeFunction;

import java.io.File;
import java.util.Date;


/**
 * @author anicka andry
 * <pre> wrapper for <code>java.io.File<code/> add 
 *       <code>compareable</code>  based on created date so we can sort before this object is bind into presentaion layer
 * </pre>
 * 
 */
public class ReportFile implements Comparable<ReportFile>{
	
	private String fileName;
	private String absolutePath;
	private Date createdDate ;
	
	public ReportFile(File file) {
		this.fileName = file.getName();
		this.absolutePath = file.getAbsolutePath();
		this.createdDate = DateTimeFunction.getDateFromMillis(file.lastModified());
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getAbsolutePath() {
		return absolutePath;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public int compareTo(ReportFile o) {
		if (this.getCreatedDate().after(o.getCreatedDate()))
			return -1;
		return 1;
	}


}
