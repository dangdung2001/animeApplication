package com.app.animeApplication.provider;


import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;


@Component
public class JWTprovider {

	private static final Logger LOGGER = LoggerFactory.getLogger(JWTprovider.class);

    private static final String AUTHORITIES_KEY = "auth";

    @Value("${spring.security.authentication.jwt.AccessTokenValidity}")
    private long AccessTokenValidityMs;
    
    @Value("${spring.security.authentication.jwt.RefreshTokenValidity}")
    private long RefreshTokenValidityMs;

    @Value("${spring.security.authentication.jwt.secret}")
    private String secretKey;

    @SuppressWarnings("deprecation")
	public String createToken(Authentication authentication , long tokenValidityInMillis) throws InvalidKeyException{
    	
        String authorities = authentication.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(","));

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expirationDateTime = now.plus(tokenValidityInMillis, ChronoUnit.MILLIS);

        Date issueDate = Date.from(now.toInstant());
        Date expirationDate = Date.from(expirationDateTime.toInstant());

        return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
                    .signWith(SignatureAlgorithm.HS256, this.secretKey).setIssuedAt(issueDate).setExpiration(expirationDate).compact();
    }
    
    public String createAccessToken(Authentication authentication) throws InvalidKeyException{
	
    	return createToken(authentication , AccessTokenValidityMs);
    }
    
    
    public String createRefreshToken(Authentication authentication) throws InvalidKeyException{
    	
    	return createToken(authentication, RefreshTokenValidityMs);
    }

    public Authentication getAuthentication(String token) {

        @SuppressWarnings("deprecation")
		Claims claims = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        
        LOGGER.info("CLAIMS : " + claims);

        Collection<? extends GrantedAuthority> authorities = Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(",")).stream()
                    .<GrantedAuthority>map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);
        
        LOGGER.info("principal :" + principal);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    @SuppressWarnings("deprecation")
	public void validateToken(String authToken) {

            Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(authToken);
        }
     }
    
    
    







	

