package com.haikong.util;

import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * 具体检查登录验证的工具类
 */
public class CheckUtil {
    public static boolean checkSignature(String signature,String timestamp,String nonce){
        Assert.notNull(signature,"签名值不能为null");
        Assert.notNull(timestamp,"时间戳不能为null");
        Assert.notNull(nonce,"nonce不为null");

        String[] strArr = {Constants.TOKEN,timestamp,nonce};
        Arrays.sort(strArr);
        StringBuilder builder = new StringBuilder();
        for (String aStrArr : strArr) {
            builder.append(aStrArr);
        }
        System.out.println("验证结果:"+DecriptUtil.SHA1(builder.toString()).equals(signature));

        return DecriptUtil.SHA1(builder.toString()).equals(signature);
    }

    private CheckUtil(){}
}
