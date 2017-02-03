package id.base.app.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomUtilConfig {

	@Bean(name="numericFormatter")
	public NumericFunction numericFormatter() {
		return new NumericFunction();
	}
}
