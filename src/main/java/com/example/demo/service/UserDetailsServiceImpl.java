package com.example.demo.service;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.ERole;
import com.example.demo.entity.RegistrationEntity;
import com.example.demo.entity.RegistrationEntityDto;
import com.example.demo.entity.Role;
import com.example.demo.model.RestaurantModel;
import com.example.demo.repository.RegistrationDtoRepository;
import com.example.demo.repository.RegistrationRepository;
import com.example.demo.repository.RoleRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	RegistrationRepository registrationRepository;
	
	@Autowired
	RegistrationDtoRepository dtoRepo;
	
	@Autowired
	RoleRepository roleRepository;
	public List<RegistrationEntityDto> getAllUsers(){
		List<RegistrationEntityDto> list1=dtoRepo.findAll();
//		List<RegistrationEntityDto> list2=new ArrayList<RegistrationEntityDto>();
//		for(RegistrationEntity l:list1) {
//			list2.add(new RegistrationEntityDto(l.getId(), l.getUsername(), l.getEmail(), l.getRoles()));
//		}
//		return list2;
//		List<RegistrationEntityDto> list=dtoRepo.findAll();
//		List<RegistrationEntity> listEntity=registrationRepository.findAll();
//		Set<RegistrationEntityDto> dtoSet=new HashSet<RegistrationEntityDto>();
//		int i=0;
//		for(RegistrationEntity l:listEntity) {
//			System.out.println("registrationEntity===="+l);
//			if(l.getUsername().equalsIgnoreCase(list.get(i).getUsername())) {
//				list.add(new RegistrationEntityDto(l.getId(), l.getUsername(), l.getEmail(), l.getRoles()));
//			}
//			
//			
//		}
//		dtoSet.addAll(list);
		
		return list1;
	}
//	
//	public RegistrationEntity getById(long id) {
//		return null;
//	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RegistrationEntity user = registrationRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

	private List<GrantedAuthority> getGrantedAuthorities(Set<Role> roles){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
        for(Role rolesname:roles) {
        	authorities.add(new SimpleGrantedAuthority("ROLE_" + rolesname.getName()));	
		}
		
        return authorities;
    }

	public ResponseEntity<RegistrationEntityDto>  getUsertById(long id){
		
		Optional<RegistrationEntityDto> user=dtoRepo.findById(id);
		if(user.isPresent()) {
			return new ResponseEntity<RegistrationEntityDto>(user.get(),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
public RegistrationEntityDto changeRole(@PathVariable long id,@RequestBody RegistrationEntity resm) {
		System.out.println("resmmm==="+resm);
		String st="ROLE_ADMIN";
		String st1="ROLE_USER";
			resm.setPassword("*****");
		    Optional<RegistrationEntity> restdata=registrationRepository.findById(id);
		   
		    RegistrationEntityDto entity=new RegistrationEntityDto(resm.getId(), resm.getUsername(), resm.getEmail(), resm.getRoles());
		     System.out.println(restdata);
		     System.out.println(entity);
		    if(restdata.isPresent()) {
		       
			   System.out.println("inside outer if");
			   System.out.println(resm.getRoles());
				if(!resm.getRoles().isEmpty()) {
					System.out.println("inside inner if");
					System.out.println(resm.getRoles());
					Set<Role> role=new HashSet<Role>();
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				     role.add(adminRole);
					entity.setRoles(role);
					System.out.println(entity.getRoles());
					
				}
				
		    }
		 
		return dtoRepo.save(entity);
	}
    

public ResponseEntity<HttpStatus> deleteByUsername(@PathVariable long id){
	try {
		
		dtoRepo.deleteById(id);
		registrationRepository.deleteById(id);
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	catch(Exception e) {
		return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
	
}
