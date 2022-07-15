package com.example.springsecurity.Controller;

import com.example.springsecurity.Config.JwtUtils;
import com.example.springsecurity.Config.UserDetailsImpl;
import com.example.springsecurity.DTO.JwtResponse;
import com.example.springsecurity.DTO.LoginRequest;
import com.example.springsecurity.Model.Role;
import com.example.springsecurity.Model.URole;
import com.example.springsecurity.Model.User;
import com.example.springsecurity.Repo.RoleRepo;
import com.example.springsecurity.Repo.UserRepo;
import com.example.springsecurity.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final RoleRepo roleRepo;

    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, RoleRepo roleRepo, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.roleRepo = roleRepo;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok().body(new JwtResponse(userDetails.getId(), jwt));
//        try{
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest)
//            )
//        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestParam("name") String name,
                                          @RequestParam("pass") String pass){
        if(userRepo.findByName(name).isPresent()){
            return ResponseEntity.badRequest().body("Error: User is already existed");
        }
        User user = new User();
        user.setName(name);
        user.setPass(passwordEncoder.encode(pass));
        Set<Role> roleSet = new HashSet<>();
        try{
            Role role = roleRepo.findByRoleName(URole.USER).get();
            roleSet.add(role);
            user.setRoles(roleSet);
            if (userService.saveUser(user) == null){
                return ResponseEntity.ok().body("User registered successfully!");
            } else {
                return ResponseEntity.badRequest().body(userService.saveUser(user));
            }
        }catch (Exception e){
           return ResponseEntity.badRequest().body("Error: "+ e.getMessage());
        }
    }
}
