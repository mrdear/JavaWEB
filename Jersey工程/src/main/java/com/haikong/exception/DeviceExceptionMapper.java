package com.haikong.exception;

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
        Response.ResponseBuilder ResponseBuilder = null;

        if (e instanceof DeviceException){

            //截取自定义类型
            DeviceException exp = (DeviceException) e;
            ErrorEntity entity = new ErrorEntity(exp.getCode(),exp.getMessage());
            ResponseBuilder = Response.ok(entity, MediaType.APPLICATION_JSON);
        }else {
            ErrorEntity entity = new ErrorEntity(ErrorCode.OTHER_ERR.getCode(),e.getMessage());
            ResponseBuilder = Response.ok(entity, MediaType.APPLICATION_JSON);
        }
        return ResponseBuilder.build();
    }
}
