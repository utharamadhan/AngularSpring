package id.base.app.dao.jobmonitor;

import id.base.app.AbstractHibernateDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.monitoring.JobMonitor;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class JobMonitorDAO extends AbstractHibernateDAO<JobMonitor,Long> implements IJobMonitorDAO {

	
	public void delete(Long[] objectPKs) throws SystemException {
		// TODO Auto-generated method stub
	}
	
	public List<JobMonitor> findAll(){
		return super.findAll();
	}
	

	public JobMonitor findJobMonitorById(Long id) throws SystemException {
		return super.findByPK(new Long(id));
	}
	
	public void saveOrUpdate(JobMonitor anObject) throws SystemException {
		 if(anObject.getPkJobMonitor()==null)
			 super.create(anObject);
	     else {
	    	 super.update(anObject);
	     }
	}

	public PagingWrapper<JobMonitor> findAllParameterWithFilter(int startNo,
			int offset, List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset, filter, order, null);
	}

	@Override
	public JobMonitor findById(Long id) throws SystemException {
		return super.findByPK(id);
	}

	@Override
	public List<JobMonitor> findObjects(Long[] objectPKs)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagingWrapper<JobMonitor> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}

}
