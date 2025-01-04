package com.app.animeApplication.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.animeApplication.provider.JWTprovider;

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
		
		LOGGER.info("" + request.getUserPrincipal());
		try {
            String jwt = this.resolveToken(request);
            if (StringUtils.hasText(jwt)) {
                if (this.JWTProvider.validateToken(jwt)) {
                    Authentication authentication = this.JWTProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);

//            this.resetAuthenticationAfterRequest();
        } catch (JwtException eje) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
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
