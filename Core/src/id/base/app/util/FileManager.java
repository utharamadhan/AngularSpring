package id.base.app.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileManager {
	/**
     * This method deletes a file.
     * @param filename Name of the file to be deleted.
     * @return Success of delete.
     */
	
	private static final Logger logger = LoggerFactory.getLogger(FileManager.class);
	
    public static boolean deleteFile(String filename) {
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
    
    public static ZipInputStream getZipInputStream(InputStream inputStream) {
		return new ZipInputStream(new BufferedInputStream(inputStream));
	}
	
	public static ZipInputStream getZipInputStream(File fileInputStream) {
		try {
			return getZipInputStream(new FileInputStream(fileInputStream));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static File createFolder(File folder) {
		if (!folder.exists() || folder.isFile()) {
			folder.mkdir();
		}
		return folder;
	}
	public static void  createFolderList(File file) {
		String path = file.getAbsolutePath();
		int index = -1;
		while((index = path.indexOf("\\", index)) != -1) {
			createFolder(new File(path.substring(0, index)));
			index = index + 1;
		}
	}
    
    public static void extraxctZipFile(File parent, InputStream inputStream) throws FileNotFoundException, IOException {
		//List<String> listExtractedFile = new ArrayList();
		ZipInputStream zipIn = getZipInputStream(inputStream);
		ZipEntry zipEntry = null;
		FileOutputStream out = null;
		try{			
			while ((zipEntry = zipIn.getNextEntry()) != null) {
				//listExtractedFile.add(zipEnty.getName());
				File fileOut = new File(parent, zipEntry.getName());
				createFolderList(fileOut);
				
				if (zipEntry.isDirectory()) {
					// if zipEntry is directory, we need to make one here
					if (!fileOut.exists()) {
						fileOut.mkdir();
					}
				} else {
					out = new FileOutputStream(fileOut);				
					byte[] buffer = new byte[512];
					int read = -1;
					while ((read = zipIn.read(buffer, 0, 512)) != -1) {
						out.write(buffer, 0, read);
						out.flush();
					}
					closeStream(out);
				}
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e; 
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}finally{
			closeStream(zipIn);
			closeStream(out);
		}
	}
    
    
    public static void closeStream(InputStream inputStream) throws IOException {
		if (inputStream != null) {
			inputStream.close();
			inputStream =  null;
		}
	}
	
	public static void closeStream(OutputStream outputStream) throws IOException {
		if (outputStream != null) {
			outputStream.close();
			outputStream =  null;
		}
	}	
    public static boolean createDir(String dirname) {
        File f = new File(dirname);
        boolean result = false;
        try {
            result = f.mkdirs();
        }
        catch (Exception ex) { 
        	ex.printStackTrace();
        }
        return result;
    }
    
    public static boolean renameFile(String filename, String newfilename) {
        File f = new File(filename);
        File nf = new File(newfilename);
        boolean result = false;
        try {
            result = f.renameTo(nf);            
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
        return result;
    }
    
    public static boolean copyFile(String filename, String newfilename) {
        boolean result = false;
        try {
            File out = new File(newfilename);
            InputStream fis = new FileInputStream(filename);
            OutputStream fos = new FileOutputStream(out);
            int buffer = 0;
            while ((buffer = fis.read()) != -1) {
                fos.write(buffer);
            }
            fis.close();
            fos.close();
            result = true;
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
        return result;
    }
    
    public static boolean copyDir(String dirname, String newdir) {
        boolean result = false;
        try {
            createDir(newdir);
            File f = new File(dirname);
            if (f.isDirectory()) {
                String[] files = f.list();
                for (int i=0; i<files.length; i++) {
                    File file = new File(dirname + "/" + files[i]);
                    if (file.isDirectory())
                        copyDir(f.getAbsolutePath() + "/" + files[i], newdir + "/" + files[i]);  
                    else
                        copyFile(f.getAbsolutePath() + "/" + files[i], newdir + "/" + files[i]);
                }
            } else {
                copyFile(dirname, newdir);
            }
            result = true;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return result;
    }
    
    public static boolean deleteDir(String dirname) {
        boolean result = false;
        try {
            File f = new File(dirname);
            if (f.isDirectory()) {
                String[] files = f.list();
                if(files.length>0){
                	 for (int i=0;i<files.length;i++) {
                         deleteDir(f.getAbsolutePath() + "/" + files[i]);
                         deleteFile(dirname);
                     } 	
                }else{
                    deleteFile(dirname);
                }
            } else {
                deleteFile(dirname);
            }
            result = true;
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
        return result;
    }
    
    public static boolean exists(String filename) {
    	try {
    		File file = new File(filename);
    		return file.exists();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public static long getSize(String filename) {
        File f = new File(filename);
        if (f.length() > 1024)
            return f.length();
        else
            return 1024;
    } 
    
    public static StringBuffer readFile(String filename) {
        StringBuffer content = new StringBuffer();
        try {
            File file = new File(filename);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int ichar = 0;
            while ((ichar = bis.read()) > 0) {
                content.append((char)ichar);
            }
        } catch (Exception e) {
        }
        return content;
    }
    
    public static void writeFile(InputStream in, String filepath) {
        try {
            OutputStream os = new FileOutputStream(filepath);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.close(); 
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static void copyFile(File file, String dest) {
    	try{
    	     File f2 = new File(dest);
    	     InputStream in = new FileInputStream(file);
    	     OutputStream out = new FileOutputStream(f2);    	     
    	     while (true){
    	    	 int data = in.read();
    	    	 if(data==-1){
    	    		 break;
    	    	 }    	   
    	    	 out.write(data);
    	     }
    	     in.close();
    	     out.close();
    	     logger.debug("File copied to {}", dest);
    	}
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static void writeFile(String text, String filepath) {
        try {
            OutputStream bos = new FileOutputStream(filepath);
            bos.write(text.getBytes());
            bos.close(); 
        }
        catch (Exception e) {
        	e.printStackTrace();
        }        
    }
    
    public static void appendFile(String text, String filepath) {
    	try {
    		OutputStream bos = new FileOutputStream(filepath, true);
    		bos.write(text.getBytes());
    		bos.close();    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
	/** delete all file before generated new one 
	 * @param dir
	 */
	public static void flushAllFileFromFolder(String dirname) {
		 File dir = new File(dirname);
	    for (File file: dir.listFiles()) {
	        if (!file.isDirectory()){
	        	file.delete();
	        }
	    }
	}
    
  
}
