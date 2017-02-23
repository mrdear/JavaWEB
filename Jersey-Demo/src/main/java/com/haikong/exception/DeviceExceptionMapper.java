package com.haikong.exception;

import com.haikong.ResultVO;

import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Niu Li
 * @date 2016/7/26
 */
@Provider
public class DeviceExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        logger.error("错误信息:",e);
        Response.ResponseBuilder ResponseBuilder = null;
        if (e instanceof NumberFormatException){
            ErrorEntity entity = new ErrorEntity(ResultVO.OTHER_ERR.getCode(),ResultVO.OTHER_ERR.getMessage());
            ResponseBuilder = Response.ok(entity, MediaType.APPLICATION_JSON);
        }else {
            ErrorEntity entity = new ErrorEntity(ResultVO.OTHER_ERR.getCode(),e.getMessage());
            ResponseBuilder = Response.ok(entity, MediaType.APPLICATION_JSON);
        }
        System.out.println("执行自定义异常");
        return ResponseBuilder.build();
    }

    private static Logger logger = Logger.getLogger(DeviceExceptionMapper.class);
}
