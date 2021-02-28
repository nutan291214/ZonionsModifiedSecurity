package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ERole;
import com.example.demo.entity.RegistrationEntity;
import com.example.demo.entity.RegistrationEntityDto;
import com.example.demo.entity.Role;
import com.example.demo.model.request.RegistrationRequestModel;
import com.example.demo.model.request.UserLoginRequestModel;
import com.example.demo.model.response.JwtResponse;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.repository.RegistrationDtoRepository;
import com.example.demo.repository.RegistrationRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.service.UserDetailsServiceImpl;
import com.example.demo.shared.JwtUtils;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
     RegistrationRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RegistrationDtoRepository dtoRepository;
	@Autowired
	UserDetailsServiceImpl service;
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequestModel loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		Optional<RegistrationEntityDto> findByUsername = dtoRepository.findByUsername(loginRequest.getUsername());
		List<String> role=new ArrayList<>();
		RegistrationEntityDto user=findByUsername.get();
	
			
			for(Role r:user.getRoles()) {
	            role.add(r.getName().name());
	            System.out.println("Getting role......"+r.getName().name());
	        	roles=role;
	        }

		
		
		
		
       System.out.println(roles);
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequestModel signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		RegistrationEntity user = new RegistrationEntity(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));
		
		RegistrationEntityDto user1 = new RegistrationEntityDto(signUpRequest.getUsername(), 
				 signUpRequest.getEmail());
		
       
		Set<String> strRoles = signUpRequest.getRole();
		
		Set<Role> roles = new HashSet<>();
		
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
//		Optional<Role> userRole=roleRepository.findByName(ERole.ROLE_USER);
//		roles.add(userRole);

//		if (strRoles == null) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//		} else {
//			strRoles.forEach(role -> {
//				switch (role) {
//				case "admin":
//					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(adminRole);
//
//					break;
//				case "mod":
//					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(modRole);
//
//					break;
//				default:
//					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(userRole);
//				}
//			});
//		}
        
		user.setRoles(roles);
		user1.setRoles(roles);
		userRepository.save(user);
		dtoRepository.save(user1); 
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("/users")
	public List<RegistrationEntityDto> getAll(){
		System.out.println(service.getAllUsers());
		return service.getAllUsers();
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<RegistrationEntityDto> getUserById(@PathVariable long id){
		return service.getUsertById(id);
	}

	@PutMapping("/users/{id}")
	public RegistrationEntityDto changeRole(@PathVariable long id,@RequestBody RegistrationEntity regi ) {
		return service.changeRole(id, regi);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteByUsername(@PathVariable long id){
		
		return service.deleteByUsername(id);
		
	}

}
