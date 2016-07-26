package com.haikong.model;

import java.sql.Timestamp;

/**
 * 保存设备消息的类
 */
public class Device {

    /**
     * device_id : gh_4f7d3885c0a9_9ea2808950a2dce5
     * device_type : gh_4f7d3885c0a9
     * msg_id : 72553035
     * msg_type : notify
     * services : {"operation_status":{"status":1},"power_switch":{"on_off":true}}
     * create_time : 1463367815
     * data : {"vol":2265,"power":3,"ene":0,"t1":"0-0-0-0","t2":"0-0-0-0","t3":"0-0-0-0","t4":"0-0-0-0","ct":"0-0-1"}
     */

    private String device_id;
    private String device_type;
    private long msg_id;
    private String msg_type;
    /**
     * operation_status : {"status":1}
     * power_switch : {"on_off":true}
     */

    private ServicesBean services;
    private Timestamp create_time;
    private String data;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public long getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(long msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public ServicesBean getServices() {
        return services;
    }

    public void setServices(ServicesBean services) {
        this.services = services;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = new Timestamp(create_time*1000);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
