package ru.bsluckyanchuk.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
@Component

public class JwtProvider {
    private static final String AUTH = "Authorization";
    private static final String BEARER = "Bearer_";

    //@Value("${jwt.token.secret}")
    private static String secret = "I could drink the sea / I could be different / Forever young / Forever drunk";

    // public JwtProvider(UserDetailsService userDetailsService) {
    //    this.userDetailsService = userDetailsService;
    //}


    @PostConstruct
    private void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }


    public  String createToken(String email) {
        Date issuedAt = new Date();
        //@Value("${jwt.token.expired}")
        long expiredIn = 134143141L;
        Date expiration = new Date(issuedAt.getTime() + expiredIn);
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }
}
