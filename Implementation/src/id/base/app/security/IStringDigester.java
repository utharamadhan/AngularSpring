package id.base.app.security;

import org.springframework.stereotype.Component;

@Component

public interface IStringDigester {	
	
	public String digest(String text);
	
	public String digest(String text, Integer iterationTime);

	public boolean matches(String string1, String string2);
	
}
