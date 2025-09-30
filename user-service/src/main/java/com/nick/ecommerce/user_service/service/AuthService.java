package com.nick.ecommerce.user_service.service;

import com.nick.ecommerce.user_service.dto.JwtResponse;
import com.nick.ecommerce.user_service.dto.LoginRequest;
import com.nick.ecommerce.user_service.dto.RegisterRequest;
import com.nick.ecommerce.user_service.model.Role;
import com.nick.ecommerce.user_service.model.User;
import com.nick.ecommerce.user_service.repository.RoleRepository;
import com.nick.ecommerce.user_service.repository.UserRepository;
import com.nick.ecommerce.user_service.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;

    public void register(RegisterRequest request, Role.ERole roleType){
        if (userRepository.existsByUsername(request.username())){
            throw new RuntimeException("Username is already taken");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());

        Role role = roleRepository.findByRole(roleType).orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Set.of(role));

        userRepository.save(user);
    }

    public JwtResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.username()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        String token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole().name())).toList()
        ));

        return new JwtResponse(token, user.getUsername(), user.getRoles().stream().map(role -> role.getRole().name()).toList());
    }
}
