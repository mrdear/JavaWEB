package cn.medear.springcloud.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Niu Li
 * @since 2017/6/3
 */
@Data
@NoArgsConstructor
@ToString
public class User implements Serializable{

  private static final long serialVersionUID = -2885884654600114856L;

  private Long id;

  private String username;

  private String password;
}
