package com.search.Sjob.service;

import com.search.Sjob.authentication.AuthenticationRequest;
import com.search.Sjob.authentication.AuthenticationResponse;
import com.search.Sjob.authentication.RegisterRequest;
import com.search.Sjob.model.User;
import com.search.Sjob.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    public AuthenticationResponse login(AuthenticationRequest request) throws AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );

            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            System.out.println("User: " + user);
            var jwtToken = jwtService.generateToken(user);
            return new AuthenticationResponse(jwtToken);
        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid email or password");
        }
    }

    public AuthenticationResponse register(RegisterRequest request){
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getCountry(),
                request.getPhone(),
                request.getGender(),
                request.getPassword(),
                request.getRole()
        );

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        System.out.println("jwt Token : " + jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

}
