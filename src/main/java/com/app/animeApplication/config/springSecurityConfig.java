package com.app.animeApplication.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.app.animeApplication.filters.jwtFilter;
import com.app.animeApplication.provider.CustomAccessDeniedHandler;
import com.app.animeApplication.provider.CustomAuthenticationEntryPoint;
import com.app.animeApplication.provider.JWTprovider;
import com.app.animeApplication.provider.customAuthenticationSuccessHandler;
import com.app.animeApplication.provider.customFailHandler;
import com.app.animeApplication.services.UserService;


@Configuration
@EnableWebSecurity
public class springSecurityConfig {

	@Autowired
	private JWTprovider jwTprovider;
	
	@Autowired
	private customAuthenticationSuccessHandler customOauth2LoginSuccesHandler;
	
	@Autowired
	private customFailHandler failHandler;
	
	@Autowired
	private CustomAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;
	
	@Bean
	public AuthenticationManager authenticationManager(UserService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf-> csrf.disable())
		
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.exceptionHandling(handler-> handler
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler)
				)
		.authorizeHttpRequests(auth -> 
			auth.requestMatchers(appConstants.PUBLIC_URLS).permitAll()
			.requestMatchers(appConstants.ADMIN_URLS).hasAnyRole( appConstants.USER, appConstants.ADMIN)
			.requestMatchers(appConstants.USER_URLS).hasAnyRole(appConstants.USER)
			.anyRequest().authenticated()
		)
		.addFilterBefore(new jwtFilter(jwTprovider), UsernamePasswordAuthenticationFilter.class)
		.oauth2Login(oauth2-> oauth2
				.successHandler(customOauth2LoginSuccesHandler)
				.failureHandler(failHandler)
		)
		.cors();
		return http.build();

	}
	
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));  
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  
        corsConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization")); 
        corsConfig.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);  

        return source;
    }
	
	
}
