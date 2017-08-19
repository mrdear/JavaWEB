package cn.mrdear.security.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import cn.mrdear.security.dto.TokenUserAuthentication;
import cn.mrdear.security.dto.TokenUserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * jwt token
 *
 * @author Niu Li
 * @since 2017/6/28
 */
public class JwtTokenUtil {
  /**
   * token过期时间
   */
  private static final long VALIDITY_TIME_MS = 24 * 60 * 60 * 1000; // 24 hours  validity
  /**
   * header中标识
   */
  private static final String AUTH_HEADER_NAME = "x-authorization";

  /**
   * 签名密钥
   */
  @Value("${jwt.token.secret}")
  private String secret;

  /**
   * 验签
   */
  public Optional<Authentication> verifyToken(HttpServletRequest request) {
    final String token = request.getHeader(AUTH_HEADER_NAME);
    if (token != null && !token.isEmpty()){
      final TokenUserDTO user = parse(token.trim());
      if (user != null) {
        return Optional.of(new TokenUserAuthentication(user, true));
      }
    }
    return Optional.empty();
  }


  /**
   * 从用户中创建一个jwt Token
   *
   * @param userDTO 用户
   * @return token
   */
  public String create(TokenUserDTO userDTO) {
    return Jwts.builder()
        .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
        .setSubject(userDTO.getUsername())
        .claim("id", userDTO.getId())
        .claim("avatar", userDTO.getAvatar())
        .claim("email", userDTO.getEmail())
        .claim("roles", userDTO.getRoles())
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  /**
   * 从token中取出用户
   */
  public TokenUserDTO parse(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
    TokenUserDTO userDTO = new TokenUserDTO();
    userDTO.setId(NumberUtils.toLong(claims.getId()));
    userDTO.setAvatar(claims.get("avatar",String.class));
    userDTO.setUsername(claims.get("username",String.class));
    userDTO.setEmail(claims.get("email",String.class));
    userDTO.setRoles((List<String>) claims.get("roles"));
    return userDTO;
  }

}
