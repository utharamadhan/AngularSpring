package id.base.app.util.filetransfer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPUtil extends FileTransferUtil{
	private static Map<String, SFTPUtil> sftpMap = new Hashtable<String, SFTPUtil>();

	private JSch jsch;

	private boolean putFile(String remoteDirectory, String remoteFile, InputStream is) throws Exception {
		 jsch = new JSch();
		 Session session = openSession();
	     Channel channel = session.openChannel("sftp");
	     channel.connect();
	     ChannelSftp channelSftp = (ChannelSftp) channel;
	     
	     channelSftp.put(is, remoteDirectory+remoteFile);
	     
	     closeSession(channelSftp, channel, session);

	     return true;
	}

	public SFTPUtil(){
		
	}
	
	public SFTPUtil(String server, 
					String username, 
					String password, 
					Integer port){
		this.server		= server;
		this.username	= username;
		//this.password	= (new XOREncryptor()).decrypt(password);
		this.password 	= password;
		this.port		= port;
		this.jsch		= new JSch();
	}

	public static synchronized SFTPUtil getInstance(String server, 
													String username, 
													String password, 
													Integer port) throws IOException {
		SFTPUtil instance = new SFTPUtil(server, username, password, port);
		return instance;
	}

	private Session openSession() throws Exception{
		 this.jsch = new JSch();
		 Session session = jsch.getSession(username, server, port);
		 session.setPassword(password);
         session.setConfig("StrictHostKeyChecking", "no");
         /*
          * this time out set 2 minute for handle when connection has problem but session is still connected,
          * with this configuration system will close this connection when session has idle for more than 2 minute
          */
         session.setTimeout(2 * 60 * 1000);
         session.connect();
         System.out.println("Session connected");
		 return session;
	}	
	
	private void closeSession(ChannelSftp channelSftp, Channel channel, Session session){
		try {
			if(channelSftp != null){
				//channelSftp.quit();
				channelSftp.disconnect();
			}
		} catch (Exception e) {
		}
		
		try {
			if(channel != null){
				channel.disconnect();
			}
		} catch (Exception e) {
		}
		
		try {
			if(session != null){
				session.disconnect();
			}
		} catch (Exception e) {
		}
		System.out.println("session closed...");
	}
	
	@Override
	public boolean getFile(String remoteDirectory, 
						String localDirectory, 
						String remoteFile, 
						String localFile) throws Exception {
		 Session session = openSession();
	     Channel channel = session.openChannel("sftp");
	     channel.connect();
	     System.out.println("connecting to remote");
	     ChannelSftp channelSftp = (ChannelSftp) channel;
	     System.out.println("opening channel");
	     try {
	    	 System.out.println("try to get from="+remoteDirectory+remoteFile);
	    	 System.out.println("send to ="+localDirectory+localFile);
		     channelSftp.get(remoteDirectory+remoteFile, localDirectory+localFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	     
	     closeSession(channelSftp, channel, session);

	     return true;
	}

	@Override
	public String[] list(String remoteDirectory, 
						 boolean makeDirWhenNotExist, 
						 FilenameFilter filenameFilter) throws Exception {
		 String[] result = null;
		 
		 Session session = openSession();
	     Channel channel = session.openChannel("sftp");
	     channel.connect();
	     ChannelSftp channelSftp = (ChannelSftp) channel;
	     
	     Vector list = channelSftp.ls(remoteDirectory);
	     if(list != null && list.size() > 0){
	    	 List<String> names = new ArrayList<String>();
		     for(int i=0; i<list.size(); i++){
                Object obj = list.elementAt(i);
                if(obj instanceof ChannelSftp.LsEntry){
                	String fn = ((com.jcraft.jsch.ChannelSftp.LsEntry)obj).getFilename();
                	if(filenameFilter.accept(null, fn)){
                        System.out.println("==> fn : "+fn);
                    	names.add(fn);
                	}
                }
		     }
		     if(names.size() > 0){
		    	 result = names.toArray(new String[names.size()]);   
		     }
		 }
	     
	     closeSession(channelSftp, channel, session);
		
	     return result;
	}

	@Override
	public boolean makeDirectory(String remoteDirectory) throws Exception {
		 Session session = openSession();
	     Channel channel = session.openChannel("sftp");
	     channel.connect();
	     ChannelSftp channelSftp = (ChannelSftp) channel;
	     
	     channelSftp.mkdir(remoteDirectory);

	     closeSession(channelSftp, channel, session);
	     
	     return true;
	}

	@Override
	public boolean putFile(String remoteDirectory, String localDirectory, String remoteFile, String localFile) throws Exception {
		InputStream is = new FileInputStream(localDirectory+localFile);
		 System.out.println("==> copy file "+localDirectory+localFile+" to "+remoteDirectory+remoteFile);

		return putFile(remoteDirectory, remoteFile, is);
	}

	@Override
	public void removeFile(String remoteDirectory, String file) throws Exception {
		 Session session = openSession();
	     Channel channel = session.openChannel("sftp");
	     channel.connect();
	     ChannelSftp channelSftp = (ChannelSftp) channel;
	     
		 System.out.println("==> remove file "+remoteDirectory+file);
	     channelSftp.rm(remoteDirectory+file);
	     
	     closeSession(channelSftp, channel, session);
	}

	@Override
	public boolean renameFile(String oldFile, String newFile) throws Exception {
		 this.jsch = new JSch();
		 Session session = openSession();
	     Channel channel = session.openChannel("sftp");
	     channel.connect();
	     ChannelSftp channelSftp = (ChannelSftp) channel;
	     
		 System.out.println("==> rename from "+oldFile+" to "+newFile);
	     channelSftp.rename(oldFile, newFile);
	     
	     closeSession(channelSftp, channel, session);

	     return true;
	}

	@Override
	public void writeTextFile(String remoteDirectory, String fileName, String content, String tempDir) throws Exception {
		String localFile = tempDir + fileName;
		File file = new File(localFile);
		Writer output = new BufferedWriter(new FileWriter(file));
		output.write(content);
		output.close();

		putFile(remoteDirectory, tempDir, fileName, fileName);
		file.delete();
	}

	@Override
	public boolean putFile(String remoteDirectory, String localDirectory, String remoteFile, String localFile, int fileType) throws Exception {
		return false;
	}
}
