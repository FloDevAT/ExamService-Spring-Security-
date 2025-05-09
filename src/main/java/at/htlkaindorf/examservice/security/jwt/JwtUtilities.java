package at.htlkaindorf.examservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Class which we are being provided with in the exam, so no further details / documentation
 */
@Component
public class JwtUtilities
{
  @Value("${token.signin.secret}")
  private String SECRET;

  private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

  public String extractUsername(String jwt)
  {
    Claims claims = getClaims(jwt);
    return claims.getSubject();
  }

  public boolean isTokenValid(String jwt)
  {
    Claims claims = getClaims(jwt);
    return claims.getExpiration().after(Date.from(Instant.now()));
  }

  private Claims getClaims(String jwt)
  {
    return Jwts.parser()
            .verifyWith(generateKey())
            .build()
            .parseSignedClaims(jwt)
            .getPayload();
  }

  public String generateToken(String username)
  {
    Map<String, String> claims = new HashMap<>();
    claims.put("iss","https://www.htl-kaindorf.ac.at");
    return Jwts.builder()
            .claims(claims)
            .subject(username)
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
            .signWith(generateKey()) // HS384
            // .signWith(generateKey(), SignatureAlgorithm.HS512)
            .compact();
  }

  private SecretKey generateKey()
  {
    byte[] decodedKey = Base64.getDecoder().decode(SECRET);
    return Keys.hmacShaKeyFor(decodedKey);
  }
}
