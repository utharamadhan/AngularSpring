package id.base.app.ldap;

import org.springframework.ldap.core.LdapTemplate;



public abstract class BaseSpringLdap<T> implements LdapAuthentication<T>  {

	protected LdapTemplate ldapTemplate;

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}
	
}
