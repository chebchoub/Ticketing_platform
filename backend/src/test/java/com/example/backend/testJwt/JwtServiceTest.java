package com.example.backend.testJwt;

import com.example.backend.Config.JwtService;
import com.example.backend.Entity.Role;
import com.example.backend.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }




    private String createExpiredToken() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return io.jsonwebtoken.Jwts.builder()
                .setSubject("username")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(key)
                .compact();
    }

    /*@Test
    public void testExtractUsername_ValidToken() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJheW1lbkBnbWFpbC5jb20iLCJpYXQiOjE3MDc5MDMzMjMsImV4cCI6MTcwNzkwNDc2M30.xvc9hkBAkeavA_zmRK9Ox2gx4aRWD758Wz79F76DbKQ";

        // When
        String username = jwtService.extarctUsername(token);

        // Then
        assertEquals("aymen@gmail.com", username);
    }

    @Test
    public void testExtractClaim_ValidToken() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJheW1lbkBnbWFpbC5jb20iLCJpYXQiOjE3MDc5MDMzMjMsImV4cCI6MTcwNzkwNDc2M30.xvc9hkBAkeavA_zmRK9Ox2gx4aRWD758Wz79F76DbKQ";

        // When
        String subject = jwtService.extractClaim(token, Claims::getSubject);

        // Then
        assertEquals("aymen@gmail.com", subject);
        System.out.println(subject);
    }*/

    @Test
    public void testGenerateToken_UserDetails() {
        // Given
        UserDetails userDetails =new User("aymen","chabchoub","exemple@gmail.com","aze123", Role.USER);

        // When
        String token = jwtService.generateToken(userDetails);

        // Then
        assertNotNull(token);
        assertTrue(token.length() > 0);
        System.out.println(token);
    }

    @Test
    public void testGenerateToken_ExtraClaimsAndUserDetails() {
        // Given
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("key", "value");
        UserDetails userDetails =new User("aymen","chabchoub","exemple@gmail.com","aze123", Role.USER);

        // When
        String token = jwtService.generateToken(extraClaims, userDetails);

        // Then
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    /*@Test
    public void testIsTokenValid_ValidToken() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJheW1lbkBnbWFpbC5jb20iLCJpYXQiOjE3MDc5MDMzMjMsImV4cCI6MTcwNzkwNDc2M30.xvc9hkBAkeavA_zmRK9Ox2gx4aRWD758Wz79F76DbKQ";
        UserDetails userDetails =new User("aymen","chabchoub","aymen@gmail.com","aze123", Role.USER);

        // When
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Then
        assertTrue(isValid);
    }*/

    @Test
    public void testIsTokenExpired_NotExpiredToken() {
        // Given
        JwtService jwtService = new JwtService();
        // Suppose we have a token that expires after 1 hour from now
        Date expirationDate = new Date(System.currentTimeMillis() + +1000*60*24);
        String notExpiredToken = io.jsonwebtoken.Jwts.builder()
                .setExpiration(expirationDate)
                .signWith(jwtService.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        // When
        boolean isExpired = jwtService.isTokenExpired(notExpiredToken);

        // Then
        assertFalse(isExpired);
        System.out.println(isExpired);
    }
   /* @Test
    public void testIsTokenValid_InvalidToken() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJheW1lbkBnbWFpbC5jb20iLCJpYXQiOjE3MDc5MDMzMjMsImV4cCI6MTcwNzkwNDc2M30.xvc9hkBAkeavA_zmRK9Ox2gx4aRWD758Wz79F76DbKQ";
        UserDetails userDetails =new User("aymen","chabchoub","gj@gmail.com","aze123", Role.USER);

        // When
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Then
        assertFalse(isValid);
    }
*/
   /* @Test
    public void testExtractAllClaims() {
        // Given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJheW1lbkBnbWFpbC5jb20iLCJpYXQiOjE3MDc5MDMzMjMsImV4cCI6MTcwNzkwNDc2M30.xvc9hkBAkeavA_zmRK9Ox2gx4aRWD758Wz79F76DbKQ";

        // When
        Claims claims = jwtService.extractAllClaims(token);

        // Then
        assertNotNull(claims);
        assertEquals("aymen@gmail.com", claims.getSubject());
    }
*/
    @Test
    public void testGetSignInKey() {
        // When
        Key key = jwtService.getSignInKey();

        // Then
        assertNotNull(key);
    }

}
