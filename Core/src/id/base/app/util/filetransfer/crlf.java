package id.base.app.util.filetransfer;

/*
 * @(#)crlf.java	1.00 7th Jan 1999
 * 
 * Modification Log:
 *
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.LineNumberReader;


/**
 * How many times have you transferred files from a UNIX machine to 
 * Windows or vice versa and then discovered that you did a binary 
 * transfer by mistake? What do you do then? You can ftp them again!
 * What about downloaded files? I can give you one example - JDK 
 * sources from Sun. They always come with UNIX style line feeds only. 
 * Viewing them with notepad becomes a pain.
 * So here's an utility to make life simpler. Just run this utility 
 * against your file and bingo! all those black boxes and ^M are gone.
 * <p><code>
 * Usage: <br>
 *        java crlf [-template template] [-recurse] file1 file2 ...<br>
 *        template can be a simple wild card with '*' and '?'<br>
 *        recurse option enables resursing sub-directories<br>
 *        filename can be name of a file or a directory<br>
 * </code></p>
 *
 * You are free to use this code and to make modifications provided
 * this notice is retained.
 * <p>
 * If you found this useful, please add a note of acknowledgement to my guestbook,
 * or send me a mail at <a href="mailto:tanmaykm@hotmail.com">tanmaykm@hotmail.com</a>. 
 * If you would like to report a bug or suggest some improvements, 
 * you are most welcome. I will be happy to help you use this piece of code.
 * <p>
 *
 * @author 	    Tanmay K. Mohapatra
 * @version     1.00, 7th Jan, 1999
 */

public class crlf implements FilenameFilter
{
    File   dir;
    String sTemplate;
    boolean bFiles, bDirs;
    
    /**
     * The same class acts as a FilenameFilter. This is the constructor for that.
     */
    public crlf(File dir, String sTemplate, boolean bFiles, boolean bDirs)
    {
        this.dir = dir;
        this.sTemplate = sTemplate;
        this.bFiles = bFiles;
        this.bDirs = bDirs;
    }

    /**
     * Accept returns true if the file passes the filter.
     */
    public boolean accept(File dir, String sFileName)
    {
        if (dir.equals(this.dir))
        {
            File checkFile = new File(dir, sFileName);
            if ( (checkFile.isDirectory() && !bDirs) ||
                 (checkFile.isFile() && !bFiles) )
            {
                return false;
            }
            // check wild card
            return matchWildCard(sFileName, sTemplate);
        }
        return false;
    }

    /**
     * Function matches simple wild cards containing '?' or '*'
     */
    public boolean matchWildCard(String sFullString, String sPartialExp)
    {
        int iFullStrIndx, iPartExpIndx;
        int iFullStrLen, iPartExpLen;
        
        if ((null == sFullString) || (null == sPartialExp) || 
            (0==(iFullStrLen=sFullString.length())) || 
            (0==(iPartExpLen=sPartialExp.length())))
        {
            return false;
        }

        for (iFullStrIndx=iPartExpIndx=0; iPartExpIndx != iPartExpLen; iFullStrIndx++, iPartExpIndx++)
        {
            if (iFullStrIndx == iFullStrLen)
            {
                return (sPartialExp.charAt(iPartExpIndx)=='*' && ((iPartExpIndx+1)==iPartExpLen));
            }

            switch (sPartialExp.charAt(iPartExpIndx))
            {
                case '?':
                    break;

                case '*':
                    iPartExpIndx++;
                    if (iPartExpIndx == iPartExpLen)
                        return true;
                    if (-1 == (iFullStrIndx=sFullString.indexOf(sPartialExp.charAt(iPartExpIndx))))
                        return false;
                    break;

                default:
                    if (sPartialExp.charAt(iPartExpIndx) != sFullString.charAt(iFullStrIndx))
                        return false;
                    break;
            }
        }

        return (iFullStrIndx == iFullStrLen);
    }
    
    
	/**
	 * @param args Array of file names to scan.
	 */
	public static void main (String[] args)
	{
        String  sLineSep;
        int     iIndex;
        String  sFileTemplate = "*";
        boolean bRecurse = false;
        
        // no file names
        if (args.length < 1)
        {
            System.out.println("Usage: java crlf [-template template] [-recurse] file1 file2 ...");
            System.out.println("       template can be a simple wild card with '*' and '?'");
            System.out.println("       recurse option enables resursing sub-directories");
            System.out.println("       filename can be name of a file or a directory");
            System.exit(-1);
        }

        sLineSep=System.getProperty("line.separator");
        if ("\n".equals(sLineSep))
        {
            System.out.println("LineSep=\\n");
        }
        else
        {
            System.out.println("LineSep=\\r\\n");
        }
        
        for(iIndex=0; iIndex<args.length; iIndex++)
        {    
            // check for template
            if ("-template".equalsIgnoreCase(args[iIndex]))
            {
                iIndex++;
                if (iIndex < args.length)
                {
                    sFileTemplate = args[iIndex];
                    System.out.println("Template set to " + sFileTemplate);
                }
            }
            // check for recurse
            else if("-recurse".equalsIgnoreCase(args[iIndex]))
            {
                System.out.println("Recurse sub-directories");
                bRecurse = true;
            }
            // must be a file/directory name
            else
            {
                processFile(args[iIndex], sFileTemplate, sLineSep, bRecurse, true);
            }
        }
	}
    
    /**
     * process this file or if it is a directory then process the files within that.
     */
    public static void processFile(String sFileName, String sFileTemplate, String sLineSep, boolean bRecurse, boolean bFirstLevel)
    {
        File    thisFile;
            
        thisFile = new File(sFileName);
        
        if (thisFile.isDirectory())
        {
            if (bFirstLevel || bRecurse)
            {
                FilenameFilter filter;
                String [] allFiles; 
                
                filter = new crlf(thisFile, sFileTemplate, true, false);
                allFiles = thisFile.list(filter);
                // process all files in the directory
                for (int iIndex = allFiles.length; iIndex > 0; iIndex--)
                {
                    File fullFile = new File(thisFile, allFiles[iIndex-1]);
                    processFile(fullFile.getAbsolutePath(), sFileTemplate, sLineSep, bRecurse, false);
                }
                
                if (bRecurse)
                {
                    // recurse to directories
                    filter = new crlf(thisFile, "*", false, true);
                    allFiles = thisFile.list(filter);
                    // process all directories in the directory
                    for (int iIndex = allFiles.length; iIndex > 0; iIndex--)
                    {
                        File fullFile = new File(thisFile, allFiles[iIndex-1]);
                        processFile(fullFile.getAbsolutePath(), sFileTemplate, sLineSep, bRecurse, false);
                    }
                }
            }
        }
        else
        {
            System.out.println(sFileName);
            crlf.convertFile(sFileName, sLineSep);
        }
    }
    
    public static void convertFile(String sFileName, String sLineSep)
    {
    	convertFile(sFileName, sLineSep, sFileName);
    }
    /**
     * Convert all line separators in this file.
     */
    public static void convertFile(String sFileName, String sLineSep, String sNewFileName)
    {
        LineNumberReader lineRead;
        FileWriter       fileWrite;
        String           sLine;
        File             workFile, sourceFile;
    
        try
        {
            lineRead = new LineNumberReader(new FileReader(sFileName));
            if (sNewFileName.equals(sFileName)) {
            	fileWrite = new FileWriter(sFileName + ".crlf.tmp");
            }
            else {
            	fileWrite = new FileWriter(sNewFileName, false);
            }
            
            int iCount = 0;
            sLine = lineRead.readLine();
            
            // read lines and write back. simple!!
            while (null != sLine)
            {
            	iCount++;
            	if (iCount>1) {
            		fileWrite.write(sLineSep);
            	}
                fileWrite.write(sLine);
//                fileWrite.write(sLineSep);
                
                sLine = lineRead.readLine();
            }
            
            lineRead.close();
            fileWrite.close();
            
            if (sNewFileName.equals(sFileName)) {
	            workFile = new File(sFileName + ".crlf.tmp");
	            sourceFile = new File(sFileName);
	            sourceFile.delete();
	            workFile.renameTo(sourceFile);
            }
            
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
    }
}

