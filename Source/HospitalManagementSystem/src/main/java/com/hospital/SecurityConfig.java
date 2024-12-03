package com.hospital;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
		      		.withUser("patient").password(encoder.encode("patient")).roles("USER", "PATIENT")
				.and().withUser("doctor").password(encoder.encode("doctor")).roles("USER", "DOCTOR")
				.and().withUser("Nirmal").password(encoder.encode("nirmal")).roles("USER", "DOCTOR")
				.and().withUser("Gowtham").password(encoder.encode("gowtham")).roles("USER", "DOCTOR")
				.and().withUser("Peter").password(encoder.encode("peter")).roles("USER", "DOCTOR")
				.and().withUser("John").password(encoder.encode("john")).roles("USER", "PATIENT")
				.and().withUser("James").password(encoder.encode("james")).roles("USER", "PATIENT")
				.and().withUser("surya").password(encoder.encode("surya")).roles("USER", "PATIENT");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().frameOptions().disable().and().authorizeRequests()
				.antMatchers("/", "/main", "/signup", "/login*").permitAll().antMatchers("/doctors/**")
				.hasRole("DOCTOR").antMatchers("/patients/**").hasRole("PATIENT").antMatchers("/anonymous*").anonymous()
				.antMatchers("/h2-console/**").permitAll().anyRequest().authenticated().and().formLogin()
				.defaultSuccessUrl("/showPostLogin", false).permitAll().and().logout().logoutSuccessUrl("/")
				.permitAll();
	}

}
