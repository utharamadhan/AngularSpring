package id.base.app.sms;

import id.base.app.util.StringFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sanz
 *  
 */
public class SmsDestinationLocator {
	
	public List<SmsDestination> resolveSmsDestination(String targets,Long merchant , Long entry, String delimiter) {
		String[] split = targets.split(delimiter);
		List<SmsDestination> results = new ArrayList<SmsDestination>();
		if (split.length == 0) {
			return results;
		}
		for (String target: split) {
			results.addAll(resolveSmsDestination(target, merchant,entry));
		}
		
		return results;
	}
	
	public List<SmsDestination> resolveSmsDestination(String constant,Long merchant , Long entry) {
		List<SmsDestination> defaultResult;
		constant = StringFunction.trim(constant);
		if (constant != null && constant.length() > 0) {
			if(constant.equals(SmsDestinationResolver.MERCHANT_PARTNER))
			   return merchantPartnerResolver.resolveSmsDestination(merchant);
			if(constant.equals(SmsDestinationResolver.INITIATOR))
				return workflowInitiatorResolver.resolveSmsDestination(entry);
			if(constant.equals(SmsDestinationResolver.LAST_ACTOR))
				return workflowLastActorResolver.resolveSmsDestination(entry);
			if(constant.equals(SmsDestinationResolver.USER_HANDLE_MERCHANT))
				return merchantAdminResolver.resolveSmsDestination(merchant);
			
			defaultResult = new ArrayList<SmsDestination>();
			defaultResult.add(new SmsDestination(constant, constant));
		} else {
			defaultResult = new ArrayList<SmsDestination>();
		}
		
		return defaultResult;
	}
	
	public void setMerchantPartnerResolver(
			SmsDestinationResolver merchantPartnerResolver) {
		this.merchantPartnerResolver = merchantPartnerResolver;
	}
	public void setWorkflowInitiatorResolver(
			SmsDestinationResolver workflowInitiatorResolver) {
		this.workflowInitiatorResolver = workflowInitiatorResolver;
	}
	public void setWorkflowLastActorResolver(
			SmsDestinationResolver workflowLastActorResolver) {
		this.workflowLastActorResolver = workflowLastActorResolver;
	}
	public void setMerchantAdminResolver(
			SmsDestinationResolver merchantAdminResolver) {
		this.merchantAdminResolver = merchantAdminResolver;
	}

	private SmsDestinationResolver merchantPartnerResolver;
	private SmsDestinationResolver workflowInitiatorResolver;
	private SmsDestinationResolver workflowLastActorResolver;
	private SmsDestinationResolver merchantAdminResolver;
	
    
}
