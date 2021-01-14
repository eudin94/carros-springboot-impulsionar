package com.carros.api.users;

import com.carros.api.infra.security.jwt.JwtLoginInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/info")
    public UserDTO userInfo(@AuthenticationPrincipal User user) {
        //UserDetails userDetails = JwtUtil.getUserDetails();
        return UserDTO.create(user);
    }

    @GetMapping("/{login}")
    public ResponseEntity get(@PathVariable("login") String login) {
        UserDTO user = service.getUserByLogin(login);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody JwtLoginInput login) {
        String username = login.getUsername();
        String password = login.getPassword();

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new BadCredentialsException("Invalid username/password.");
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

        return ResponseEntity.ok(login.toString());
    }
}
