package cn.mrdear.client.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户DTO,用于Service层传输
 * @author Niu Li
 * @since 2017/6/12
 */
@Data
public class UserDTO implements Serializable{

  private static final long serialVersionUID = 4086492518942464226L;

  private Long id;

  private String username;

  private String password;

  private Integer age;

  private String nickname;

  private String mail;

  private String memo;

}
