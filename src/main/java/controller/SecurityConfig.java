
package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public AuthenticationProvider authProvider()
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}
//
//	
//	 @Override
//	  public void configure(AuthenticationManagerBuilder builder)
//	          throws Exception {
//	      builder.inMemoryAuthentication()
//	             .withUser("dip@gmail.com")
//	             .password("{noop}123")
//	             .roles("USER");
//	  }
	
	
//	
//	public UserDetailsService userdetailsser() {
//		List<UserDetails> user= new ArrayList<>();
//		user.add(User.withDefaultPasswordEncoder().username("dip@gmail.com").password("Dai").roles("USER").build());
//		return new InMemoryUserDetailsManager(user);
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http
			.authorizeRequests()
//				.antMatchers("/history").authenticated()
//				.anyRequest().authenticated()
				.anyRequest().permitAll()
				.and()
			.formLogin()
				.loginPage("/test").permitAll()
				.defaultSuccessUrl("/home", true)
		        .failureUrl("/login?error=true")
				.and()
			.logout().invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/logout-success").permitAll()
				.and()
			.csrf().disable();
		
		
	}
	
	@Bean
	public BCryptPasswordEncoder encodePass() {
		return new BCryptPasswordEncoder();
	}
	

}

