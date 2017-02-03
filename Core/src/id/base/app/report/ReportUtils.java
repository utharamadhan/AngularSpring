package id.base.app.report;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReportUtils {
	
	private static Logger logger=LoggerFactory.getLogger(ReportUtils.class);
	public static final int EXPORT_TO_PDF 		= 1;
	public static final int EXPORT_TO_EXCEL 	= 2;
	public static final int EXPORT_TO_TXT 	= 3;
	public static final String PDF_EXTENSION 	= "pdf";
	public static final String XLS_EXTENSION 	= "xls";
	public static final String XLSX_EXTENSION	= "xlsx";
	public static final String TXT_EXTENSION 	= "txt";
	
	public static final Map<String,String> mediaTypeMap = new HashMap<String,String>();
	static{
		mediaTypeMap.put(TXT_EXTENSION, "text/plain");
		mediaTypeMap.put(PDF_EXTENSION, "application/pdf");
		mediaTypeMap.put(XLS_EXTENSION, "application/vnd.ms-excel");
		mediaTypeMap.put(XLSX_EXTENSION, "application/vnd.ms-excel");
	}
	
	public static List<ReportFile> listReportFiles(String reportDirectory) {
		List<ReportFile> reportFileList = new LinkedList<ReportFile>(); 
		File directory = new java.io.File(reportDirectory);
		if(!directory.isDirectory()) throw new RuntimeException( reportDirectory + " is not directory !! ");
		File[] reportFiles = directory.listFiles();
		if(reportFiles.length>0){
		   for (int i = 0; i < reportFiles.length; i++) {
			   reportFileList.add(new ReportFile(reportFiles[i]));
		   }
		}
		Collections.sort(reportFileList);
		return reportFileList;
	}
	
	public static List<ReportFile> listReportFiles(String reportDirectory,String merchantCode) {
		List<ReportFile> reportFileList = new LinkedList<ReportFile>(); 
		logger.debug("Folder to read : [{}]",reportDirectory+merchantCode);
		String reportDir = reportDirectory + merchantCode;
		File directory = new java.io.File(reportDir);
		if(!directory.isDirectory()) {
			directory.mkdir();
			logger.debug("create folder with name [{}]",directory.getName());
		}
		File[] reportFiles = directory.listFiles();
		if(reportFiles.length>0){
		   for (int i = 0; i < reportFiles.length; i++) {
			   reportFileList.add(new ReportFile(reportFiles[i]));
		   }
		}
		Collections.sort(reportFileList);
		return reportFileList;
	}
	
	public static JasperReport loadReport(InputStream is) throws Exception{
		try {
			JasperDesign design = JRXmlLoader.load(is);
			return JasperCompileManager.compileReport(design);
		} finally {
			//is.close();
		}
	}
	
	/**
	 * Export data (JasperPrint) to PDF File
	 * fileTargetLocation is destination location of generated file
	 * @author Utha
	 */
	public static void exportReportPdfFile(JasperPrint jasperPrint, String fileTargetLocation) throws JRException {
		JasperExportManager.exportReportToPdfFile(jasperPrint, fixFileName(fileTargetLocation, PDF_EXTENSION));	
	}
	
	/**
	 * Export data (JasperPrint) to Excel file
	 * fileTargetLocation is destination location of generated file
	 * @author Utha
	 */
	public static void exportReportXlsFile(JasperPrint jasperPrint, String fileTargetLocation) throws JRException {
		JRXlsExporter exporter = new JRXlsExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fixFileName(fileTargetLocation, XLS_EXTENSION)));

        exporter.exportReport();
	}
	
	/**
	 * fix pdf file name.
	 * append '.pdf' extension if file name is has not pdf extension yet
	 * original code by ko Willy
	 */
	public static String fixFileName(String fileName, String extension){
		if(FilenameUtils.isExtension(fileName, extension)){
			return fileName;
		}else{
			return fileName+"."+extension;
		}
	}
}
