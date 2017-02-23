package com.haikong.service;

import com.alibaba.fastjson.JSONObject;
import com.haikong.dao.DeviceMapper;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 处理设备传送来数据的service
 */
@Service("DeviceService")
public class DeviceService {

    @Resource(name = "DeviceMapper")
    private DeviceMapper deviceMapper;

    /**
     * 接收notify类型的设备消息
     * 如果设备未联网,则设置为关闭状态
     * @param object
     */
    public void deviceNotify(JSONObject object){
    }

    /**
     * 接收get_resp类型的设备消息,这里还要根据返回码来设置设备的其他状态
     * @param object
     */
    public void deviceGetResp(JSONObject object){
    }

    /**
     * 接收set_resp类型的设备消息,这里还要根据返回码来设置设备的其他状态
     * @param object
     */
    public void deviceSetResp(JSONObject object){
    }

    /**
     * 处理用户绑定事件
     * @param object
     */
    public void deviceBind(JSONObject object){
    }
    /**
     * 处理用户解绑事件
     * @param object
     */
    public void deviceUnBind(JSONObject object){

    }

    private static Logger logger  = Logger.getLogger(DeviceService.class);

}
