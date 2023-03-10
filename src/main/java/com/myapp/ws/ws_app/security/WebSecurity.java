package com.myapp.ws.ws_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.myapp.ws.ws_app.services.UserService;



@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	  private final UserService userDetailsService;
	  private final BCryptPasswordEncoder bCryptPasswordEncoder;
	  
	  @Autowired
	  public WebSecurity(UserService userDetailsService,
			  BCryptPasswordEncoder bCryptPasswordEncoder) {
	        this.userDetailsService = userDetailsService;
	        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	    }
	  
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		

		http
		    .cors().and()
		    .csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
			.permitAll()
			.antMatchers("/v2/api-docs",
									"/swagger-resources/**",
									"/swagger-ui.html**",
									"/webjars/**")
			.permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(getAuthenticationFilter())
			.addFilter(new com.myapp.ws.ws_app.security.AuthorizationFilter(authenticationManager()))
			.sessionManagement()
		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	protected com.myapp.ws.ws_app.security.AuthenticationFilter getAuthenticationFilter() throws Exception {
	    final com.myapp.ws.ws_app.security.AuthenticationFilter filter = new com.myapp.ws.ws_app.security.AuthenticationFilter(authenticationManager());
	    filter.setFilterProcessesUrl("/users/login");
	    return filter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
}
