package id.base.app.controller.stream;

import id.base.app.SystemConstant;
import id.base.app.dao.parameter.IAppParameterDAO;
import id.base.app.report.ReportUtils;
import id.base.app.util.ReflectionFunction;
import id.base.app.util.StringFunction;
import id.base.app.valueobject.AppParameter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/stream")
public class StreamController {
	@Autowired
	IAppParameterDAO appParameterDAO;
    
    @RequestMapping("/pdfReport/{filename}")
    public void getPdfReport(@PathVariable(value="filename") String fileName, HttpServletResponse response) {
    	InputStream is = null;
    	try {
            is = new FileInputStream(new File(SystemConstant.LOCAL_TEMP_DIRECTORY_REPORT + ReportUtils.fixFileName(fileName, ReportUtils.PDF_EXTENSION)));
            IOUtils.copy(is, response.getOutputStream());
            response.setContentType("application/pdf");
            response.flushBuffer();
          } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
          } finally{
        	  if(is!=null){
        		  IOUtils.closeQuietly(is);
        	  }
          }
    }
    
    @RequestMapping("/pdfReport/{path}/{filename}")
    public void getPdfBySysParamPathAndName(@PathVariable(value="path") String path, @PathVariable(value="filename") String fileName, HttpServletResponse response) {
    	InputStream is = null;
    	String thePath = ""; 
    	try {
    		AppParameter sysPath = appParameterDAO.findAppParameterByName(path);
    		if(sysPath!=null && sysPath.getValue()!=null){
    			thePath = sysPath.getValue();
    		}else{
    			thePath = (String) ReflectionFunction.getPropertyValue(SystemConstant.class, path);
    		}
    		if(StringFunction.isNotEmpty(thePath)){
    			File file = new File(thePath + ReportUtils.fixFileName(fileName, ReportUtils.PDF_EXTENSION));
    			if(file.exists()){
		            is = new FileInputStream(file);
		            IOUtils.copy(is, response.getOutputStream());
		            response.setContentType("application/pdf");
		            response.flushBuffer();
    			}
    		}
          } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
          } finally{
        	  if(is!=null){
        		  IOUtils.closeQuietly(is);
        	  }
          }
    }
    
    @RequestMapping("/excelReport/{filename}")
    public void getExcelReport(@PathVariable(value="filename") String fileName, HttpServletResponse response) {
    	InputStream is = null;
    	try {
            is = new FileInputStream(new File(SystemConstant.LOCAL_TEMP_DIRECTORY_REPORT + ReportUtils.fixFileName(fileName, ReportUtils.XLS_EXTENSION)));
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
          } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
          } finally {
        	  if(is!=null){
        		  IOUtils.closeQuietly(is);
        	  }
          }
    }
    
    @RequestMapping(value = "/txt/{path}/{fileName}", method = RequestMethod.GET)
    public void getText( @PathVariable(value="path") String path,@PathVariable(value="fileName") String fileName, HttpServletResponse response) {
    	InputStream is = null;
        try {
        	// get path name
        	String pathName = "";
        	AppParameter app = appParameterDAO.findAppParameterByName(path);
        	if(null != app){
        		pathName = app.getValue();
        	}
        	pathName = pathName+StringFunction.date2String(new Date(), SystemConstant.SYSTEM_DATE_MASK_NO_DELIMITER)+ SystemConstant.PATH_SEPARATOR;    		
        	is = new FileInputStream(new File(pathName+fileName+".txt"));
			// copy it to response's OutputStream
			IOUtils.copy(is, response.getOutputStream());
			response.setContentType("text/plain");
			response.flushBuffer();
        } catch (IOException ex) {
          throw new RuntimeException("IOError writing file to output stream");
        } finally {
      	  if(is!=null){
    		  IOUtils.closeQuietly(is);
      	  }
        }
    }
}
