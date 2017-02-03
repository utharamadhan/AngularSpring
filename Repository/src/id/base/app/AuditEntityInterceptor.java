package id.base.app;

import id.base.app.SystemConstant;
import id.base.app.valueobject.BaseEntity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditEntityInterceptor extends EmptyInterceptor{
	
	protected static Logger LOGGER = LoggerFactory.getLogger(AuditEntityInterceptor.class);

	private static final long serialVersionUID = -3574710795945495186L;

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		String username = SystemConstant.SYSTEM_USER;
		try{
			SecurityContext secureContext = SecurityContextHolder.getContext();
			Authentication auth = secureContext.getAuthentication();
			username = auth.getName();
		}catch(Exception e){
			//use system username
			LOGGER.warn("onFlushDirty:Authentication exception, maybe username not exist in this context?");
		}
		if(entity instanceof BaseEntity){
			LOGGER.trace("onFlushDirty for " + entity.getClass().getSimpleName());
			int modifiedByIdx = ArrayUtils.indexOf(propertyNames, "modifiedBy");
			int modifiedTimeIdx = ArrayUtils.indexOf(propertyNames, "modificationTime");
			currentState[modifiedByIdx] = username;
			currentState[modifiedTimeIdx] = new Date();
			return true;
		}
		return super.onFlushDirty(entity, id, currentState, previousState,
				propertyNames, types);
	}
	
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		String username = SystemConstant.SYSTEM_USER;
		try{
			SecurityContext secureContext = SecurityContextHolder.getContext();
			Authentication auth = secureContext.getAuthentication();
			username = auth.getName();
		}catch(Exception e){
			//use system username
			LOGGER.warn("onSave:Authentication exception, maybe username not exist in this context?");
		}
		if(entity instanceof BaseEntity){
			LOGGER.trace("onSave for " + entity.getClass().getSimpleName());
			int createdByIdx = ArrayUtils.indexOf(propertyNames, "createdBy");
			int createdTimeIdx = ArrayUtils.indexOf(propertyNames, "creationTime");
			int modifiedByIdx = ArrayUtils.indexOf(propertyNames, "modifiedBy");
			int modifiedTimeIdx = ArrayUtils.indexOf(propertyNames, "modificationTime");
			state[createdByIdx] = username;
			state[createdTimeIdx] = new Date();
			state[modifiedByIdx] = username;
			state[modifiedTimeIdx] = new Date();
			return true;
		}
		return super.onSave(entity, id, state, propertyNames, types);
	}
}
