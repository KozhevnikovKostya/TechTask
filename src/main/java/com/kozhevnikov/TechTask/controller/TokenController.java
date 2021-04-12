package com.kozhevnikov.TechTask.controller;

import com.kozhevnikov.TechTask.model.dto.LoginDto;
import com.kozhevnikov.TechTask.model.User;
import com.kozhevnikov.TechTask.repository.UserRepository;
import com.kozhevnikov.TechTask.security.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @ApiOperation(value = "Create token")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created token"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginDto loginDto){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            User user = userRepository.findByUsername(loginDto.getUsername())
                                      .orElseThrow(()-> new UsernameNotFoundException(String.format("User with username: %s not found", loginDto.getUsername())));
            String token = jwtTokenProvider.createToken(loginDto.getUsername(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", loginDto.getUsername());
            response.put("token", token);
            return ResponseEntity.ok(response);

        }catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid credentials", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
