package com.prod.services;


import com.prod.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtService {
    @Value("{jwt.secret}")
    private String mySecretKey="hqwdjnqdkwqdnqwdjnqndwjdnqdu382u9uedj3ij9m21e9wj129ue129ewjs921prm31ne2udg13yd3i1hdu1oj2ond12he3ud1hio12ws";

    private final Key key=Keys.hmacShaKeyFor(mySecretKey.getBytes(StandardCharsets.UTF_8));

    public String generateJWT(User user){

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("email",user.getUsername())
                .claim("role",user.getAuthorities())
                .claim("userId",user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+3600000))
                .signWith(key)
                .compact();
    }

    public Claims parseJwt(String jwt){

        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();


    }

    public Long getIdFromToken(String token){
        Claims payload = parseJwt(token);
        return payload.get("userId",Long.class);
    }

    public String generateRefreshToken(Long userId){
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+3600000*24))
                .claim("userId",userId)
                .signWith(key)
                .compact();
    }

}
