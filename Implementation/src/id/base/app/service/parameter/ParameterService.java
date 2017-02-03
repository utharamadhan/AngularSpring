/*
 * Created on Apr 8, 2004
 */
package id.base.app.service.parameter;


import id.base.app.dao.parameter.IAppParameterDAO;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.service.MaintenanceService;
import id.base.app.util.dao.Operator;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.AppParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParameterService  implements MaintenanceService<AppParameter>, IParameterService {

	@Autowired
    protected  IAppParameterDAO parameterDAO;
    
    public ParameterService(){};
    
    public ParameterService(IAppParameterDAO parameterDAO){
    	this.parameterDAO = parameterDAO;
    }
    
	public void delete(Long[] objectPKs) throws SystemException {
		// TODO Auto-generated method stub
	}
	
	public List<AppParameter> findAllAppParameter() throws SystemException {
		return parameterDAO.findAll();
	}

	public AppParameter findById(Long id) throws SystemException {
		return parameterDAO.findAppParameterById(id);
	}

	public void saveOrUpdate(AppParameter anObject) throws SystemException {
	    parameterDAO.saveOrUpdate(anObject);
	}

	public PagingWrapper<AppParameter> findAllByFilter(int startNo, int offset,
			List<SearchFilter> filter, List<SearchOrder> order)
			throws SystemException {
		if (order==null) {
			order = new ArrayList<SearchOrder>();
            order.add(new SearchOrder(AppParameter.NAME, SearchOrder.Sort.ASC));
		}
		return parameterDAO.findAllParameterWithFilter(startNo, offset, filter, order);
	}

	@Override
	public List<AppParameter> findObjects(Long[] objectPKs)
			throws SystemException {
		List<AppParameter> appParameters=new ArrayList<AppParameter>();
		AppParameter appParam=null;
		for(Long l:objectPKs){
			appParam=parameterDAO.findAppParameterById(l);
			appParameters.add(appParam);
		}
		return appParameters;
	}

	@Override
	public AppParameter findParameterByName(String name) throws SystemException {
		return parameterDAO.findAppParameterByName(name);
	}
	
	@Override
	public Map<String,String> findParameterPairValue(String likeName) throws SystemException {
		return parameterDAO.findParameterPairValue(likeName);
	}

	@Override
	public List<AppParameter> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map<String, Object> findAppParametersByNames(List<String> names)
			throws SystemException {
		Map<String,Object> map = new HashMap<String, Object>();
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		filters.add(new SearchFilter(AppParameter.NAME, Operator.IN, names));
		List<AppParameter> params = parameterDAO.findParametersByNames(filters);
		for (AppParameter appParameter : params) {
			map.put(appParameter.getName(), appParameter.getValue());
		}
		return map;
	}
	
}
