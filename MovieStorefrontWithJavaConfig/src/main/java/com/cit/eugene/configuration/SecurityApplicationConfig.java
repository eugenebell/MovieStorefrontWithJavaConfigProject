package com.cit.eugene.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityApplicationConfig extends WebSecurityConfigurerAdapter {

	private DataSource dataSource;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("select USERNAME, PASSWORD, ENABLED from USERS where USERNAME = ?")
        .authoritiesByUsernameQuery("select USERNAME, AUTHORITY from AUTHORITIES where USERNAME = ?");
    }
	
	
	
	 @Override
	  public void configure(WebSecurity web) throws Exception {
	    web
	      .ignoring()
	         .antMatchers("/images/**")
	         .antMatchers("/scripts/**")
	         .antMatchers("/styles/**");
	  }
	 
	 @Override
	  protected void configure(HttpSecurity http) throws Exception {
		 http
	        .authorizeRequests()
	        .antMatchers("/index.html","/denied.htm","/favicon.ico").permitAll() 
	        .antMatchers("/movies/movieListing").hasRole("USER")
	        //.antMatchers().hasRole("ADMIN, USER")
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	            .loginPage("/movies/login")
	            .loginProcessingUrl("/j_spring_security_check")
	            .usernameParameter("username")
	            .passwordParameter("password")
	            .permitAll();
	  }
	
}
