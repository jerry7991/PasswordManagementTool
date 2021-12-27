package com.epam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.config.JWTTokenHelper;
import com.epam.dto.UserDetailsDto;

@RestController
@CrossOrigin
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JWTTokenHelper jWTTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody UserDetailsDto userDTO) throws UsernameNotFoundException {
		this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userDTO.getUserName(), userDTO.getMasterPassword()));

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(userDTO.getUserName());
		String token = this.jWTTokenHelper.generateToken(userDetails);
		userDTO.setToken(token);
		return ResponseEntity.ok(userDTO);
	}
}
