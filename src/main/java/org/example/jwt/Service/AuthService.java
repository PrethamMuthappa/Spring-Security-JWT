package org.example.jwt.Service;


import org.example.jwt.Model.User;
import org.example.jwt.repository.Userrepos;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    protected final Userrepos userrepos;

    private PasswordEncoder passwordEncoder;

    protected final JwtService jwtService;

    protected AuthenticationManager authenticationManager;



    public AuthService(Userrepos userrepos, JwtService jwtService) {
        this.userrepos = userrepos;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse register(User request){
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(user.getRole());

        user=userrepos.save(user);

        String token=jwtService.tokengeneration(user);

        return new AuthenticationResponse(token);


    }

    public AuthenticationResponse authenciate(User request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );

     User user= userrepos.findUserByUsername(request.getUsername()).orElseThrow();

        String token=jwtService.tokengeneration(user);

        return new AuthenticationResponse(token);
    }


}
