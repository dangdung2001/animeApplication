package com.app.animeApplication.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {

	@Value("${recaptcha.secret.key}")
    private String secretKey;

    private final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean validateCaptcha(String captchaToken) {
    	
        RestTemplate restTemplate = new RestTemplate();
        String url = RECAPTCHA_VERIFY_URL + "?secret=" + secretKey + "&response=" + captchaToken;
        
        RecaptchaResponse response = restTemplate.postForObject(url, null, RecaptchaResponse.class);
        return response != null && response.isSuccess();
    }

    
    private static class RecaptchaResponse {
        private boolean success;
        private String hostname;
        
        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
        
        public String getHostname() {
        	return hostname;
        }
        
        public void setHostname(String hostname) {
        	this.hostname = hostname;
        }
    }
	
}
