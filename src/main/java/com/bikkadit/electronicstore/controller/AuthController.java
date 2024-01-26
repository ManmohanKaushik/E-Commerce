package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.constants.MessageConstants;
import com.bikkadit.electronicstore.dto.JwtRequest;
import com.bikkadit.electronicstore.dto.JwtResponse;
import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.exception.BadRequestException;
import com.bikkadit.electronicstore.security.JwtHelper;
import com.bikkadit.electronicstore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.bikkadit.electronicstore.constants.UriConstants.*;

@CrossOrigin(origins = "http://localhost:9090", exposedHeaders = "token")
@RestController
@RequestMapping(AUTH_URI)
@Slf4j

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



    /**
     * @author Manmohan Sharma
     * @apiNote To addItemToCart data in database
     * @since 1.0v
     * @return CartDto
     */
    @PostMapping(AUTH_LOGIN)
   // @Secured("ROLE_ADMIN")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        log.info("Initiated request for login  ");
        this.doAutheticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);
        UserDto userDto = mapper.map(userDetails, UserDto.class);
        JwtResponse response = JwtResponse.builder().jwtToken(token).user(userDto).build();
        log.info("Completed request for login  ");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAutheticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BadRequestException(MessageConstants.BAD_REQUEST_EXCEPTION);
        }
    }

    /**
     * @author Manmohan Sharma
     * @apiNote To addItemToCart data in database
     * @since 1.0v
     * @return CartDto
     */
    @GetMapping(GET_CURRENT_USER)
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) throws UsernameNotFoundException {
        log.info("Initiated request for get current user ");
        String name = principal.getName();
        log.info("Completed request for get current user ");
        return new ResponseEntity<>(mapper.map(userDetailsService.loadUserByUsername(name), UserDto.class), HttpStatus.OK);

    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto){
        UserDto newUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(newUser,HttpStatus.CREATED);
    }
}
