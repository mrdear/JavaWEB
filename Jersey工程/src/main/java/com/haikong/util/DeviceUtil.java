package com.haikong.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 处理设备消息的工具类
 */
public class DeviceUtil {

    /**
     * 把设备传送来的json解析为json对象
     * @param req 设备请求
     * @return json对象
     */
    public static JSONObject parseJSON(HttpServletRequest req){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(req.getInputStream()));//获取请求的输入流

            StringBuilder builder = new StringBuilder();
            String str = null;
            //读入json串
            while ((str = reader.readLine())!=null){
                builder.append(str);
            }
            //转换为json对象,并返回
            return JSON.parseObject(builder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (reader!=null) reader.close();
                reader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
