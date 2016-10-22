package cn.mrdear.util;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;

import cn.mrdear.Setting;

/**
 * @author Niu Li
 * @date 2016/10/11
 */
public class SystemUtil {


    public static Setting getSetting() throws IOException {
        InputStream in = SystemUtil.class.getClassLoader().getResourceAsStream("setting.json");
        Setting setting = JSON.parseObject(in,null,Setting.class);
        if (setting == null){
            throw new RuntimeException("未配置ss文件路径");
        }
        return setting;
    }
}
