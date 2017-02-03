package id.base.app.mail;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

public class MailDestination {
	String name;
	String emailAddress;
	
	public MailDestination() {
	}
	
	public MailDestination(String name, String emailAddress) {
		this.name = name;
		this.emailAddress = emailAddress;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public static class MailDestinationTransformer implements ResultTransformer {

		@Override
		public List transformList(List list) {
			return list;
		}

		@Override
		public Object transformTuple(Object[] tuples, String[] aliases) {
			return new MailDestination((String)tuples[0], (String)tuples[1]);
		}
		
	}
}
