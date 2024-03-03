package org.example.jwt.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.jwt.Model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final String secretekey = "d80e5ef6f6f6fd50084ab7a1140cbfe4e3a01e9c9efa79dcec9670a994d4ff8f";

    public String extractusername(String token) {
        return extractclaim(token, Claims::getSubject);
    }

    public boolean isvalid(String token, UserDetails user) {
        String username = extractusername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationtime(token).before(new Date());
    }

    private Date extractExpirationtime(String token) {
        return extractclaim(token, Claims::getExpiration);
    }

    public <T> T extractclaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractallclaims(token);
        return resolver.apply(claims);
    }

    private Claims extractallclaims(String token) {
        return Jwts.parser()
                .verifyWith(getsignkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getsignkey() {
        byte[] keybyte = Decoders.BASE64URL.decode(secretekey);
        return Keys
                .hmacShaKeyFor(keybyte);
    }

    public String tokengeneration(User user) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getsignkey())
                .compact();

        return token;
    }

}
