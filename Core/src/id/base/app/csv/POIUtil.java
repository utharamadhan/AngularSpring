package id.base.app.csv;

import id.base.app.ILookupConstant;
import id.base.app.SystemConstant;
import id.base.app.util.DateTimeFunction;
import id.base.app.util.EmailFunction;
import id.base.app.util.StringFunction;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class POIUtil {
	protected static Logger LOGGER = LoggerFactory.getLogger(POIUtil.class);
	
	public static Workbook getWorkbook(String fileType, InputStream inputStream) throws IOException, IllegalArgumentException {
		if(fileType.equalsIgnoreCase("xlsx")){
			return new XSSFWorkbook(inputStream);
		}else if(fileType.equals("xls")){
			return new HSSFWorkbook(inputStream);
		}else{
			throw new IllegalArgumentException("fileType=" + fileType + " not recognized");
		}
	}
	
	public static String getFileExtension(String fileName){
		return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	}
	
	public static void writeData(Workbook workbook, Cell cell, Object obj) throws Exception {
		if(obj instanceof Date){
			cell.setCellValue((Date)obj);
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(SystemConstant.SYSTEM_DATE_MASK_2));
			cell.setCellStyle(cellStyle);
			
		}else if(obj instanceof Boolean){
			cell.setCellValue((Boolean)obj);
		}else if(obj instanceof String){
			if(((String)obj).length() > 0 && ((String)obj).trim().charAt(0)=='='){
				cell.setCellType(Cell.CELL_TYPE_FORMULA);
				cell.setCellFormula(((String)obj).substring(1, ((String)obj).length()));
			}else{
				cell.setCellValue((String)obj);	
			}
		}else if(obj instanceof Double){
			cell.setCellValue((Double)obj);
		}else if(obj instanceof Long){
			cell.setCellValue((Long)obj);
		}
	}
	
	public static Object convertCellToObject(Cell cell) {
		try{
			if(cell.getCellType()==Cell.CELL_TYPE_STRING){
				return cell.getRichStringCellValue().getString();
			}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
				return cell.getNumericCellValue();
			}else if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
				return (String) "=" + cell.getCellFormula();
			}else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
				return new String("");
			}else{
				return new String("ERROR READ");
			}
		}catch(Exception e){
			e.printStackTrace();
			return new String("ERROR READ");
		}
	}
	
	public static Object convertCellToObject(int dataType, Cell cell) throws Exception {
		if(dataType == ILookupConstant.FieldDataType.NUMERIC){
			try{
				if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
					switch(cell.getCachedFormulaResultType()) {
		            	case Cell.CELL_TYPE_NUMERIC:
		            		return Double.valueOf(cell.getNumericCellValue());
		            	case Cell.CELL_TYPE_STRING:
		            		return Double.parseDouble(cell.getRichStringCellValue().getString());
						default : 
							throw new Exception("cell type not registered");
					}
		        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					return cell.getNumericCellValue();
				}else {
					return Double.parseDouble(cell.getRichStringCellValue().getString()); 
				}
			}catch(Exception e){
				throw new Exception("must be in numeric type");
			}
		}else if(dataType == ILookupConstant.FieldDataType.LONG){
			try{
				if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
					switch(cell.getCachedFormulaResultType()) {
		            	case Cell.CELL_TYPE_NUMERIC:
		            		return Double.valueOf(cell.getNumericCellValue()).longValue();
		            	case Cell.CELL_TYPE_STRING:
		            		Double doubleValue = Double.parseDouble(cell.getRichStringCellValue().getString());
							return doubleValue.longValue();
						default : 
							throw new Exception("cell type not registered");
					}
		        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
					return Double.valueOf(cell.getNumericCellValue()).longValue();
				}else {
					Double doubleValue = Double.parseDouble(cell.getRichStringCellValue().getString());
					return doubleValue.longValue();
				}
			}catch(Exception e){
				throw new Exception("must be in money type");
			}
		}else if(dataType == ILookupConstant.FieldDataType.STRING){
			return cell.getRichStringCellValue().getString().trim();
		}else if(dataType == ILookupConstant.FieldDataType.DATE){
			try{
				if(cell.getCellType() == Cell.CELL_TYPE_STRING){
					LOGGER.info("FORMATING DATE : [STRING] " + cell.getRichStringCellValue().getString());
					String cellValue =  cell.getRichStringCellValue().getString().trim();
					
					if(cellValue.startsWith("'")){
						cellValue = cellValue.substring(1);
					}
					
					LOGGER.info("FORMAT TO DATE RESULT : [DATE] " + DateTimeFunction.string2Date(cellValue, SystemConstant.SYSTEM_DATE_MASK_2));
					return DateTimeFunction.string2Date(cellValue, SystemConstant.SYSTEM_DATE_MASK_2);
				}else{
					return cell.getDateCellValue();					
				}
			}catch(Exception e){
				throw new Exception("must be in date type");
			}
		}else if(dataType == ILookupConstant.FieldDataType.LOOKUP){
			return cell.getRichStringCellValue().getString().trim();
		}else if(dataType == ILookupConstant.FieldDataType.EMAIL){
			String emailValue = cell.getRichStringCellValue().getString().trim();
			if(!StringFunction.isEmpty(emailValue) && !EmailFunction.isAddressValidRegex(emailValue)){
    			throw new Exception("not valid email");
    		}else{
    			return emailValue;
    		}
		}else{
			throw new IllegalArgumentException("Data type is not recognized");
		}
	}
	
	public static void createRegionBorderStyle(Workbook workbook, Sheet sheet, CellRangeAddress cellRangeAddress, short borderType) {
		RegionUtil.setBorderTop(CellStyle.BORDER_MEDIUM, cellRangeAddress, sheet, workbook);
		RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, cellRangeAddress, sheet, workbook);
		RegionUtil.setBorderRight(CellStyle.BORDER_MEDIUM, cellRangeAddress, sheet, workbook);
		RegionUtil.setBorderLeft(CellStyle.BORDER_MEDIUM, cellRangeAddress, sheet, workbook);
	}
	
	public static CellStyle createBorderStyle(Workbook workbook, short borderType){
		CellStyle border = workbook.createCellStyle();
		border.setBorderTop(borderType);
		border.setBorderBottom(borderType);
		border.setBorderRight(borderType);
		border.setBorderLeft(borderType);
		
		return border;
	}
	
	public static CellStyle createBorderDateStyle(Workbook workbook, short borderType, String formatDate){
		CellStyle border = workbook.createCellStyle();
		border.setBorderTop(borderType);
		border.setBorderBottom(borderType);
		border.setBorderRight(borderType);
		border.setBorderLeft(borderType);
		border.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(formatDate));
		
		return border;
	}
}
