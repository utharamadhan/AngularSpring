package id.base.app.dao.audittrail;

import id.base.app.AbstractHibernateDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.LogAuditTrail;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class AuditTrailDAO extends AbstractHibernateDAO<LogAuditTrail,Long> implements IAuditTrailDAO {

	
	public void create(LogAuditTrail audit) throws SystemException {
		super.create(audit);
	}

	public PagingWrapper<LogAuditTrail> findAuditTrailByCode(int startNo,
			int offset, List<SearchFilter> searchFilter,
			List<SearchOrder> searchOrder) throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset, searchFilter, searchOrder, null);
	}

	
	@Override
	public List<LogAuditTrail> findAuditTrailByCode(Integer code)throws SystemException {
		String query = "from LogAuditTrail ";
		if (code != null && code != 0) { 
			query += "where code=?";
		}
		return (List<LogAuditTrail>) super.findByQueryString(query, new Object[]{code});
	}
	
	@Override
	public List<LogAuditTrail> findAuditTrailByCodeSortDateDesc(Integer code)throws SystemException {
		String query = "from LogAuditTrail ";
		if (code != null && code != 0) { 
			query += "where code=?";
			query += "order by access_time desc";
		}
		return (List<LogAuditTrail>) super.findByQueryString(query, new Object[]{code});
	}
	
	
}
