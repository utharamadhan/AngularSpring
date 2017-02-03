package id.base.app.dao.forecast.call;

import id.base.app.AbstractHibernateDAO;
import id.base.app.SystemConstant;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.DateTimeFunction;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;
import id.base.app.valueobject.forecast.ForecastCallDaily;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ForecastCallDailyDAO extends AbstractHibernateDAO<ForecastCallDaily, Long> implements IForecastCallDailyDAO {

	@Override
	public ForecastCallDaily findById(Long id) throws SystemException {
		return super.findByPK(id);
	}

	@Override
	public void saveOrUpdate(ForecastCallDaily anObject) throws SystemException {
		if(anObject.getPkForecastCallDaily()==null){			
			super.create(anObject);
		} else {
			super.update(anObject);
		}
	}

	@Override
	public void delete(Long[] objectPKs) throws SystemException {
		for(Long objectPK : objectPKs){
			ForecastCallDaily obj = findById(objectPK);
			super.delete(obj);
		}
	}

	@Override
	public List<ForecastCallDaily> findObjects(Long[] objectPKs) throws SystemException {
		return null;
	}

	@Override
	public PagingWrapper<ForecastCallDaily> findAllByFilter(int startNo, int offset, List<SearchFilter> filter, List<SearchOrder> order) throws SystemException {
		return super.findAllWithPagingWrapper(startNo, offset, filter, order, null);
	}

	@Override
	public Boolean isForecastDailyAlreadyExist(Long pkLookupAddress, Date date) throws SystemException {
		Criteria crit = getSession().createCriteria(domainClass);
			crit.createAlias("lookupAddress", "lookupAddress");
			crit.add(Restrictions.eq("lookupAddress.pkLookupAddress", pkLookupAddress));
			crit.add(Restrictions.eq("forecastDate", date));
			crit.setProjection(Projections.rowCount());
		Long rowCount = (Long) crit.uniqueResult();
		return rowCount != null && rowCount > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public ForecastCallDaily getForecastDaily(Long pkLookupAddress, Date dateParam) throws SystemException {
		Criteria crit = getSession().createCriteria(domainClass);
			crit.createAlias("lookupAddress", "lookupAddress");
			crit.add(Restrictions.eq("lookupAddress.pkLookupAddress", pkLookupAddress));
			crit.add(Restrictions.eq("forecastDate", dateParam));
			crit.setMaxResults(1);
		return (ForecastCallDaily) crit.uniqueResult();
	}

	public static String PURGING_FORECAST_CALL_DAILY = "DELETE FROM FORECAST_CALL_DAILY WHERE FORECAST_DATE < to_date(:paramDate, 'YYYY-MM-DD')";

	@Override
	public void purgingForecastDaily(Integer purgingLimit) throws SystemException {
		Calendar cal = DateTimeFunction.getCalendar(DateTimeFunction.truncateDate(new Date()));
			cal.add(Calendar.DAY_OF_YEAR, purgingLimit * -1);
		String purgingQuery = PURGING_FORECAST_CALL_DAILY;
		SQLQuery sqlQuery = getSession().createSQLQuery(purgingQuery);
			sqlQuery.setString("paramDate", DateTimeFunction.date2String(cal.getTime(), SystemConstant.WEB_SERVICE_DATE));
		try {
			sqlQuery.executeUpdate();			
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}

}