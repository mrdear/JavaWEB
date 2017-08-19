package cn.mrdear.security.dto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Spring Security中存放的认证用户
 * @author Niu Li
 * @since 2017/6/28
 */
public class TokenUserAuthentication implements Authentication {

  private static final long serialVersionUID = 3730332217518791533L;

  private TokenUserDTO userDTO;

  private Boolean authentication = false;

  public TokenUserAuthentication(TokenUserDTO userDTO, Boolean authentication) {
    this.userDTO = userDTO;
    this.authentication = authentication;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return userDTO.getRoles().stream()
        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  @Override
  public Object getCredentials() {
    return "";
  }

  @Override
  public Object getDetails() {
    return userDTO;
  }

  @Override
  public Object getPrincipal() {
    return userDTO.getUsername();
  }

  @Override
  public boolean isAuthenticated() {
    return authentication;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.authentication = isAuthenticated;
  }

  @Override
  public String getName() {
    return userDTO.getUsername();
  }
}
