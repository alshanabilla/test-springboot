// package com.example.demo.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.DisabledException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UserDetails;
// // import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.demo.config.JwtTokenUtil;
// import com.example.demo.config.MyUserDetails;
// import com.example.demo.dto.LoginDTO;
// import com.example.demo.dto.LoginResponse;

// @RestController
// public class JwtAuthenticationController {
//     @Autowired
// 	private AuthenticationManager authenticationManager;

// 	@Autowired
// 	private JwtTokenUtil jwtTokenUtil;

// 	@Autowired
// 	private MyUserDetails myUserDetails;

// 	@RequestMapping(value = "/account/authenticate", method = RequestMethod.POST)
// 	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO loginDTO) throws Exception {

// 		authenticate(loginDTO.getEmail(), loginDTO.getPassword());

// 		final UserDetails userDetails = myUserDetails
// 				.loadUserByUsername(loginDTO.getEmail());

// 		final String token = jwtTokenUtil.generateToken(userDetails);

// 		return ResponseEntity.ok(new LoginResponse(token));
// 	}

// 	private void authenticate(String username, String password) throws Exception {
// 		try {
// 			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
// 		} catch (DisabledException e) {
// 			throw new Exception("USER_DISABLED", e);
// 		} catch (BadCredentialsException e) {
// 			throw new Exception("INVALID_CREDENTIALS", e);
// 		}
// 	}
// }
