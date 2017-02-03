package id.base.app.sms;

import java.util.List;

import org.hibernate.transform.ResultTransformer;

public class SmsDestination {
	String number;
	String name;
	
	public SmsDestination(String name, String number) {
		this.number = number;
		this.name = name;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String text) {
		this.name = text;
	}

	public static class SmsDestinationTransformer implements ResultTransformer {

		@Override
		public List transformList(List list) {
			return list;
		}

		@Override
		public Object transformTuple(Object[] tuples, String[] aliases) {
			return new SmsDestination((String)tuples[0], (String)tuples[1]);
		}
		
	}
}
