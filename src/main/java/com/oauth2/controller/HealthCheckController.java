package com.oauth2.controller;

import com.oauth2.model.AuthenticationRequest;
import com.oauth2.model.AuthenticationResponse;
import com.oauth2.service.UserValidateService;
import com.oauth2.util.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserValidateService userValidateService;

    @Autowired
    private JWTUtility jwtUtility;

    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "Application Running";
    }

    @PostMapping("/gettoken")
    public ResponseEntity<?> getToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Invalid Credential");
        }

        final UserDetails userDetails = userValidateService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtUtility.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

}
