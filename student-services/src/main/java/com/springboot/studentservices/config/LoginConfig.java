package com.springboot.studentservices.config;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class LoginConfig extends WebSecurityConfigurerAdapter {

	 	@Autowired
	    private UserDetailsService userDetailsService;
	 	


	    @Bean
	    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	    }
	   
	    
	   @Bean
	    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
		   SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
	       handler.setUseReferer(true);
	       return handler;
	    } 
	    
	  
	    

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	// In the future, /addStock will require login. For testing purposes now it is excluded from the list of pages 
	    	// that require login
	    	// A logged in user cannot access the /signup URL -> he must log out and then try to signup
	        http
	                .authorizeRequests().antMatchers("/admin").hasAnyRole("ADMIN","USER").antMatchers("/signup").not().authenticated()
	                    .antMatchers("/","/welcome","/addCompany","/css/**","/js/**","/img/**","/viewArticle").permitAll()
	                    .anyRequest().authenticated()
	                    .and()
	                .formLogin()
	                    .loginPage("/login")
	                    .successHandler(successHandler())
	                    .permitAll()
	                    .and()
	                    .rememberMe()
	                    .alwaysRemember(true)
	                    .and()
	                .logout()
	                .logoutSuccessUrl("/welcome")
	                    .permitAll(); 
	    }

	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	    }
}