/*
 * Created on Jul 1, 2005
 *
 */
package id.base.app.paging;

import id.base.app.SystemParameter;

public class PagingUtil {
    public static int getStartRowFromPage(int page) {
        int startRow;
        if (page < 1) {
            startRow = 1;
        } else {
            startRow = ((page - 1) * SystemParameter.MAX_RECORD_PER_SCREEN) + 1;
        }
        return startRow;
    }

    public static int getStartRowFromPage(int page, int maxRowPerScreen) {
        int startRow;
        if (page < 1) {
            startRow = 1;
        } else {
            startRow = ((page - 1) * maxRowPerScreen) + 1;
        }
        return startRow;
    }  
    
	public static int getStartRow(int page, int maxRow){
		int startRow = 0;
		
		if(page > 1){
			startRow = (page-1)* maxRow;
		}
		
		return startRow;
	}

	public static int getNumOfPage(long numOfRow, int maxRow){
		int numOfPage = 0;
		
		if(numOfRow > 0 ){
			int div = (int)numOfRow/maxRow;
			int mod = (int)numOfRow%maxRow;
			
			if(mod > 0){
				numOfPage = div + 1;
			}else{
				numOfPage = div;
			}
		}
		
		return numOfPage;
	}    
	
	public static int getLastPageStartRow(long numOfRow, int maxRow) {
		if (numOfRow == 0) {
			return 1;
		}
		int numOfPage = getNumOfPage(numOfRow, maxRow);
		return (numOfPage - 1) * maxRow + 1;
	}
}
