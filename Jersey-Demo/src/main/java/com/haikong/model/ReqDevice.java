package com.haikong.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 主动请求时,返回值使用这个模型
 */
@XmlRootElement
public class ReqDevice extends Device {
    private int asy_error_code;
    private String asy_error_msg;

    private String user;//用户的openid

    public int getAsy_error_code() {
        return asy_error_code;
    }

    public void setAsy_error_code(int asy_error_code) {
        this.asy_error_code = asy_error_code;
    }

    public String getAsy_error_msg() {
        return asy_error_msg;
    }

    public void setAsy_error_msg(String asy_error_msg) {
        this.asy_error_msg = asy_error_msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
