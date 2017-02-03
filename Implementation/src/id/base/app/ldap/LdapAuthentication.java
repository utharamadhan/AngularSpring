package id.base.app.ldap;

import id.base.app.exception.SystemException;

import javax.naming.directory.Attributes;

/**
 * <h1>generic interface for ldap access</h1>
 * 
 */
public interface LdapAuthentication<T> {

	public abstract boolean authenticate(T user, String password) throws SystemException ;
	
	public abstract String buildDn(T user) throws SystemException ;
	
	public abstract T mapFromAttributes(Attributes attrs) throws SystemException ;
	
	public abstract Attributes buildAttributes(T user) throws SystemException ;
	
	
}
