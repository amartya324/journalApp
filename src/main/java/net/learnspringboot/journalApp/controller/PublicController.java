package net.learnspringboot.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.learnspringboot.journalApp.service.CustomUserDetailsServiceImpl;
import net.learnspringboot.journalApp.service.UserService;
import net.learnspringboot.journalApp.entity.User;
import net.learnspringboot.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "okay";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ",e);
            return new ResponseEntity<>("Incorrect username or password ",HttpStatus.BAD_REQUEST);
        }

    }
}
