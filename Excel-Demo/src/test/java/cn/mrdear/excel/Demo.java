package cn.mrdear.excel;

import cn.mrdear.excel.util.FieldName;

/**
 * @author Niu Li
 * @since 2017/2/23
 */
public class Demo {

    public Demo(String username, String password) {
        this.userName = username;
        this.passWord = password;
    }
    public Demo() {
    }
    //fildName字段需要和header对应
    @FieldName(value = "username")
    private String userName;
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "Demo{" +
            "userName='" + userName + '\'' +
            ", passWord='" + passWord + '\'' +
            '}';
    }
}
