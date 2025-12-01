package at.kacharino.workouttrackerstronger.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.expiration}")
    private Long expirationValueInMS;

    @Value("${jwt.secret}")
    private String mySecretKey;

    public String generateToken(String email) {
        var secretKey = Keys.hmacShaKeyFor(mySecretKey.getBytes());

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expirationValueInMS)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        var emailFromToken = extractEmail(token);

        return emailFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        var secretKey = Keys.hmacShaKeyFor(this.mySecretKey.getBytes());

        var parser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        Jws<Claims> parsedToken = parser.parseClaimsJws(token);
        return parsedToken.getBody();
    }


}
