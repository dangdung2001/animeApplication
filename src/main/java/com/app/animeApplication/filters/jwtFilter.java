package com.app.animeApplication.filters;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.animeApplication.entity.RefreshToken;
import com.app.animeApplication.provider.JWTprovider;
import com.app.animeApplication.services.RefreshTokenService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class jwtFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(jwtFilter.class);

	private final JWTprovider JWTProvider;

	public jwtFilter(JWTprovider JWTprovider) {

		this.JWTProvider = JWTprovider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();

	    if ("/api/public/refresh-token".equals(requestURI)) {
	        filterChain.doFilter(request, response);
	        return;
	    }
		
		try {
			String jwt = this.resolveToken(request);
			if (StringUtils.hasText(jwt)) {

				this.JWTProvider.validateToken(jwt);
				Authentication authentication = this.JWTProvider.getAuthentication(jwt);
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}

		} 
		catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); 
	        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With");
	        response.setHeader("Access-Control-Allow-Credentials", "true"); 
			response.getWriter().write("Invalid or expired JWT token.");
			return;
		}

		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {

		String AUTHORIZATION_HEADER = "Authorization";

		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			String jwt = bearerToken.substring(7, bearerToken.length());
			return jwt;
		}
		return null;
	}

}
