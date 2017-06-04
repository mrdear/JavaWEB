package cn.mrdear.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
@Entity
public class User implements Serializable{

  private static final long serialVersionUID = -2885884654600114856L;

  @Id
  @GeneratedValue
  private Long id;

  private String username;

  private String password;
}
