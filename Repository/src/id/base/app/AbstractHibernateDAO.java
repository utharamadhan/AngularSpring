package id.base.app;



import id.base.app.dao.transform.DynamicResultTransformer;
import id.base.app.exception.ErrorHolder;
import id.base.app.exception.SystemException;
import id.base.app.paging.PagingUtil;
import id.base.app.paging.PagingWrapper;
import id.base.app.util.ReflectionFunction;
import id.base.app.util.StringFunction;
import id.base.app.util.dao.SearchAlias;
import id.base.app.util.dao.SearchFilter;
import id.base.app.util.dao.SearchOrder;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import softtech.hong.hce.model.PropertyValue;
import softtech.hong.hce.type.RestrictionType;
import softtech.hong.hce.utils.QueryUtils;


/**
 * AbstractHibernateDAO is abstract class to further wrap basic CRUD functionality
 * for DAO. This is to allow standardize DAO processing methods.
 * 
 */
public class AbstractHibernateDAO<T,Y extends Serializable> {
	
	protected static Logger LOGGER = LoggerFactory.getLogger(AbstractHibernateDAO.class);
	
	protected Class<T> domainClass = getDomainClass();
	
	private List<String> fieldNames = new ArrayList<String>();
	
	public List<String> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}

	@PersistenceContext
    EntityManager entityManager;

	public Session getSession() {
		return (Session) entityManager.getDelegate();
	}

	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
	    if (domainClass == null) {
	    	ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
	        domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
	    }
	    return domainClass;
	}
    
	public final void create(final Object anObject) throws SystemException {
		create(anObject,true);
	}
	
	public final void create(final Object anObject,final boolean flush) throws SystemException {
		getSession().save(anObject) ; 
		if(flush){
			getSession().flush();
			//getSession().refresh(anObject);
		}
	}
	
	public final Long createGetPK(final Object anObject,final boolean flush) throws SystemException {
		try {
			Long pk = (Long) getSession().save(anObject) ;
			if(flush){
			   getSession().flush();
			   //getSession().refresh(anObject);
			}
			return pk;
		} catch (HibernateException e) {
			throw new SystemException(new ErrorHolder("error.database.access"));
		}
	}

	public final void create(final List<T> list) throws SystemException {
		for (T object : list) {
			create(object,false);
		}
	}
	
	public final void update(final Object anObject) throws SystemException {
		try{
			getSession().merge(anObject) ; getSession().flush(); //getSession().refresh(anObject);
		}catch (HibernateException e) {
			Exception e2 = e;
			while ((e2 = (Exception) e2.getCause()) != null) {
				LOGGER.error(e2.getMessage(),e2);
				if (e2 instanceof BatchUpdateException) {
					SQLException exception = ((BatchUpdateException) e2).getNextException();
					LOGGER.error(exception.getMessage(),exception);
				}
			}
			LOGGER.error("update.error",e);
			throw new SystemException(new ErrorHolder("id", new Object[]{e.getMessage()}));
		}
	}
	
	public final void saveUpdate(final Object anObject) throws SystemException {
		try{
			getSession().saveOrUpdate(anObject) ; getSession().flush(); //getSession().refresh(anObject);
		}catch (HibernateException e) {
			Exception e2 = e;
			while ((e2 = (Exception) e2.getCause()) != null) {
				LOGGER.error(e2.getMessage(),e2);
				if (e2 instanceof BatchUpdateException) {
					SQLException exception = ((BatchUpdateException) e2).getNextException();
					LOGGER.error(exception.getMessage(),exception);
				}
			}
			LOGGER.error("update.error",e);
			throw new SystemException(new ErrorHolder("id", new Object[]{e.getMessage()}));
		}
	}

	
	public final void delete(final Object anObject) throws SystemException {
		try{
			getSession().delete(anObject) ; getSession().flush();
		}catch (HibernateException e) {
			Exception e2 = e;
			while ((e2 = (Exception) e2.getCause()) != null) {
				LOGGER.error(e2.getMessage(),e2);
				if (e2 instanceof BatchUpdateException) {
					SQLException exception = ((BatchUpdateException) e2).getNextException();
					LOGGER.error(exception.getMessage(),exception);
				}
			}
			LOGGER.error("delete.error",e);
			throw new SystemException(new ErrorHolder("id", new Object[]{e.getMessage()}));
		}
	}
	
	public void delete(final List<T> objectList) throws SystemException {
		for (T object : objectList) {
			getSession().delete(object);
		}getSession().flush();
	}
	
		
	
	
	
        
   
	public T findByPK(final Y id) throws SystemException {
		return (T) getSession().get(domainClass, id);

	}
    

   
    

    public List<T> findByQueryString(final String queryString,final Object[] params) 
    throws SystemException {
		Query query = getSession().createQuery(queryString);
		for (int j = 0; j < params.length; j++) {
			query.setParameter(j, params[j]);
		}
		return query.list();
    }
    

	protected Long getRowCount(final List<Criterion> criterias,final SearchAlias[] searchAliases)
			throws SystemException {
				Criteria criteria = getSession().createCriteria(domainClass
						.getName());
				
				if (searchAliases != null) {
					for (SearchAlias searchAlias : searchAliases) {
						if(searchAlias.getJoinType()!=null){
							criteria.createAlias(searchAlias
									.getAliasProperty(), searchAlias
									.getAliasName(), searchAlias.getJoinType());	
						}else{
						 criteria.createAlias(searchAlias
								.getAliasProperty(), searchAlias
								.getAliasName());
						}
					}
				}
				
				if (criterias != null) {
					for (Criterion criterion : criterias) {
						criteria.add(criterion);
					}
				}
				Object a = criteria.setProjection(Projections.rowCount())
				.uniqueResult();
				return (Long) a;
	}

	protected List<T> findByCriterion(final List<Criterion> criterias,
			final List<Order> orders, final Integer startIndex,
			final Integer maxRow, final String cacheRegion)
			throws SystemException {
		SearchAlias[] searchAliases = null;
		List<SearchAlias> aliases = new ArrayList<SearchAlias>();
		Set<String> filterAlias = new TreeSet<String>();
		for(int i=0; i<fieldNames.size();i++){
			String field = fieldNames.get(i);
			int totalAlias = field.split("\\.").length - 1;
			String colAlias = null;
			for(int j=0;j<totalAlias;j++){
				colAlias = colAlias == null ?  field.split("\\.")[j] : colAlias + "." + field.split(".")[j];
				if(!filterAlias.contains(colAlias)){
					filterAlias.add(colAlias);
					SearchAlias alias = new SearchAlias(colAlias, colAlias);
					aliases.add(alias);
				}
			}
		}
		
		if(aliases.size()>0){
			searchAliases = aliases.toArray(new SearchAlias[aliases.size()]);
		}
		return findAndFetchByCriterion(searchAliases,null, criterias, orders, startIndex, maxRow, cacheRegion);
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> findAndFetchByCriterion(final SearchAlias[] searchAliases  , 
			final String[] fetchProperties,  final List<Criterion> criterias,
			final List<Order> orders, final Integer startIndex,
			final Integer maxRow, final String cacheRegion, final String... joinEntity)
			throws SystemException {
			Criteria criteria = getSession().createCriteria(domainClass);

			if (searchAliases != null) {
				for (SearchAlias searchAlias : searchAliases) {
					if(searchAlias.getJoinType()!=null){
						criteria.createAlias(searchAlias
								.getAliasProperty(), searchAlias
								.getAliasName(), searchAlias.getJoinType());	
					}else{
					 criteria.createAlias(searchAlias
							.getAliasProperty(), searchAlias
							.getAliasName());
					}
				}
			}

			if (fetchProperties != null) {
				ProjectionList projectionList = Projections
						.projectionList();
				for (String fetchedProperty : fetchProperties) {
					projectionList.add(Projections
							.property(fetchedProperty));
				}
				criteria.setProjection(projectionList);
				if(searchAliases!=null){

					criteria
							.setResultTransformer(new DynamicResultTransformer(
									domainClass, fetchProperties, joinEntity, searchAliases));
				}else{
				criteria
						.setResultTransformer(new DynamicResultTransformer(
								domainClass, fetchProperties));
				}
			}

			if (criterias != null) {
				for (Criterion criterion : criterias) {
					criteria.add(criterion);
				}
			}

			if (orders != null) {
				for (Order order : orders) {
					criteria.addOrder(order);
				}
			}

			List<T> list = new ArrayList<T>();

			if (startIndex != null)
				criteria.setFirstResult(startIndex - 1);

			if (maxRow != null)
				criteria.setMaxResults(maxRow);

			if (cacheRegion != null) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(cacheRegion);
			}
			
			try{
				list = criteria.list();
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error(e.getMessage());
			}
			return list;
	}

	
	protected Long getRowCount(final DetachedCriteria detachedCriteria ,final SearchAlias[] searchAliases,
			final List<Criterion> criterias) throws SystemException {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		
		if (searchAliases != null) {
			for (SearchAlias searchAlias : searchAliases) {
				if(searchAlias.getJoinType()!=null){
					criteria.createAlias(searchAlias
							.getAliasProperty(), searchAlias
							.getAliasName(), searchAlias.getJoinType());
				}else{
					criteria.createAlias(searchAlias
						.getAliasProperty(), searchAlias
						.getAliasName());
				}
			}
		}
		
		if (criterias != null) {
			for (Criterion criterion : criterias) {
				criteria.add(criterion);
			}
		}
		return (Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
	}
	
	protected List<T> findByFilter(final DetachedCriteria detachedCriteria , 
			final SearchAlias[] searchAliases,final String[] fetchProperties ,final List<SearchFilter> searchFilters,
			final List<SearchOrder> searchOrders, final Integer startIndex,
			final Integer maxRow, final String cacheRegion)
			throws SystemException { 
		List<Criterion> criterias = new LinkedList<Criterion>();
		List<Order> orders = new LinkedList<Order>();
		
		fieldNames = new ArrayList<String>();
		
		if (criterias != null) {
			for (SearchFilter searchFilter : searchFilters) {
				criterias.add(buildCriterion(searchFilter));
			}
		}

		if (searchOrders != null) {
			for (SearchOrder searchOrder : searchOrders) {
				orders.add(buildOrder(searchOrder));
			}
		}
		return findByCriterion(detachedCriteria,searchAliases,fetchProperties,criterias, orders, startIndex, maxRow, cacheRegion);
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> findByCriterion(final DetachedCriteria detachedCriteria , 
			final SearchAlias[] searchAliases,final String[] fetchProperties ,final List<Criterion> criterias,
			final List<Order> orders, final Integer startIndex,
			final Integer maxRow, final String cacheRegion, final String... joinEntity)
			throws SystemException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(getSession());

				if (searchAliases != null) {
					for (SearchAlias searchAlias : searchAliases) {
						if(searchAlias.getJoinType()!=null){
							criteria.createAlias(searchAlias
									.getAliasProperty(), searchAlias
									.getAliasName(), searchAlias.getJoinType());
						}else{
							criteria.createAlias(searchAlias
									.getAliasProperty(), searchAlias
									.getAliasName());
						}
						
					}
				}

				if (fetchProperties != null) {
					ProjectionList projectionList = Projections
							.projectionList();
					for (String fetchedProperty : fetchProperties) {
						projectionList.add(Projections
								.property(fetchedProperty));
					}
					criteria.setProjection(projectionList);
					if(searchAliases!=null){
						criteria
							.setResultTransformer(new DynamicResultTransformer(
								domainClass, fetchProperties, joinEntity, searchAliases));	
					}else{
						criteria
							.setResultTransformer(new DynamicResultTransformer(
									domainClass, fetchProperties));
					}
				}
				
				if (criterias != null) {
					for (Criterion criterion : criterias) {
						criteria.add(criterion);
					}
				}

				if (orders != null) {
					for (Order order : orders) {
						criteria.addOrder(order);
					}
				}

				List<T> list = new ArrayList<T>();

				if (startIndex != null)
					criteria.setFirstResult(startIndex - 1);

				if (maxRow != null)
					criteria.setMaxResults(maxRow);

				if (cacheRegion != null) {
					criteria.setCacheable(true);
					criteria.setCacheRegion(cacheRegion);
				}

				Set<T> set = new LinkedHashSet<T>();
				set.addAll(criteria.list());

				Iterator<T> iterator = set.iterator();

				while (iterator.hasNext()) {
					T object = (T) iterator.next();
					list.add(object);
				}

				return list;
	}

	public PagingWrapper<T> findAllWithPagingWrapper(final int startIndex,final int maxRow)
			throws SystemException {
		Long _totalRecords = getRowCount(null,null);
		List<T> pList = findByCriterion(null, null, startIndex, maxRow,null);
		return new PagingWrapper<T>(pList, _totalRecords, startIndex, maxRow);
	}
    
    protected Order buildOrder(SearchOrder order) {
    	if(order.getSort()==SearchOrder.Sort.ASC)
    	   return Order.asc(order.getFieldName());
    	return Order.desc(order.getFieldName());
    }
    
    @SuppressWarnings("unchecked")
	private Criterion getCriterion(SearchFilter searchFilter){
    	fieldNames.add(searchFilter.getFieldName());
    	 switch (searchFilter.getOperand()) {
			case  EQUALS:
				  return Restrictions.eq(searchFilter.getFieldName(), searchFilter.getValue());
			case  EQUALS_IGNORE_CASE:
				  return Restrictions.eq(searchFilter.getFieldName(), searchFilter.getValue()).ignoreCase();
			case  EQUALS_OR_GREATER_THAN:
				  return Restrictions.ge(searchFilter.getFieldName(), searchFilter.getValue());
			case  EQUALS_OR_LESS_THAN:
				  return Restrictions.le(searchFilter.getFieldName(), searchFilter.getValue());
			case  GREATER_THAN:
				  return Restrictions.gt(searchFilter.getFieldName(), searchFilter.getValue());
			case  IN :  
				  return Restrictions.in(searchFilter.getFieldName(), (Collection)searchFilter.getValue());
			case  IS_NOT_NULL :  
				  return Restrictions.isNotNull(searchFilter.getFieldName());
			case  IS_NULL :  
				  return Restrictions.isNull(searchFilter.getFieldName());
			case  LESS_THAN :  
				  return Restrictions.lt(searchFilter.getFieldName(), searchFilter.getValue())	;
			case  LIKE :   
				  return Restrictions.ilike(searchFilter.getFieldName(), "%".concat(searchFilter.getValue().toString()).concat("%"));
			case  NOT_EQUAL :  
				  return Restrictions.ne(searchFilter.getFieldName(),  searchFilter.getValue());
			case  IN_ARRAY :
				  return Restrictions.in(searchFilter.getFieldName(), (Object[])searchFilter.getValue());
			case  NOT_IN_ARRAY :
				  return Restrictions.not(Restrictions.in(searchFilter.getFieldName(), (Object[])searchFilter.getValue()));
			case  SQL_RESTRICTION :
				String[] values = (String[])searchFilter.getValue(); 
				org.hibernate.type.Type[] types = new org.hibernate.type.Type[values.length];
				Arrays.fill(types, StringType.INSTANCE);
				return Restrictions.sqlRestriction( searchFilter.getFieldName(), values, types);
			case LIKE_COLUMN : 
				return Restrictions.sqlRestriction(searchFilter.getFieldName() + " like '%' || " + searchFilter.getValue() + " || '%'");
			case  SIMILAR_TO :
				  return Restrictions.sqlRestriction("{alias}." + ReflectionFunction.getColumnName(domainClass, searchFilter.getFieldName()) + " similar to ? ", searchFilter.getValue(), StringType.INSTANCE);
		    default :
		    	  return null;
	 }
    }
    
    protected Criterion buildCriterion(SearchFilter searchFilter) {
    	if(searchFilter.isOr()){
    		return Expression.or(buildCriterion(searchFilter.getLeftFilter()), buildCriterion(searchFilter.getRightFilter()));
		}else if(searchFilter.isAnd()) {
			return Expression.and(buildCriterion(searchFilter.getLeftFilter()), buildCriterion(searchFilter.getRightFilter()));
		}else{
    		return getCriterion(searchFilter);
    	}
    }
    
    public PagingWrapper<T> findAllWithPagingWrapper(DetachedCriteria detachedCriteria,int startIndex,
			int maxRow,SearchAlias[] searchAlias , String[] fetchProperties ,List<SearchFilter> searchFilters,
			List<SearchOrder> searchOrders, String cacheRegion)
			throws SystemException {
    	DetachedCriteria clonedDetachedCriteria = (DetachedCriteria)SerializationUtils.clone(detachedCriteria);
		List<Criterion> criterionList = new LinkedList<Criterion>();
		List<Order> orderList = new LinkedList<Order>();

		fieldNames = new ArrayList<String>();
		
		if (searchFilters != null) {
			for (SearchFilter filter : searchFilters) {
				criterionList.add(buildCriterion(filter));
			}
		}

		if (searchOrders != null) {
			for (SearchOrder order : searchOrders) {
				orderList.add(buildOrder(order));
			}
		}

		long _totalRecords = getRowCount(detachedCriteria, searchAlias ,criterionList);
		List<T> pList = findByCriterion(clonedDetachedCriteria,searchAlias,fetchProperties,criterionList, orderList, startIndex,
				maxRow, cacheRegion); 

		return new PagingWrapper<T>(pList, _totalRecords, startIndex, maxRow);

	}
    
	public PagingWrapper<T> findAllWithPagingWrapper(int startIndex,
			int maxRow, List<SearchFilter> searchFilters,
			List<SearchOrder> searchOrders, String cacheRegion)
			throws SystemException {
		List<Criterion> criterionList = new LinkedList<Criterion>();
		List<Order> orderList = new LinkedList<Order>();

		fieldNames = new ArrayList<String>();
		
		if (searchFilters != null) {
			for (SearchFilter filter : searchFilters) {
				criterionList.add(buildCriterion(filter));
			}
		}

		if (searchOrders != null) {
			for (SearchOrder order : searchOrders) {
				orderList.add(buildOrder(order));
			}
		}

		SearchAlias[] searchAliases = null;
		List<SearchAlias> aliases = new ArrayList<SearchAlias>();
		Set<String> filterAlias = new TreeSet<String>();
		for(int i=0; i<fieldNames.size();i++){
			String field = fieldNames.get(i);
			int totalAlias = field.split("\\.").length - 1;
			String colAlias = null;
			for(int j=0;j<totalAlias;j++){
				colAlias = colAlias == null ?  field.split("\\.")[j] : colAlias + "." + field.split(".")[j];
				if(!filterAlias.contains(colAlias)){
					filterAlias.add(colAlias);
					SearchAlias alias = new SearchAlias(colAlias, colAlias);
					aliases.add(alias);
				}
			}
		}
		
		if(aliases.size()>0){
			searchAliases = aliases.toArray(new SearchAlias[aliases.size()]);
		}
		
		Long _totalRecords = getRowCount(criterionList,searchAliases);
		if(_totalRecords==null){
			_totalRecords = 0L;
		}
		// normalize startIndex (in case startIndex > totalRecords/maxRow)
		if (startIndex > _totalRecords) {
			startIndex = PagingUtil.getLastPageStartRow(_totalRecords, maxRow);
		}
		List<T> pList = findByCriterion(criterionList, orderList, startIndex,
				maxRow, cacheRegion);

		return new PagingWrapper<T>(pList, _totalRecords, startIndex, maxRow);

	}
	
		
	public PagingWrapper<T> findFetchedPropertyWithPagingWrapper(int startIndex,
			int maxRow,SearchAlias[] searchAlias ,String[] fetchedProperties ,List<SearchFilter> searchFilters,
			List<SearchOrder> searchOrders, String cacheRegion, final String... joinEntity)
			throws SystemException {
		List<Criterion> criterionList = new LinkedList<Criterion>();
		List<Order> orderList = new LinkedList<Order>();

		fieldNames = new ArrayList<String>();
		
		if (searchFilters != null) {
			for (SearchFilter filter : searchFilters) {
				criterionList.add(buildCriterion(filter));
			}
		}

		if (searchOrders != null) {
			for (SearchOrder order : searchOrders) {
				orderList.add(buildOrder(order));
			}
		}

		Long _totalRecords = getRowCount(criterionList,searchAlias);
		// normalize startIndex (in case startIndex > totalRecords/maxRow)
		if (startIndex > _totalRecords) {
			startIndex = PagingUtil.getLastPageStartRow(_totalRecords, maxRow);
		}
		List<T> pList = findAndFetchByCriterion(searchAlias,fetchedProperties,criterionList, orderList, startIndex,
				maxRow, cacheRegion, joinEntity);

		return new PagingWrapper<T>(pList, _totalRecords, startIndex, maxRow);

	}

    public List<T> findAll(List<SearchFilter> searchFilters,List<SearchOrder> searchOrders,String cacheRegion)
    	throws SystemException{
    	List<Criterion> criterionList = new LinkedList<Criterion>();
		List<Order> orderList = new LinkedList<Order>();

		fieldNames = new ArrayList<String>();
		
		if (searchFilters != null) {
			for (SearchFilter filter : searchFilters) {
				criterionList.add(buildCriterion(filter));
			}
		}

		if (searchOrders != null) {
			for (SearchOrder order : searchOrders) {
				orderList.add(buildOrder(order));
			}
		}
		
		return  findByCriterion(criterionList, orderList, null,
				null, cacheRegion);

    }
    
	protected List<T> findFetchedPropertyList(SearchAlias[] searchAlias,
			String[] fetchedProperty, List<SearchFilter> searchFilters,
			List<SearchOrder> searchOrders, String cacheRegion)
			throws SystemException {
		List<Criterion> criterionList = new LinkedList<Criterion>();
		List<Order> orderList = new LinkedList<Order>();

		fieldNames = new ArrayList<String>();
		
		if (searchFilters != null) {
			for (SearchFilter filter : searchFilters) {
				criterionList.add(buildCriterion(filter));
			}
		}

		if (searchOrders != null) {
			for (SearchOrder order : searchOrders) {
				orderList.add(buildOrder(order));
			}
		}
		return findAndFetchByCriterion(searchAlias, fetchedProperty,
				criterionList, orderList, null, null, cacheRegion);
	}

        
	public List<T> findAll(int startIndex , int maxRow) throws SystemException {
		return findByCriterion(null, null, startIndex, maxRow, null);
	}
	
	public List<T> findAll() throws SystemException {
		return findByCriterion(null, null, null, null, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> loadAll(DetachedCriteria detachedCriteria) throws SystemException{
		List<T> list = null;
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		list = criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> top(DetachedCriteria detachedCriteria, int rows) throws SystemException{
		List<T> list = null;
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		list = criteria.setMaxResults(rows).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public T first(DetachedCriteria detachedCriteria) throws SystemException{
		T object = null;
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		object = (T) criteria.setMaxResults(1).uniqueResult();
		return object;
	}
	
	public void updateByIdentity(String property, Object value, Object identityValue) throws SystemException{
		updateByIdentity(domainClass, property, value, identityValue);
	}
	
	public void updateByIdentity(Class<?> clazz, String property, Object value, Object identityValue) throws SystemException{
		Session session = null;
		session = getSession();
		String queryString = StringUtils.replace(QueryConstant.UPDATE_QUERY, QueryConstant.OBJECT_NAME,
				QueryUtils.getTableName(clazz.getCanonicalName()));
			queryString = StringUtils.replace(queryString, QueryConstant.PROPERTY_VALUE, QueryUtils.getColumnName(clazz, property) + " = :first");
			queryString = StringUtils.replace(queryString, QueryConstant.CONDITION, 
					QueryUtils.getIdentifierColumnName(clazz.getCanonicalName()) + " = :second");
		Query query = session.createSQLQuery(queryString)
			.setParameter("first", value).setParameter("second", identityValue);
		query.executeUpdate();
	}
	
	/**
	 * @param property
	 * @param value
	 * @param expression
	 * @throws SystemException
	 */
	public void update(String property, Object value, softtech.hong.hce.model.Expression expression) throws SystemException {
		Session session = null;
		session = getSession();
		String queryString = StringUtils.replace(QueryConstant.UPDATE_QUERY, QueryConstant.OBJECT_NAME,
				QueryUtils.getTableName(domainClass.getCanonicalName()));
			queryString = StringUtils.replace(queryString, QueryConstant.PROPERTY_VALUE, QueryUtils.getColumnName(domainClass, property) + " = :first");
			queryString = StringUtils.replace(queryString, QueryConstant.CONDITION, 
					QueryUtils.getColumnName(domainClass, expression.getPropertyName()) + " = :second");
		Query query = session.createSQLQuery(queryString)
			.setParameter("first", value).setParameter("second", expression.getValue());
		query.executeUpdate();
	}
		
	/**
	 * @param propertyValues
	 * @param expressions
	 * @throws SystemException
	 */
	public void update(PropertyValue[] propertyValues, softtech.hong.hce.model.Expression...expressions) throws SystemException{
		update(domainClass, propertyValues, expressions);
	}
	
	/**
	 * @param clazz
	 * @param propertyValues
	 * @param expressions
	 * @throws SystemException
	 */
	public void update(Class<?> clazz, PropertyValue[] propertyValues, softtech.hong.hce.model.Expression...expressions) throws SystemException{
		Session session = null;
		session = getSession();
		String queryString = StringUtils.replace(QueryConstant.UPDATE_QUERY, QueryConstant.OBJECT_NAME,
				QueryUtils.getTableName(clazz.getCanonicalName()));
			queryString = StringUtils.replace(queryString, QueryConstant.PROPERTY_VALUE, QueryUtils.setClause(clazz, propertyValues));
			queryString = StringUtils.replace(queryString, QueryConstant.CONDITION, 
					QueryUtils.whereClause(clazz, expressions));
		Query query = session.createSQLQuery(queryString);
		
		for(PropertyValue propertyValue : propertyValues){
			query.setParameter(propertyValue.getPropertyName(), propertyValue.getValue());
		}
		
		for(softtech.hong.hce.model.Expression expression : expressions){
			query.setParameter("_" + expression.getPropertyName(), expression.getValue());
			if(expression.getRestrictionType() == RestrictionType.BETWEEN){				
				query.setParameter("and_" + expression.getPropertyName(), expression.getValue());
			}
		}
		query.executeUpdate();
	}
	
	/**
	 * @param clazz
	 * @param ids
	 * @param expressions
	 * @throws SystemException
	 */
	public void update(Class<?> clazz, Long[] ids, PropertyValue... propertyValues) throws SystemException{
		if(ids == null || ids.length == 0 || propertyValues == null || propertyValues.length == 0){
			throw new HibernateException("Cannot find identifier or property to be updated!");
		}
		Session session = null;
		session = getSession();
		String queryString = StringUtils.replace(QueryConstant.SOFT_DELETE_QUERY_MULTIPLE_UPDATE, QueryConstant.OBJECT_NAME, 
				QueryUtils.getTableName(clazz.getCanonicalName()));
		queryString = StringUtils.replace(queryString, QueryConstant.IDENTIFIER, QueryUtils.getIdentifierColumnName(domainClass.getCanonicalName()));
		String setString = " ";
		for (PropertyValue propertyValue : propertyValues) {
			setString += QueryUtils.getColumnName(clazz, propertyValue.getPropertyName()) + " = :"+ propertyValue.getPropertyName() + " ,";
		}
		queryString =StringUtils.replace(queryString, QueryConstant.PROPERTY_NAME, StringUtils.substring(setString, 0, setString.length() -1) );
		Query query = session.createSQLQuery(queryString);
		for(int i=0; i < propertyValues.length; i++){
			query.setParameter(propertyValues[i].getPropertyName(), propertyValues[i].getValue());
		}
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}
	
	public boolean isExists(DetachedCriteria detachedCriteria) throws HibernateException {
		Session session = null;
		session = getSession();
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		criteria.setProjection(Projections.rowCount());
		return (Long)criteria.uniqueResult() > 0;
	}
	
	/**
	 * @param detachedCriteria
	 * @return Long
	 * @throws HibernateException
	 */
	public Long getCount(DetachedCriteria detachedCriteria) throws HibernateException{
		Session session = null;
		session = getSession();
		detachedCriteria.setProjection(Projections.rowCount());
		Criteria criteria = detachedCriteria.getExecutableCriteria(session);
		return ((Long) criteria.uniqueResult());
	}
	
	/**
	 * @param clazz
	 * @param ids
	 * @throws SystemException
	 * delete objects using in query
	 */
	public void hardDelete( Long[] ids) throws SystemException{
		Session session = null;
		session = getSession();
		String queryString = StringUtils.replace(QueryConstant.HARD_DELETE_QUERY, QueryConstant.OBJECT_NAME, 
				QueryUtils.getTableName(domainClass.getCanonicalName()));
		queryString = StringUtils.replace(queryString, QueryConstant.IDENTIFIER, QueryUtils.getIdentifierColumnName(domainClass.getCanonicalName()));
		Query query = session.createSQLQuery(queryString).setParameterList("ids", ids);
		query.executeUpdate();
	}
	
	/**
	 * @param clazz
	 * @param property
	 * @param ids
	 * @param value
	 * @throws SystemException
	 */
	public void softDelete(Class<?> clazz, String property, Long[] ids, boolean value) throws SystemException{
		softDelete(clazz, ids, softtech.hong.hce.model.Expression.eq(property, value));
	}
	
	/**
	 * @param clazz
	 * @param ids
	 * @param expressions
	 * @throws HibernateException
	 */
	public void softDelete(Class<?> clazz, Long[] ids, softtech.hong.hce.model.Expression... expressions) throws SystemException{
		if(ids == null || ids.length == 0 || expressions == null || expressions.length == 0){
			throw new HibernateException("Cannot find identifier or property to be updated!");
		}
		Session session = null;
		session = getSession();
		String queryString = StringUtils.replace(QueryConstant.SOFT_DELETE_QUERY_MULTIPLE_UPDATE, QueryConstant.OBJECT_NAME, 
				QueryUtils.getTableName(clazz.getCanonicalName()));
		String setString = " ";
		for (softtech.hong.hce.model.Expression expression : expressions) {
			setString += expression.getPropertyName() + " = :"+ expression.getPropertyName() + " ,";
		}
		queryString =StringUtils.replace(queryString, QueryConstant.PROPERTY_NAME, StringUtils.substring(setString, 0, setString.length() -1) );
		queryString =StringUtils.replace(queryString, QueryConstant.IDENTIFIER, QueryUtils.getIdentifierColumnName(clazz.getCanonicalName()));
		Query query = session.createSQLQuery(queryString);
		for(int i=0; i < expressions.length; i++){
			query.setParameter(expressions[i].getPropertyName(), expressions[i].getValue());
		}
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}
	
	/**
	 * @param clazz
	 * @param property
	 * @param ids
	 * @param value
	 * @throws SystemException
	 * delete collection of object by remark the value of property (actually we use : true | false)
	 */
	public void softDelete(String property, Long[] ids, boolean value) throws SystemException{
		softDelete(ids, new softtech.hong.hce.model.Expression[]{ new softtech.hong.hce.model.Expression(property, value)});
	}
	
	/**
	 * @param clazz
	 * @param ids
	 * @param expressions
	 * @throws SystemException
	 * delete collection of object by remark the value of property (actually we use : true | false)
	 * can be used for multiple properties and values
	 */
	public void softDelete( Long[] ids, softtech.hong.hce.model.Expression... expressions) throws SystemException{
		softDelete(domainClass, ids, expressions);
	}

	public List<T> findAll(List<SearchFilter> filter,
			List<SearchOrder> order) throws SystemException {
		return findAll(filter, order, null);
	}
	
	/**
	 * @param detachedCriterias
	 * @param startNo
	 * @param offset
	 * @return PagingWrapper
	 * @throws SystemException
	 * find All List and set to PagingWrapper
	 */
	@SuppressWarnings("unchecked")
	public PagingWrapper<T> findAllWithPagingWrapper(DetachedCriteria[] detachedCriterias, int startNo, int offset) throws SystemException{
//		PagingWrapper<T> pagingWrapper = new PagingWrapper<>();
		List<T> list = null;
		Session session = null;
		session = getSession();
		
		Criteria critCount = detachedCriterias[0].getExecutableCriteria(session);
		Long count = 0l;
		
//		Object a = criteria.setProjection(Projections.rowCount())
//				.uniqueResult();
//				return (Long) a;
		
		count = (Long) critCount.uniqueResult();
//		pagingWrapper.setMaxRecord(count.intValue());

		Criteria dataCriteria = detachedCriterias[1].getExecutableCriteria(session);
		
		dataCriteria.setFirstResult(startNo-1);
		dataCriteria.setMaxResults(offset);
		list = dataCriteria.list();
		
		if (startNo > count) {
			startNo = PagingUtil.getLastPageStartRow(count, offset);
		}
		
		return new PagingWrapper<T>(list, count, startNo, offset);
		
	}
	
	public Date getCurrentSystemDate(){
		Date appDate = new Date();
		try {
			Query query = getSession().createSQLQuery("SELECT SYSDATE FROM DUAL");
			appDate = (Date) ( query.uniqueResult());
		} catch (Exception e) {
			LOGGER.error("getCurrentSystemDate = "+StringFunction.printStackTraceToString(e));
		}
		return appDate;
	}
}
