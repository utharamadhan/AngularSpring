package id.base.app.dao.transform;

import id.base.app.util.ReflectionFunction;
import id.base.app.util.dao.SearchAlias;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.expression.Resolver;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author anicka andry
 * <pre>manage field retrieval effecient</pre>
 *
 */
public class DynamicResultTransformer<T> implements ResultTransformer {
	
	final static Logger LOGGER = LoggerFactory.getLogger(DynamicResultTransformer.class);
	
	public DynamicResultTransformer( Class<T> resultClass,String[] fieldNames) {
		this.fieldNames = fieldNames;
		this.resultClass = resultClass;
	}
	
	public DynamicResultTransformer( Class<T> resultClass,String[] fieldNames,String[] joinEntity) {
		this.fieldNames = fieldNames;
		this.resultClass = resultClass;
		this.joinEntity = joinEntity;
	}
	
	public DynamicResultTransformer( Class<T> resultClass,String[] fieldNames,String[] joinEntity, SearchAlias[] searchAliases) {
		this.fieldNames = fieldNames;
		this.resultClass = resultClass;
		this.joinEntity = joinEntity;
		for (SearchAlias searchAlias : searchAliases) {
			this.searchAliasMap.put(searchAlias.getAliasName(), searchAlias.getAliasProperty());
		}
	}

	static Logger logger = LoggerFactory.getLogger(DynamicResultTransformer.class);

	private String[] fieldNames ;
	private String[] joinEntity ;
	private Class<T> resultClass ;
	private Map<String,String> searchAliasMap = new HashMap<String, String>();
	
	
	private static final long serialVersionUID = 5024387905301935159L;
	
	

	@Override
	public List transformList(List list) {
		return list;
	}

	@Override
	public T transformTuple(Object[] tuple, String[] alias) {
		T instance = null;
		try {
			instance = resultClass.newInstance();
			PropertyUtilsBean propertyUtils = new PropertyUtilsBean();
			Resolver resolver = propertyUtils.getResolver();
			if(joinEntity!=null&&joinEntity.length>0){
				for (int j = 0; j < joinEntity.length; j++) {
					String name = joinEntity[j];
					Object bean = instance;
					while(resolver.hasNested(name)){
						String next = resolver.next(name);
						Field f = ReflectionFunction.getField(bean.getClass(), next);
						f.setAccessible(true);
						if(f.get(bean)==null){
							Object d = f.getType().newInstance();
							f.set(bean, d);
							bean = d;
						}else{
							bean = f.get(bean);
						}
						name = resolver.remove(name);
					}
					Field f = ReflectionFunction.getField(bean.getClass(), name);
					f.setAccessible(true);
					f.set(bean, f.getType().newInstance());
					name = resolver.remove(name);
					
				}
			}
			for (int i = 0; i < fieldNames.length; i++) {
				String fieldName = fieldNames[i];
				if(fieldName.lastIndexOf(".")>0){
					String aliasName = fieldName.substring(0,fieldName.lastIndexOf("."));
					if(searchAliasMap.containsKey(aliasName)){
						fieldName = fieldName.replace(aliasName, searchAliasMap.get(aliasName));
					}
				}
				BeanUtils.copyProperty(instance, fieldName, tuple[i]);
			}
		} catch (InstantiationException e) {
			if (LOGGER.isErrorEnabled()) LOGGER.error(e.getMessage());
		} catch (IllegalAccessException e) {
			if (LOGGER.isErrorEnabled()) LOGGER.error(e.getMessage());
		} catch (InvocationTargetException e) {
			if (LOGGER.isErrorEnabled()) LOGGER.error(e.getMessage());
		} catch (SecurityException e) {
			if (LOGGER.isErrorEnabled()) LOGGER.error(e.getMessage());
		} catch (NoSuchFieldException e) {
			if (LOGGER.isErrorEnabled()) LOGGER.error(e.getMessage());
		}
		return instance;
	}

}
