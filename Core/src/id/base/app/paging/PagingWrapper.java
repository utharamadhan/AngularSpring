/*
 * Created on Apr 17, 2004
 *
 */
package id.base.app.paging;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Wrap result for Paging Helper
 * @author denny
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagingWrapper<T> implements Serializable {
    @SuppressWarnings("unchecked")
    private static final List EMPTY_LIST = new LinkedList();

    private static final long serialVersionUID = 1322545907045080110L;
    private List<T> result = new LinkedList<T>();
    private int currentPage;
    private int noOfPage;
    /** total number of result */
    private long maxRecord;
    /** result start from */
    private int startRecordIndex;
    private int recordsPerPage;
    private int currentRecord;

    @SuppressWarnings("unchecked")
    public PagingWrapper() {
        this.result = EMPTY_LIST;
        this.currentPage = 1;
        this.noOfPage = 1;
    }

    public PagingWrapper(List<T> _result, long _totalRecords, int _start, int _size){
    	this.result = _result;
    	this.maxRecord = _totalRecords;
    	this.startRecordIndex = _start;
    	this.recordsPerPage = _size;
    	
        int imod = _start % _size;
        int idiv = _start / _size;
        if(imod == 0){
            currentPage = idiv;
        } else{
            currentPage = idiv + 1;
        }

//        logger.debug("imod {}",imod);
//        logger.debug("idiv {}",idiv);
//        logger.debug("current page {}",currentPage);

        //calculation to determine number of page
        imod = (int)_totalRecords % _size;
        idiv = (int)_totalRecords / _size;
        if(imod == 0){
            noOfPage = idiv;
        } else{
            noOfPage = idiv + 1;
        }

        if (_totalRecords < _start) {
            currentPage = noOfPage;
            _start = ((currentPage - 1) * _size) + 1;
        }
        
        currentRecord = (currentPage == 0 ? 0 : (currentPage-1) * recordsPerPage) + result.size();
    }
    
    public boolean isResultEmpty() {
        return (result == null || result.isEmpty());
    }

    /**
     * @return Returns the currentPage.
     */
    public int getCurrentPage() {
        return currentPage;
    }
    /**
     * @param currentPage The currentPage to set.
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    /**
     * @return Returns the maxRecord.
     */
    public long getMaxRecord() {
        return maxRecord;
    }
    /**
     * @param maxRecord The maxRecord to set.
     */
    public void setMaxRecord(long maxRecord) {
        this.maxRecord = maxRecord;
    }
    /**
     * @return Returns the noOfPage.
     */
    public int getNoOfPage() {
        return noOfPage;
    }
    /**
     * @param noOfPage The noOfPage to set.
     */
    public void setNoOfPage(int noOfPage) {
        this.noOfPage = noOfPage;
    }
    /**
     * @return Returns the result.
     */
    public List<T> getResult() {
        return result;
    }
    /**
     * @param result The result to set.
     */
    public void setResult(List<T> result) {
        this.result = result;
    }
    /**
     * @return Returns the startRecordIndex.
     */
    public int getStartRecordIndex() {
        return startRecordIndex;
    }
    /**
     * @param startRecordIndex The startRecordIndex to set.
     */
    public void setStartRecordIndex(int startRecordIndex) {
        this.startRecordIndex = startRecordIndex;
    }


    /**
     * @return number of records per page
     */
    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    /**
     * Set number of records per page
     * @param i number of records per page
     */
    public void setRecordsPerPage(int i) {
        recordsPerPage = i;
    }
    
    public int getCurrentRecord() {
		return currentRecord;
	}

	public void setCurrentRecord(int currentRecord) {
		this.currentRecord = currentRecord;
	}

	/**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new StringBuffer(PagingWrapper.class.getName())
            .append(" [startRecordIndex = ").append(this.startRecordIndex)
            .append(", resultEmpty = ").append(this.isResultEmpty())
            .append(", noOfPage = ").append(this.noOfPage)
            .append(", maxRecord = ").append(this.maxRecord)
            .append(", currentPage = ").append(this.currentPage)
            .append(", recordsPerPage = ").append(this.recordsPerPage)
            .append("]")
            .toString();
    }
}
