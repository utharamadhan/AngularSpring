package id.base.app.util.filetransfer;

import id.base.app.encryptor.XOREncrypter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPUtil extends FileTransferUtil{
//	private static Map<String, FTPUtil> ftpMap = new Hashtable<String, FTPUtil>(); 

	private FTPClient ftp;

	private void connect() throws Exception {
		ftp.connect(server);
		// After connection attempt, you should check the reply code to
		// verify
		// success.
		int reply = ftp.getReplyCode();

		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new IOException("FTP server refused connection.");
		}
	}
	
	private void disconnect() {
		if (ftp != null && ftp.isConnected()) {
			try {
				ftp.disconnect();
			} catch (IOException ioe) {
				// do nothing
			}
		}
	}
	
	private boolean login() throws Exception {
		boolean result = ftp.login(username, password);
		if(result){
			// Use passive mode as default because most of us are
			// behind firewalls these days.
			ftp.enterLocalPassiveMode();
		}
		return result;
	}

	private void logout() {
		try {
			if ( ftp != null) {
				ftp.logout();
			}
		} catch (Exception e) {
			// do nothing
		}
	}
	
	public FTPUtil(){
		
	}

	public FTPUtil(String server, String username, String password){
		this.server		= server;
		this.username	= username;
		//assume FTP clear password
//		this.password	= (new XOREncrypter()).decrypt(password);
		this.password = password;
//		this.password	= password;
		this.ftp 		= new FTPClient();
	}

	public static synchronized FTPUtil getInstance(String server, 
												   String username, 
												   String password) throws Exception {
		FTPUtil instance = new FTPUtil(server, username, password);
		return instance;
	}

	@Override
	public boolean getFile(String remoteDirectory, 
						String localDirectory, 
						String remoteFile, 
						String localFile) throws Exception {
		try {
			boolean result = false;
			connect();
			
			if(login()) {
				ftp.setFileType(FTP.ASCII_FILE_TYPE);

				// transfer files
				/*
				OutputStream output = new FileOutputStream(remoteDirectory+remoteFile);
				result 				= ftp.retrieveFile(localDirectory+localFile, output);
				*/
				
				OutputStream output = new FileOutputStream(localDirectory+localFile);
				result 				= ftp.retrieveFile(remoteDirectory+remoteFile, output);
				
				output.close();
			}

			logout();
			
			System.out.println("get result = "+result);
			return result;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}

	@Override
	public String[] list(String remoteDirectory, 
						 boolean makeDirWhenNotExist,
						 FilenameFilter filenameFilter) throws Exception {
		try {
			connect();
			String[] rslt = null;
			
			if(login()) {
				rslt = ftp.listNames(remoteDirectory);
			}
			
			List<String> ls = new ArrayList<String>();
			if(rslt != null && rslt.length > 0){
//				int dirLen = remoteDirectory.length();
				for(int i=0; i<rslt.length; i++){
					if(filenameFilter.accept(null, rslt[i])){
						//ls.add(rslt[i].substring(dirLen));
						ls.add(rslt[i]);
						System.out.println("==>File : "+rslt[i]);
					}
				}
			}
			
			String[] result = null;
			if(ls.size() > 0){
				result = ls.toArray(new String[ls.size()]);
			}
			logout();
			return result;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}

	@Override
	public boolean makeDirectory(String remoteDirectory) throws Exception {
		try {
			connect();
			
			if(login()) {
				return ftp.makeDirectory(remoteDirectory);
			}

			logout();
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
		return false;
	}

	@Override
	public boolean putFile(String remoteDirectory, String localDirectory, String remoteFile, String localFile) throws Exception {
		System.out.println("==> put file remoteDirectory = "+remoteDirectory+remoteFile+", file = "+localDirectory+localFile);
		InputStream is = new FileInputStream(localDirectory+localFile);
		boolean result = putFile(remoteDirectory+remoteFile, is, FTP.ASCII_FILE_TYPE);
		System.out.println("put result = "+result);
		return result;
	}

	public void copyFile(String localDirectory, String remoteDirectory, String remoteFile, String localFile) throws Exception {
		System.out.println("==> put file remoteDirectory = "+remoteDirectory+remoteFile+", file = "+localDirectory+localFile);
		InputStream is = new FileInputStream(localDirectory+localFile);
		boolean result = putFile(remoteDirectory+remoteFile, is, FTP.ASCII_FILE_TYPE);
		System.out.println("put result = "+result);
	}
	
	@Override
	public boolean putFile(String remoteDirectory, String localDirectory, String remoteFile, String localFile, int fileType) throws Exception {
		File f = new File(localDirectory+localFile);
		System.out.println("==> size of file to copy : "+f.length());				
		InputStream input = new FileInputStream(f);
		return putFile(remoteDirectory+remoteFile, input, fileType);
	}

	private boolean putFile(String remoteDirectory, InputStream is, int fileType) throws Exception {
		boolean result = false;
		try {
			connect();

			if(login()) {
				ftp.setFileType(fileType);
				result = ftp.storeFile(remoteDirectory, is);
				System.out.println(ftp.getReplyCode());
				is.close();
			}

			logout();
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
		return result;
	}

	@Override
	public void removeFile(String remoteDirectory, String file) throws Exception {
		try {
			connect();

			if(login()) {
				ftp.deleteFile(remoteDirectory+file);
			}

			logout();
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}

	@Override
	public boolean renameFile(String oldFile, String newFile) throws Exception {
		try {
			boolean result = false;

			connect();

			if(login()) {
				System.out.println("==> rename from "+oldFile+" to "+newFile);
				result = ftp.rename(oldFile, newFile);
			}
			
			logout();
			return result;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			disconnect();
		}
	}

	@Override
	public void writeTextFile(String remoteDirectory, String fileName, String content, String tempDir) throws Exception {
		String localFile = tempDir + fileName;
		File file = new File(localFile);
		Writer output = new BufferedWriter(new FileWriter(file));
		output.write(content);
		output.close();

		putFile(remoteDirectory, tempDir, fileName, fileName, FTP.BINARY_FILE_TYPE);
		file.delete();
	}

	public FTPClient getFtp() {
		return ftp;
	}

	public void setFtp(FTPClient ftp) {
		this.ftp = ftp;
	}
}
