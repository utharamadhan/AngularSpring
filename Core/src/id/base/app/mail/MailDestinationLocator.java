package id.base.app.mail;

import id.base.app.util.StringFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anicka andry
 *  
 */
public class MailDestinationLocator {
	
	public List<MailDestination> resolveMailDestination(String targets,Long merchant , Long entry, String delimiter) {
		String[] split = targets.split(delimiter);
		List<MailDestination> results = new ArrayList<MailDestination>();
		if (split.length == 0) {
			return results;
		}
		for (String target: split) {
			results.addAll(resolveMailDestination(target, merchant,entry));
		}
		
		return results;
	}
	
	public List<MailDestination> resolveMailDestination(String constant,Long merchant , Long entry) {
		List<MailDestination> defaultResult;
		constant = StringFunction.trim(constant);
		if (constant != null && constant.length() > 0) {
			
			if(constant.equals(MailDestinationResolver.MERCHANT_PARTNER))
			   return merchantPartnerResolver.resolveMailDestination(merchant);
			if(constant.equals(MailDestinationResolver.INITIATOR))
				return workflowInitiatorResolver.resolveMailDestination(entry);
			if(constant.equals(MailDestinationResolver.LAST_ACTOR))
				return workflowLastActorResolver.resolveMailDestination(entry);
			if(constant.equals(MailDestinationResolver.USER_HANDLE_MERCHANT))
				return merchantAdminResolver.resolveMailDestination(merchant);
			
			if(constant.startsWith(MailDestinationResolver.MERCHANT_PARTNER_ROLES) && constant.endsWith(MailDestinationResolver.CLOSE_ROLES_TAG)){
				String role=constant.substring(MailDestinationResolver.MERCHANT_PARTNER_ROLES.length(),constant.indexOf(MailDestinationResolver.CLOSE_ROLES_TAG)).trim();
				return merchantPartnerResolver.resolveMailDestinationRoles(merchant,role);
			}
			
			if(constant.startsWith(MailDestinationResolver.USER_HANDLE_MERCHANT_ROLES) && constant.endsWith(MailDestinationResolver.CLOSE_ROLES_TAG)){
				String role=constant.substring(MailDestinationResolver.USER_HANDLE_MERCHANT_ROLES.length(),constant.indexOf(MailDestinationResolver.CLOSE_ROLES_TAG)).trim();
				return merchantAdminResolver.resolveMailDestinationRoles(merchant,role);
			}
			
			defaultResult = new ArrayList<MailDestination>();
			defaultResult.add(new MailDestination(constant, constant));
		} else {
			defaultResult = new ArrayList<MailDestination>();
		}
		
		return defaultResult;
	}
	
	private MailDestinationResolver merchantPartnerResolver;
	
	public void setMerchantPartnerResolver(
			MailDestinationResolver merchantPartnerResolver) {
		this.merchantPartnerResolver = merchantPartnerResolver;
	}
	public void setWorkflowInitiatorResolver(
			MailDestinationResolver workflowInitiatorResolver) {
		this.workflowInitiatorResolver = workflowInitiatorResolver;
	}
	public void setWorkflowLastActorResolver(
			MailDestinationResolver workflowLastActorResolver) {
		this.workflowLastActorResolver = workflowLastActorResolver;
	}
	public void setMerchantAdminResolver(
			MailDestinationResolver merchantAdminResolver) {
		this.merchantAdminResolver = merchantAdminResolver;
	}
	
	private MailDestinationResolver workflowInitiatorResolver;
	private MailDestinationResolver workflowLastActorResolver;
	private MailDestinationResolver merchantAdminResolver;
	
    
}
