package id.base.app.sms;

import java.util.List;

public interface SmsDestinationResolver {
	
	public static final String MERCHANT_PARTNER = "[MERCHANT_PARTNER]";
	public static final String INITIATOR = "[INITIATOR]";
	public static final String LAST_ACTOR = "[LAST_ACTOR]";
	public static final String USER_HANDLE_MERCHANT = "[USER_HANDLE_MERCHANT]";
	
	
	public List<SmsDestination> resolveSmsDestination(Long key);

}
