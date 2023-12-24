package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.exception.BadRequestException;
import com.bikkadit.electronicstore.security.JwtHelper;
import com.bikkadit.electronicstore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;

    public AuthController() {
    }
private void doAutheticate(String email, String password){
    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(email,password);
    try {
        manager.authenticate(authenticationToken);
    } catch (BadCredentialsException e) {
        throw new BadRequestException(MessageConstants.BAD_REQUEST_EXCEPTION);
    }
}

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) throws UsernameNotFoundException {
        String name = principal.getName();
       //  return new ResponseEntity<>(mapper.map(userDetailsService.loadUserByUsername(name)),UserDto.class);
        return null;
    }
}
