package cn.mrdear.provider.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

/**
 * 用户实体类,对应数据库
 * @author Niu Li
 * @since 2017/6/12
 */
@Data
@Entity
@Table(name = "user")
public class User implements Serializable{

  private static final long serialVersionUID = 1793488098966504793L;
  @Id
  @GeneratedValue
  private Long id;

  private String username;

  private String password;

  private Integer age;

  private String nickname;

  private String mail;

  private String memo;

}
