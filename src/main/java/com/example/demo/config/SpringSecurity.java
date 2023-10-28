package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringSecurity {
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(c -> c.disable())
				.authorizeHttpRequests(req -> req.requestMatchers("/admin/**")
													  .authenticated()
													  .anyRequest().permitAll())
				.formLogin(form -> form.loginPage("/login")
										   .loginProcessingUrl("/adminLogin")
										   .defaultSuccessUrl("/admin/dashboard").permitAll());
//				.logout(form -> form.invalidateHttpSession(true)
//										.clearAuthentication(true)
//										.logoutRequestMatcher(new AntPathRequestMatcher("/login"))
//										.logoutSuccessUrl("/login?logout").permitAll()
//				);
		return http.build();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customUserDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	 
}
