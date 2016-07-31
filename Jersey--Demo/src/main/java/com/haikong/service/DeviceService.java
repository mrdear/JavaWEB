package com.haikong.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haikong.dao.DeviceMapper;
import com.haikong.model.ReqDevice;
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
        ReqDevice device = JSON.parseObject(object.toJSONString(),ReqDevice.class);//解析json类
        device.setAsy_error_code(0);
        if (deviceMapper.updateDeviceDetail(device)){
            logger.info("notify类型设备状态"+object.toString());
        }else {
            logger.error("更新设备出错"+object.toString());
        }
    }

    /**
     * 接收get_resp类型的设备消息,这里还要根据返回码来设置设备的其他状态
     * @param object
     */
    public void deviceGetResp(JSONObject object){
        ReqDevice reqDevice = JSON.parseObject(object.toJSONString(),ReqDevice.class);//解析传来的串
        if (reqDevice.getAsy_error_code()==0){//这里有一种情况是请求的设备关闭
            if (deviceMapper.updateDeviceDetail(reqDevice)){
                logger.info("get_resp类型更新设备状态"+object.toString());
            }else {
                logger.error("get_resp类型更新设备出错"+object.toString());
            }
        }else{//无响应,则设置设备为关闭状态
            if (deviceMapper.updateDeviceOff(reqDevice)){
                logger.info("设备:"+reqDevice.getDevice_id()+"无响应,已设置为关闭");
            }else {
                logger.error("关闭设备出错"+object.toJSONString());
            }
        }
    }

    /**
     * 接收set_resp类型的设备消息,这里还要根据返回码来设置设备的其他状态
     * @param object
     */
    public void deviceSetResp(JSONObject object){
        ReqDevice reqDevice = JSON.parseObject(object.toJSONString(),ReqDevice.class);//解析传来的串
        int asy_error_code = reqDevice.getAsy_error_code();
            if (asy_error_code == 0){
                deviceMapper.updateDeviceDetail(reqDevice);
                logger.info("set_resp类型更新设备状态" + object.toString());
            }else {//证明未联网
                deviceMapper.updateDeviceOff(reqDevice);
                logger.error("设备未联网,已记录到数据库"+object.toString());
            }
    }

    /**
     * 处理用户绑定事件
     * @param object
     */
    public void deviceBind(JSONObject object){
        ReqDevice reqDevice = new ReqDevice();
        reqDevice.setDevice_id(object.getString("device_id"));
        reqDevice.setDevice_type(object.getString("device_type"));
        reqDevice.setUser(object.getString("open_id"));
        if (deviceMapper.insertDevice(reqDevice)){
            logger.info("用户:"+reqDevice.getUser()+"绑定设备:"+reqDevice.getDevice_id()+"成功");
        }else {
            logger.error("用户:"+reqDevice.getUser()+"绑定设备:"+reqDevice.getDevice_id()+"失败");
        }
    }
    /**
     * 处理用户解绑事件
     * @param object
     */
    public void deviceUnBind(JSONObject object){
        ReqDevice reqDevice = new ReqDevice();
        reqDevice.setDevice_id(object.getString("device_id"));
        reqDevice.setUser(object.getString("open_id"));
        if (deviceMapper.deleteUserDevice(reqDevice)){
            logger.info("用户:"+reqDevice.getUser()+"解除绑定设备:"+reqDevice.getDevice_id()+"成功");
        }else {
            logger.error("用户:"+reqDevice.getUser()+"解除绑定设备:"+reqDevice.getDevice_id()+"失败");
        }
    }

    private static Logger logger  = Logger.getLogger(DeviceService.class);

}
