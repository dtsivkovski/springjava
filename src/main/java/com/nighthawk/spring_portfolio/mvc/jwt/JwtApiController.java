package com.nighthawk.spring_portfolio.mvc.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.nighthawk.spring_portfolio.mvc.person.*;
import com.nighthawk.spring_portfolio.mvc.ModelRepository;

@RestController
@CrossOrigin
public class JwtApiController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
    private ModelRepository repository;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Person authenticationRequest) throws Exception {
		System.out.println("Auth URL Reached - " + authenticationRequest.getEmail());
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		System.out.println("Completed authenticate() function");
		final UserDetails userDetails = jwtUserDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		// final Person a = repository.getByEmail(authenticationRequest.getEmail());
		final ResponseCookie tokenCookie = ResponseCookie.from("jwt", token)
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(3600)
			.sameSite("none")
			// .domain("example.com") // Set to backend domain
			.build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, tokenCookie.toString()).build();
	}

	@PostMapping("/register")
	public ResponseEntity<?> createPersonAndAuthToken(@RequestBody String np) throws Exception {
		System.out.println(np);
		Person a;
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(np);
			a = new Person(obj.get("email").toString(), obj.get("password").toString(), obj.get("name").toString(), Boolean.valueOf(obj.get("stats").toString()), Boolean.valueOf(obj.get("chem").toString()), Boolean.valueOf(obj.get("phys").toString()), Boolean.valueOf(obj.get("bio").toString()));
			if (repository.getByEmail(a.getEmail()) != null) {
				throw new Exception("USER_ALREADY_EXISTS");
			}
			else {
				repository.save(a);
				return createAuthenticationToken(a);
			}
		} catch (Exception e) {
			throw new Exception("USER_ALREADY_EXISTS", e);
		}		// auth and get user details
	}

	@GetMapping("/logoutJWT")
	public ResponseEntity<?> logout() {
		final ResponseCookie tokenCookie = ResponseCookie.from("jwt", "")
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(0)
			.sameSite("none")
			.build();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, tokenCookie.toString()).build();
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			System.out.println("Reached authenticate() function");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			System.out.println("Authenticated user " + username + " successfully");
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}