package id.base.app.core;

import id.base.app.rest.RestConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

/*@Configuration
@EnableWebSecurity*/
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
		UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(customUserDetailsService);
		customAuthenticationProvider.setPreAuthenticatedUserDetailsService(wrapper);
        builder.authenticationProvider(customAuthenticationProvider);
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	RequestHeaderAuthenticationFilter reqHeaderFilter = new RequestHeaderAuthenticationFilter();
    	reqHeaderFilter.setPrincipalRequestHeader(RestConstant.USER_CALLER);
    	reqHeaderFilter.setAuthenticationManager(authenticationManager());
    	http.addFilter(reqHeaderFilter).sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    	.csrf().disable()
		.authorizeRequests()
			.anyRequest().authenticated();
    }
}
