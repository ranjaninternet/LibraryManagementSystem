package com.example.LMS.Controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
 
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        //TODO: process POST request
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        LmsUserDetails userDetails = (LmsUserDetails) auth.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getUsername(), userDetails.getRole()));
    }
    
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        //TODO: process POST request
        RegisterRequestWithRole WithRole = new RegisterRequestWithRole();
        WithRole.setUsername(request.getUsername());
        WithRole.setPassword(request.getPassword());
        WithRole.setEmail(request.getEmail());
        WithRole.setRole(request.getRole());
        return ResponseEntity.ok(new RegisterResponse("Registration successful"));
        
        var userDto = userService.registerNewUser(WithRole);
        String token = jwtUtil.generateToken(new LmsUserDetails(userDto.getUsername(), userDto.getEmail(), userDto.getRole()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse("Registration successful", userDto.getUsername(), userDto.getEmail(), userDto
     }
    

}
