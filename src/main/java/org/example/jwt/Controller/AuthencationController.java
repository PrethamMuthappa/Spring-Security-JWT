package org.example.jwt.Controller;


import org.example.jwt.Model.User;
import org.example.jwt.Service.AuthService;
import org.example.jwt.Service.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthencationController {


    private final AuthService authService;

    public AuthencationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User requset){
        return ResponseEntity.ok(authService.register(requset));

    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User requset){
        return ResponseEntity.ok(authService.authenciate(requset));
    }
}
