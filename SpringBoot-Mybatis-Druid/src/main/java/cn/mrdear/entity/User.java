package cn.mrdear.entity;

import java.io.Serializable;

/**
 * 用户实体类
 * @author Niu Li
 * @date 2016/8/12
 */
public class User implements Serializable{
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
