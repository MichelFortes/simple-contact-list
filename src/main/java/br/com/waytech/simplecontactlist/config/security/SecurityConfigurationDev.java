package br.com.waytech.simplecontactlist.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.waytech.simplecontactlist.config.AppProfiles;
import br.com.waytech.simplecontactlist.config.security.service.AuthenticationService;
import br.com.waytech.simplecontactlist.config.security.service.JWTService;
import br.com.waytech.simplecontactlist.controller.admin.AdminController;
import br.com.waytech.simplecontactlist.controller.user.UserController;
import br.com.waytech.simplecontactlist.persistence.repository.UserRepository;

@Profile({AppProfiles.DEV})
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurationDev extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JWTService jwtService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
		.antMatchers("/h2-console/**").permitAll()
        .antMatchers(HttpMethod.POST, "/public/**").permitAll()
        .antMatchers(UserController.PATH + "/**").hasAuthority("USER")
        .antMatchers(AdminController.PATH + "/**").hasAuthority("ADMIN")
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JWTSecurityFilter(userRepository, jwtService), UsernamePasswordAuthenticationFilter.class);
		http.headers().frameOptions().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
}
