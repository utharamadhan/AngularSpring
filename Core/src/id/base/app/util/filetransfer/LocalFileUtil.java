package id.base.app.util.filetransfer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.Writer;
import java.nio.channels.FileChannel;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalFileUtil extends FileTransferUtil{
    private static final Logger logger = LoggerFactory.getLogger(LocalFileUtil.class);

    private void copyFile(String fromFolder, String toFolder, String fromFile, String toFile) throws Exception {
    	File folder				= new File(toFolder);
    	if(!folder.exists()){
    		folder.mkdirs();
    	}
    	File in					= new File(fromFolder + fromFile); 
    	File out				= new File(toFolder + toFile);
        FileChannel inChannel 	= new FileInputStream(in).getChannel();
        FileChannel outChannel 	= new FileOutputStream(out).getChannel();
        
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
            throw e;
        } finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }

    private boolean deleteFile(String filename) {
        File f = new File(filename);
        boolean result = false;
        try {
            result = f.delete();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
	public boolean putFile(String remoteDirectory, String localDirectory, String remoteFile, String localFile) throws Exception {
		copyFile(localDirectory, remoteDirectory, localFile, remoteFile);
	    return true;
	}

	@Override
	public void removeFile(String remoteDirectory, String file) throws Exception {
    	deleteFile(remoteDirectory + file); 
	}

	@Override
	public boolean makeDirectory(String remoteDirectory) throws Exception {
		File folder				= new File(remoteDirectory);
    	if(!folder.exists()){
    		folder.mkdirs();
    	}
    	return true;
	}

	@Override
	public boolean getFile(String remoteDirectory, 
						String localDirectory, 
						String remoteFile, String localFile) throws Exception {
    	File folder				= new File(localDirectory);
    	if(!folder.exists()){
    		folder.mkdirs();
    	}
    	File in					= new File(remoteDirectory + remoteFile); 
    	File out				= new File(localDirectory + localFile);
        FileChannel inChannel 	= new FileInputStream(in).getChannel();
        FileChannel outChannel 	= new FileOutputStream(out).getChannel();
        
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
            throw e;
        } finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
	    return true;
	}

	@Override
	public boolean renameFile(String oldFile, String newFile) throws Exception {
		File f1 = new File(oldFile);
		File f2 = new File(newFile);

		logger.info("File " + f1.getName() + " exists ? " + (f1.exists() ? "yes" : "no"));
		
		return f1.renameTo(f2);
	}

	@Override
	public String[] list(String remoteDirectory, 
						 boolean makeDirWhenNotExist, 
						 FilenameFilter filenameFilter) throws Exception {
		String[] fileNames = null;
		File folder = new File(remoteDirectory);
		if(!folder.exists()){
			if(makeDirWhenNotExist){
				folder.mkdirs();
			}
		}else{
			fileNames = folder.list(filenameFilter);
		}
		return fileNames;
	}

	@Override
	public void writeTextFile(String remoteDirectory, String fileName, String content, String tempDir)throws Exception {
//		boolean ifDirectoryExists = makeDirectory(remoteDirectory);
		
		File file = new File(remoteDirectory + fileName);
		
//		if(ifDirectoryExists) {
			Writer output = new BufferedWriter(new FileWriter(file));
			output.write(StringUtils.trimToEmpty(content));
			output.close();
//		}
	}	

	@Override
	public boolean putFile(String remoteDirectory, String localDirectory, String remoteFile, String localFile, int fileType) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
