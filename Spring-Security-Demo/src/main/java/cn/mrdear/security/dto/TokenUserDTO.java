package cn.mrdear.security.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Niu Li
 * @since 2017/6/28
 */
@Data
public class TokenUserDTO {

  private Long id;

  private String username;

  private String email;

  private String avatar;

  private List<String> roles;
}
