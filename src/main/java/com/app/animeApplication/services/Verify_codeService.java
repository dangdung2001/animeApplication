package com.app.animeApplication.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.animeApplication.entity.Verification_code;
import com.app.animeApplication.reposiroty.verifi_codeRepository;

@Service
public class Verify_codeService {

	@Autowired
	private verifi_codeRepository verifi_codeRepository;
	
	
	public Verification_code getVerifyCodeByMail (String mail) {
		
		Optional<Verification_code> optional = this.verifi_codeRepository.findByEmail(mail);
		
		if(optional.isEmpty()) {
			return null;
		}
		
		return optional.get();
	}
	
	public Verification_code saveVerifyCodeByMail (String mail , String code) {
		
		Verification_code verification_code = new Verification_code();
		
		verification_code.setMail(mail);
		verification_code.setCode(code);
		verification_code.setCreated_at(LocalDateTime.now());
		verification_code.setExpires_at(LocalDateTime.now().plusMinutes(3));
		verification_code.setStatus("PENDING");
		
		return  this.verifi_codeRepository.save(verification_code);
	}
	
	public void RemoveVerifyCodeByMail (Verification_code verification_code) {
		
		this.verifi_codeRepository.delete(verification_code);
	}
	
}
