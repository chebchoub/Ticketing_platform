package com.example.backend.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY="KJBgHfTMdxiZjviAUrH8iFcqdXnnf3dh0T+6y0fAV48bSW6UvRY871P/jkrLEPbw73QRfy23COlTY39l5L2R+g7tcxhl+GNRuzROJAo1W3tVi4JkV1oyDt2DezDV/pmG8EFEMC7J7wGznLBgYNX/CrtmN0L1bMYnA8nrt7QyDkXB7RyX5QHjyRkWvyQBA9YUWVy1AF5sBZfSM1682MaH2J/BQA2GJNDc5kpK4XnY0ydG3ySx+MkYjolHgyoNRCG6je9qsa+u3WsQX/m1GDI7ES03nP6j7dmhLeapxnCz6JZqrUJh7noDHk4tD+v67TLXOSrIFBIqNaJ9woSbHdDYYHJbOJKFeTW4K92z3uZjxWI=";
    public String extarctUsername(String token) {
        return extractClaim(token,Claims::getSubject);

    }
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver)
    {
        final Claims claims =extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails)
    {
        return generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean isTokenValid(String token,UserDetails userDetails)
    {
        final String username=extarctUsername(token);
        return  (username.equals(userDetails.getUsername() )&& !isTokenExpired(token));

    }

    private boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new  Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] ketBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(ketBytes);
    }
}
