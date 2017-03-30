package cn.mrdear.model;

/**
 * 测试用户
 * @author Niu Li
 * @since 2017/3/30
 */
public class User {
  private Long id;
  private String username;
  private String password;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
