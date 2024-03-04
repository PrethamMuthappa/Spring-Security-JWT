package org.example.jwt.Service;

public class AuthenticationResponse {
    protected String token;

    public AuthenticationResponse(String token){
        this.token=token;
    }

    String getToken(){
        return token;
    }


}
