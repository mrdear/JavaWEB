package com.haikong.resources;

import com.alibaba.fastjson.JSONObject;
import com.haikong.service.DeviceService;
import com.haikong.util.CheckUtil;
import com.haikong.util.DeviceUtil;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author Niu Li
 * @date 2016/7/25
 */
@Path("/device")
@Controller
public class DeviceController {
    @Resource(name = "DeviceService")
    private DeviceService deviceService;

    /**
     * 验证设备URL用的方法
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @GET
    @Path("/sign")
    @Produces(MediaType.TEXT_PLAIN)
    public String entrance1(@QueryParam("signature") String signature, @QueryParam("timestamp") String timestamp,
                            @QueryParam("nonce") String nonce, @QueryParam("echostr") String echostr
                            ){
            if (CheckUtil.checkSignature(signature,timestamp,nonce)) {
                return echostr;
            }
            return null;
    }
    /**
     * 微信消息入口
     * @param req
     * @param resp
     */
    @POST
    @Path("/sign")
    public void entrance2(@Context HttpServletRequest req, @Context HttpServletResponse resp){
        //获取微信传来的四个固定参数
        //获取传送过来的json数据
        JSONObject object = DeviceUtil.parseJSON(req);//获取转换后的对象,下面根据MsgType来找到不同的解决策略
        //判断消息类型
        String msg_type = object.getString("msg_type");
//        System.err.println(object.toJSONString());
        //根据消息类型来处理
        switch (msg_type){
            case "notify": //如果是notify类型
                deviceService.deviceNotify(object);
                break;
            case "get_resp"://消息为get_resp类型
                deviceService.deviceGetResp(object);
                break;
            case "set_resp"://是请求设置设备状态事件
                deviceService.deviceSetResp(object);
                break;
            case "bind"://消息是绑定事件
                deviceService.deviceBind(object);
                break;
            case "unbind"://消息是解绑事件
                deviceService.deviceUnBind(object);
                break;
        }
    }



}
