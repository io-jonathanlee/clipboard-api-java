package io.jonathanlee.clipboardapijava.service.users.impl;

import io.jonathanlee.clipboardapijava.service.users.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

  private static final String SECRET_KEY = "2A472D4B6150645367566B58703273357638792F423F4528482B4D6251655468576D5A7133743677397A24432646294A404E635266556A586E32723575377821";

  private static final long MILLISECONDS_AS_SECOND = 1000L;

  private static final long EXPIRY_TIME_MINUTES = 30L;

  @Override
  public String extractUsername(String jwt) {
    return this.extractClaim(jwt, Claims::getSubject);
  }

  @Override
  public String generateJwt(final UserDetails userDetails) {
    return generateJwt(new HashMap<>(), userDetails);
  }

  @Override
  public boolean isJwtValid(final String jwt, final UserDetails userDetails) {
    final String username = this.extractUsername(jwt);
    return (username.equals(userDetails.getUsername())) && !this.isTokenExpired(jwt);
  }

  private boolean isTokenExpired(final String jwt) {
    return extractExpiration(jwt).before(new Date());
  }

  private Date extractExpiration(final String jwt) {
    return extractClaim(jwt, Claims::getExpiration);
  }

  public String generateJwt(final Map<String, Object> addedClaims, final UserDetails userDetails) {
    return Jwts
        .builder()
        .setClaims(addedClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(
            System.currentTimeMillis() + (MILLISECONDS_AS_SECOND * EXPIRY_TIME_MINUTES * 60)))
        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(jwt);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(final String jwt) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(jwt)
        .getBody();
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
  }

}
