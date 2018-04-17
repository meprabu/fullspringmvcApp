package org.ms.DmhThymeLeaf.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DmhSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	@Autowired
    private UserDetailsService userDetailsService;
	
	public void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
				.authorizeRequests().antMatchers("/","/login","/about").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN")
				.antMatchers("/user/**").hasAnyRole("USER")
				.antMatchers("/providers/**").hasAnyRole("PROVIDER")
				.anyRequest().fullyAuthenticated()
			.and()
			.formLogin()
				.loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/user",true)
			.and()
				.logout()
				.permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
	}
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
		
		/*auth.inMemoryAuthentication()
			.withUser("user").password("{noop}password").roles("USER")
			.and()
			.withUser("admin").password("{noop}password").roles("ADMIN")
			.and()
			.withUser("provider").password("{noop}password").roles("PROVIDER");*/
		
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder encoder() {
		return NoOpPasswordEncoder.getInstance();
	   // return new BCryptPasswordEncoder();
	}
	
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**");
		web.ignoring().antMatchers("/css/**","/fonts/**","/libs/**","/html/**");
	}
	

}
