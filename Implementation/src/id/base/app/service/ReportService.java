package id.base.app.service;

import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

public interface ReportService<E> extends MaintenanceService<E> {

	public Collection<E> findReportCollection(Object[] params) throws SystemException ;  

	public void updateExportedObjects(List<E> objects)throws SystemException;

	public PagingWrapper<E> findAllWithPagingWrapper(int startIndex, int maxRow, List<SearchFilter> searchFilters, List<SearchOrder> searchOrders)
					throws SystemException;
	
	public List<E> findAll(List<SearchFilter> searchFilters,List<SearchOrder> searchOrders) throws SystemException;
	
	public String exportReport(List<E> listBean, int exportTo) throws JRException, FileNotFoundException;
	
	public void exportReport(String fileName, String jasperFile, List<E> listBean, int exportTo, Map<String, Object> params) throws JRException, FileNotFoundException;
}
