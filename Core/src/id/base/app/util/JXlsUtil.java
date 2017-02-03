package id.base.app.util;

import id.base.app.SystemParameter;
import id.base.app.config.BeanUtilsConfigurer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.ReaderConfig;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public  class JXlsUtil<T> {
	private static Logger logger = LoggerFactory.getLogger(JXlsUtil.class);
	
	public List<T> read(InputStream xlsDataInputStream,InputStream xlsXmlTemplateStream,String mapName,boolean skipError) throws IOException , SAXException, InvalidFormatException {
		InputStream inputXML = new BufferedInputStream(xlsXmlTemplateStream);
		
        XLSReader mainReader = ReaderBuilder.buildFromXML( inputXML );
        
        InputStream inputXLS = new BufferedInputStream(xlsDataInputStream);
        List<T> list = new LinkedList<T>();
        Map<String, List<T>> beans = new HashMap<String,  List<T>>();
        beans.put(mapName,list);
        ReaderConfig.getInstance().setSkipErrors( skipError );
        XLSReadStatus readStatus = mainReader.read( inputXLS, beans);
        readStatus.getReadMessages();
        // reset beanutils' converter setting back to default
        BeanUtilsConfigurer.configure();
		return list ;
	}

	public byte[] write(String templateFileName, List<?> listData) throws IOException, RuntimeException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			byte[] a = null;
			Map beans = new HashMap();
			beans.put("detail", listData);
			
			String directory = SystemParameter.TEMPLATE_DIRECTORY;
			if (!(directory.endsWith("/") || directory.endsWith("\\"))) {
				directory += "/";
			}
			System.out.println("directory --> "+directory);
			System.out.println("directory --> "+templateFileName);
			FileInputStream stream = new FileInputStream(directory + templateFileName);
			XLSTransformer transformer = new XLSTransformer();
			
			Workbook wb = transformer.transformXLS(stream, beans);
			
			wb.write(bos);
			bos.close();
		} catch (Exception e) {
			logger.error("Error saat ekspor excel", e);
			throw new RuntimeException("Error saat ekspor excel", e);
		}
		// reset beanutils' converter setting back to default
        BeanUtilsConfigurer.configure();
		return bos.toByteArray();
	}
	
	public byte[] write(String templateFileName, List<?> listData, String objName) throws IOException, RuntimeException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			byte[] a = null;
			Map beans = new HashMap();
			if(objName==null){
				beans.put("detail", listData);
			}else{
				beans.put(objName, listData);
			}
			
			String directory = SystemParameter.TEMPLATE_DIRECTORY;
			if (!(directory.endsWith("/") || directory.endsWith("\\"))) {
				directory += "/";
			}
			FileInputStream stream = new FileInputStream(directory + templateFileName);
			XLSTransformer transformer = new XLSTransformer();
			
			Workbook wb = transformer.transformXLS(stream, beans);
			
			wb.write(bos);
			bos.close();
		} catch (Exception e) {
			logger.error("Error saat ekspor excel", e);
			throw new RuntimeException("Error saat ekspor excel", e);
		}
		// reset beanutils' converter setting back to default
        BeanUtilsConfigurer.configure();
		return bos.toByteArray();
	}
	
	public byte[] write(String templateFileName,Map<String, List<?>> beans) throws IOException, RuntimeException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			byte[] a = null;
			
			String directory = SystemParameter.TEMPLATE_DIRECTORY;
			if (!(directory.endsWith("/") || directory.endsWith("\\"))) {
				directory += "/";
			}
			FileInputStream stream = new FileInputStream(directory + templateFileName);
			XLSTransformer transformer = new XLSTransformer();
			
			Workbook wb = transformer.transformXLS(stream, beans);
			
			wb.write(bos);
			bos.close();
		} catch (Exception e) {
			logger.error("Error saat ekspor excel", e);
			throw new RuntimeException("Error saat ekspor excel", e);
		}
		// reset beanutils' converter setting back to default
        BeanUtilsConfigurer.configure();
		return bos.toByteArray();
	}
	
	public void write(String template ,String outputdir,Map beans) throws IOException, RuntimeException {
		OutputStream bos = new BufferedOutputStream(new FileOutputStream(outputdir));
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(template));
			XLSTransformer transformer = new XLSTransformer();
			Workbook wb = transformer.transformXLS(is, beans);
			wb.write(bos);
		} catch (Exception e) {
			logger.error("Error saat ekspor excel", e);
			throw new RuntimeException("Error saat ekspor excel", e);
		}finally {
			bos.flush();
			bos.close();
		}
		// reset beanutils' converter setting back to default
        BeanUtilsConfigurer.configure();
	}
	
	public byte[] write(InputStream xlsDataInputStream, Map<String, List<?>> beans) throws IOException, ParsePropertyException, InvalidFormatException {
		XLSTransformer transformer = new XLSTransformer();
		Workbook wb = transformer.transformXLS(xlsDataInputStream, beans);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		wb.write(bos);
		bos.close();
		BeanUtilsConfigurer.configure();
		return bos.toByteArray();
	
	}
}
