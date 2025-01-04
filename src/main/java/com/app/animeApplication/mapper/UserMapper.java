package com.app.animeApplication.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.animeApplication.entity.AuthProvider;
import com.app.animeApplication.entity.Role;
import com.app.animeApplication.entity.User;
import com.app.animeApplication.payloads.UserDTO;
import com.app.animeApplication.payloads.registryDTO;
import com.app.animeApplication.services.AuthService;
import com.app.animeApplication.services.UserService;

@Component
public class UserMapper {
	
	@Autowired
	private UserService userService;
	
	
	public registryDTO ToRegistryDTO(String email , String password , String avatar) {
		registryDTO registryDTO = new registryDTO();
		registryDTO.setEmail(email);
		registryDTO.setPassword(password);
		registryDTO.setAvatar(avatar);
        return registryDTO;
    }
	
	 public User registryToEntity(registryDTO registryDTO , String RoleName, int providerID) {
	       
		 	User principal = new User();
		 	
		 	List<Role> roles = new ArrayList<>();
			roles.add(userService.assignRole(principal, RoleName));
			
			AuthProvider provider = new AuthProvider();
			
			switch (providerID) {
			case 1: {
				provider.setProviderId(1);
				provider.setProviderCode("IN");
				provider.setProviderName("INTERNAL");
				break;
			}
			case 2: {
				provider.setProviderId(2);
				provider.setProviderCode("GG");
				provider.setProviderName("GOOGLE");
				break;
			}
			
			}
			
			
			principal.setEmail(registryDTO.getEmail());
			
			principal.setPassword(this.userService.generatePassword(registryDTO.getPassword()));
			
			principal.setFullname(registryDTO.getName());
			
			principal.setAvatar(registryDTO.getAvatar());
			
			principal.setRoles(roles);
			
			principal.setCreationdate(LocalDate.now());
			
			principal.setLastlogin(LocalDate.now());
			
			principal.setAuthProvider(provider);
		 
	        return principal;
	    }
	 
	 public UserDTO toUserDTO(User user){
		 UserDTO userDTO = new UserDTO();
		 
		 userDTO.setUserid(user.getUserid());
		 userDTO.setEmail(user.getEmail());
		 userDTO.setAvatar(user.getAvatar());
		 userDTO.setName(user.getFullname());
		 
		 return userDTO;
	 }
	 
	 public User toUserEntity(UserDTO userDTO) {
		 
		 User user = new User();
		 
		 user.setUserid(userDTO.getUserid());
		 user.setEmail(userDTO.getEmail());
		 user.setFullname(userDTO.getName());
		 user.setAvatar(userDTO.getAvatar());
		 
		 return user;
	 }
	 
	 
}










