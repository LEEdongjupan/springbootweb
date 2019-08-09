package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.java.Log;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	ZerockUsersService zerockUsersService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		log.info("security config..............");
		
	    http.authorizeRequests().antMatchers("/guest/**").permitAll();	    
	    http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER").antMatchers("/manager/**").hasRole("ADMIN");
	    http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
	    
	    http.formLogin().loginPage("/login");
	    http.exceptionHandling().accessDeniedPage("/accessDenied");
	    http.logout().invalidateHttpSession(true);
	    http.userDetailsService(zerockUsersService);

	}
	
	  @Autowired
	  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	

			log.info("build Auth global........");

			auth.userDetailsService(zerockUsersService).passwordEncoder(passwordEncoder());

	  }

	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
//		return new PasswordEncoder() {
//
//			@Override
//			public String encode(CharSequence rawPassword) {
//				
//				return rawPassword.toString();
//			}
//
//			@Override
//			public boolean matches(CharSequence rawPassword, String encodedPassword) {
//				
//				log.info("matches:" + rawPassword+":" + encodedPassword );
//				return rawPassword.equals(encodedPassword);
//			}
//			
//		};
//		
	
		return new BCryptPasswordEncoder();
	}
	
	

	
}
