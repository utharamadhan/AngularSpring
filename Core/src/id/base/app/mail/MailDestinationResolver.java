package id.base.app.mail;

import java.util.List;

public interface MailDestinationResolver {
	
	public static final String MERCHANT_PARTNER = "[MERCHANT_PARTNER]";
	public static final String INITIATOR = "[INITIATOR]";
	public static final String LAST_ACTOR = "[LAST_ACTOR]";
	public static final String USER_HANDLE_MERCHANT = "[USER_HANDLE_MERCHANT]";
	
	public static final String MERCHANT_PARTNER_ROLES = "[MERCHANT_PARTNER:";
	public static final String USER_HANDLE_MERCHANT_ROLES = "[USER_HANDLE_MERCHANT:";
	
	public static final String CLOSE_ROLES_TAG ="]";


	public List<MailDestination> resolveMailDestination(Long key);
	
	public List<MailDestination> resolveMailDestinationRoles(Long key,String roleCode);

}
