package id.base.app.util.filetransfer;

import java.io.FilenameFilter;

public abstract class FileTransferUtil {
	protected String server;
	protected String username;
	protected String password;
	protected Integer port;

	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	public abstract boolean putFile(String remoteDirectory, String localDirectory, String remoteFile, String localFile, int fileType) throws Exception;
	public abstract boolean putFile(String remoteDirectory, String localDirectory, String remoteFile, String localFile) throws Exception;
	public abstract void removeFile(String remoteDirectory, String file) throws Exception;
	public abstract boolean makeDirectory(String remoteDirectory) throws Exception;
	public abstract boolean getFile(String remoteDirectory, 
								 	String localDirectory, 
								 	String remoteFile, 
								 	String localFile) throws Exception;
	public abstract boolean renameFile(String oldFile, String newFile) throws Exception;
	public abstract String[] list(String remoteDirectory, 
								  boolean makeDirWhenNotExist, 
								  FilenameFilter filenameFilter) throws Exception;
	public abstract void writeTextFile(String remoteDirectory, String fileName, String content, String tempDir)throws Exception;	
}
