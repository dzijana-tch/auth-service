package com.charniuk.authservice.security.impl;

import com.charniuk.authservice.model.User;
import com.charniuk.authservice.security.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

  @Value("${token.signing.key}")
  private String jwtSigningKey;

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, c -> c.get("username", String.class));
  }

  /**
   * Извлечение данных из токена
   *
   * @param token           токен
   * @param claimsResolvers функция извлечения данных
   * @param <T>             тип данных
   * @return данные
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  /**
   * Извлечение всех данных из токена
   *
   * @param token токен
   * @return данные
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * Получение ключа для подписи токена
   *
   * @return ключ
   */
  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public String generateToken(User user) {
    log.info("Генерация токена для пользователя {}", user.getUserId());
    Map<String, Object> claims = new HashMap<>();
    claims.put("username", user.getUsername());
    claims.put("role", user.getRole());
    return generateToken(claims, user);
  }

  /**
   * Генерация токена
   *
   * @param extraClaims дополнительные данные
   * @param user        данные пользователя
   * @return токен
   */
  private String generateToken(Map<String, Object> extraClaims, User user) {
    return Jwts.builder().claims(extraClaims).subject(user.getUserId().toString())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
        .signWith(getSigningKey(), SIG.HS256).compact();
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    log.info("Проверка на валидность токена {}", token);
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Проверка токена на просроченность
   *
   * @param token токен
   * @return true, если токен просрочен
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Извлечение даты истечения токена
   *
   * @param token токен
   * @return дата истечения
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
}
