package id.base.app.report;

import id.base.app.SystemConstant;
import id.base.app.util.FileManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleTextReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextException;

public class JasperExporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JasperExporter.class);
	
	public static final String DATA_SOURCE = "JAVA_BEAN_DATA_SOURCE";
	public static final String DATA_SOURCE_CHILD = "JAVA_BEAN_DATA_SOURCE_CHILD";
	
	protected JasperReport jasperReport;
	protected JasperReport jasperReportChild;
	protected Map<String, JasperReport> subReports;
	
	public JasperExporter() {
		// TODO Auto-generated constructor stub
	}
	
	protected final JasperReport loadReport(String fileName) {
		try {
			String fileLocation = SystemConstant.DEFAULT_REPORT_TEMPLATE_LOCATION+"/"+fileName;
			if (fileName.endsWith(".jasper")) {
				// Load pre-compiled report.
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Loading pre-compiled Jasper Report from "
							+ URLEncoder.encode(StringUtils.trimToEmpty(fileName), "UTF-8"));
				}
				InputStream is = getClass().getClassLoader().getResourceAsStream(fileLocation);
				try {
					return (JasperReport) JRLoader.loadObject(is);
				} finally {
					is.close();
				}
			} else if (fileName.endsWith(".jrxml")) {
				// Compile report on-the-fly.
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Compiling Jasper Report loaded from "
							+ URLEncoder.encode(StringUtils.trimToEmpty(fileName), "UTF-8"));
				}
				InputStream is = getClass().getClassLoader().getResourceAsStream(fileLocation);
				try {
					JasperDesign design = JRXmlLoader.load(is);
					return JasperCompileManager.compileReport(design);
				} finally {
					is.close();
				}
			} else {
				throw new IllegalArgumentException("Report filename ["
						+ fileName + "] must end in either .jasper or .jrxml");
			}
		} catch (IOException ex) {
			throw new ApplicationContextException(
					"Could not load JasperReports report from " + fileName, ex);
		} catch (JRException ex) {
			throw new ApplicationContextException(
					"Could not parse JasperReports report from " + fileName, ex);
		}
	}
	
	public JasperExporter(String jasperReportFile) {
		if (jasperReportFile == null){
			throw new IllegalArgumentException(" jasperReportFile is null ");
		}
		jasperReport = loadReport(jasperReportFile);
	}
	
	public JasperExporter(String jasperReportFile, String jasperReportChildFile) {
		if (jasperReportFile == null){
			throw new IllegalArgumentException(" jasperReportFile is null ");
		}
		jasperReport = loadReport(jasperReportFile);
		
		if (jasperReportChildFile != null){
			jasperReportChild = loadReport(jasperReportChildFile);
		}
	}
	
	public JasperExporter(String jasperReportFile, Map<String, String> subReportsFile) {
		if (jasperReportFile == null)
			throw new IllegalArgumentException(" jasperReportFile is null ");
		jasperReport = loadReport(jasperReportFile);
			
		if (subReportsFile != null){
			subReports = new HashMap<String, JasperReport>();
			for (Entry<String, String> subreport : subReportsFile.entrySet()) {
				subReports
						.put(subreport.getKey(), loadReport(subreport.getValue()));
			}	
		}
	}
	
	public JasperExporter(String jasperReportFile, String jasperReportChildFile, Map<String, String> subReportsFile) {
		if (jasperReportFile == null)
			throw new IllegalArgumentException(" jasperReportFile is null ");
		jasperReport = loadReport(jasperReportFile);
		
		if (jasperReportChildFile != null){
			jasperReportChild = loadReport(jasperReportChildFile);
		}
			
		if (subReportsFile != null){
			subReports = new HashMap<String, JasperReport>();
			for (Entry<String, String> subreport : subReportsFile.entrySet()) {
				subReports
						.put(subreport.getKey(), loadReport(subreport.getValue()));
			}	
		}
	}
		
	public void exportPdf(Map<String, Object> model, String outputFile)
			throws Exception {
		JasperPrint jasperPrint;
		try {
			JRPdfExporter exporter = new JRPdfExporter();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("exporter is " + exporter.toString());
			}
			
			createSharedDirectory();
			outputFile = SystemConstant.LOCAL_TEMP_DIRECTORY_REPORT+ReportUtils.fixFileName(outputFile,ReportUtils.PDF_EXTENSION);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("print location at " + URLEncoder.encode(StringUtils.trimToEmpty(outputFile), "UTF-8"));
			}
			if(subReports!=null){
				model.putAll(subReports);
			}
			JRAbstractLRUVirtualizer virtualizer = null;
			virtualizer = new JRGzipVirtualizer(2);
			model.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					model, new JRBeanCollectionDataSource((List<Object>)model.get(DATA_SOURCE)));
			SimpleExporterInput input = new SimpleExporterInput(jasperPrint);
			SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(outputFile);
			exporter.setExporterInput(input);
			exporter.setExporterOutput(output);
			exporter.exportReport();
		} catch (Exception e) {
			throw e;
		}finally{
			jasperPrint=null;
		}
	}
	
	public void exportTxtReport(Map<String, Object> model, String outputFile)
			throws Exception {
		JasperPrint jasperPrint;
		try {
			JRTextExporter exporter = new JRTextExporter();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("exporter is " + exporter.toString());
			}
			
			createSharedDirectory();
			outputFile = SystemConstant.LOCAL_TEMP_DIRECTORY_REPORT+ReportUtils.fixFileName(outputFile,ReportUtils.TXT_EXTENSION);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("print location at " + URLEncoder.encode(StringUtils.trimToEmpty(outputFile), "UTF-8"));
			}
			if(subReports!=null){
				model.putAll(subReports);
			}
			SimpleTextReportConfiguration config = new SimpleTextReportConfiguration();
			config.setCharHeight(40f);
			config.setCharWidth(80f);
			config.setPageHeightInChars(40);
			config.setPageWidthInChars(80);
			exporter.setConfiguration(config);
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					model, new JRBeanCollectionDataSource((List<Object>)model.get(DATA_SOURCE)));
			SimpleExporterInput input = new SimpleExporterInput(jasperPrint);
//			SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(outputFile);
			exporter.setExporterInput(input);
			exporter.setExporterOutput(new SimpleWriterExporterOutput(outputFile));
			exporter.exportReport();
		} catch (Exception e) {
			throw e;
		}finally{
			jasperPrint=null;
		}
	}

	public void exportXlsReport(OutputStream outputStream,
			Map<String, Object> parameters, List<?> data) throws Exception {
		JasperPrint jasperPrint;
		try {
			JRXlsExporter exporter = new JRXlsExporter();
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					parameters, new JRBeanCollectionDataSource(data));
			SimpleXlsReportConfiguration config = new SimpleXlsReportConfiguration ();
			SimpleExporterInput input = new SimpleExporterInput(jasperPrint);
			SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(outputStream);
			exporter.setExporterInput(input);
			exporter.setExporterOutput(output);
			config.setOnePagePerSheet(false);
			config.setRemoveEmptySpaceBetweenRows(true);
			config.setDetectCellType(true);
			config.setWhitePageBackground(false);
			exporter.setConfiguration(config);
			exporter.exportReport();
		} catch (Exception e) {
			throw e;
		} finally{	
			outputStream.flush();
			outputStream.close();
			jasperPrint=null;
		}
	}
	
	public void exportXlsReport(Map<String, Object> model, String outputFile) throws Exception {
		JasperPrint jasperPrint;
		try {
			JRXlsExporter exporter = new JRXlsExporter();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("exporter is " + exporter.toString());
			}
			
			createSharedDirectory();
			outputFile = SystemConstant.LOCAL_TEMP_DIRECTORY_REPORT+ReportUtils.fixFileName(outputFile,ReportUtils.XLS_EXTENSION);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("print location at " + URLEncoder.encode(StringUtils.trimToEmpty(outputFile), "UTF-8"));
			}
			if(subReports!=null){
				model.putAll(subReports);
			}
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					model, new JRBeanCollectionDataSource((List<Object>)model.get(DATA_SOURCE)));
			SimpleXlsReportConfiguration config = new SimpleXlsReportConfiguration ();
			SimpleExporterInput input = new SimpleExporterInput(jasperPrint);
			SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(outputFile);
			exporter.setExporterInput(input);
			exporter.setExporterOutput(output);
			config.setOnePagePerSheet(false);
			config.setRemoveEmptySpaceBetweenRows(true);
			config.setDetectCellType(true);
			config.setWhitePageBackground(false);
			exporter.setConfiguration(config);
			exporter.exportReport();
		} catch (Exception e) {
			throw e;
		} finally{	
			jasperPrint=null;
		}
	}
	
	public void exportReportMultiSheets(Map<String, Object> model,
			String outputFile) throws Exception {
		JasperPrint jasperPrint1;
		JasperPrint jasperPrint2;
		try {
			JRXlsExporter exporter = new JRXlsExporter();
			
			String sheetName1 = (String) model.get("SHEET_NAME_1");
			String sheetName2 = (String) model.get("SHEET_NAME_2");
	
			if (subReports != null) {
				model.putAll(subReports);
			}
	
			jasperPrint1 = JasperFillManager.fillReport(
					jasperReport,
					model,
					new JRBeanCollectionDataSource((List<Object>) model
							.get(DATA_SOURCE)));
	
			jasperPrint2 = JasperFillManager.fillReport(
					jasperReportChild, model, new JRBeanCollectionDataSource(
							(List<Object>) model.get(DATA_SOURCE_CHILD)));
			
			List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
			jasperPrints.add(jasperPrint1);
			jasperPrints.add(jasperPrint2);
	
			SimpleXlsReportConfiguration config = new SimpleXlsReportConfiguration ();
			SimpleExporterInput input = SimpleExporterInput.getInstance(jasperPrints);
			SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(outputFile);
			exporter.setExporterInput(input);
			exporter.setExporterOutput(output);
			config.setOnePagePerSheet(false);
			config.setRemoveEmptySpaceBetweenRows(true);
			config.setWhitePageBackground(false);		
			if (sheetName1 != null && sheetName2 != null){
				config.setSheetNames(new String[] { sheetName1, sheetName2 });
			}
			exporter.setConfiguration(config);
	
			exporter.exportReport();
		} catch (Exception e) {
			throw e;
		} finally{	
			jasperPrint1=null ; jasperPrint2=null;
		}
	}
	
	public void exportReportToHTML(Map<String, Object> model, String outputFile) throws Exception {
		HtmlExporter exporter = new HtmlExporter();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("exporter is " + exporter.toString());
		}
		
		createSharedDirectory();
		outputFile = SystemConstant.LOCAL_TEMP_DIRECTORY_REPORT+outputFile;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("print location at " + URLEncoder.encode(StringUtils.trimToEmpty(outputFile), "UTF-8"));
		}
		if (subReports != null) {
			model.putAll(subReports);
		}
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasperReport,
				model,
				new JRBeanCollectionDataSource((List<Object>) model
						.get(DATA_SOURCE)));
		SimpleHtmlReportConfiguration config = new SimpleHtmlReportConfiguration();
		SimpleExporterInput input = new SimpleExporterInput(jasperPrint);
		SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(outputFile);
		exporter.setExporterInput(input);
		exporter.setExporterOutput(output);
		exporter.exportReport();
		jasperPrint=null;
	}
	
	private void createSharedDirectory(){
		FileManager.createDir(SystemConstant.LOCAL_TEMP_DIRECTORY_REPORT);
	}
}