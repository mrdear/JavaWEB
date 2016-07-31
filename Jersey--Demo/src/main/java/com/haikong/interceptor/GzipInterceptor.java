package com.haikong.interceptor;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Gzip压缩输出
 * 拦截器意图操纵的实体，通过操纵实体的输入/输出数据流。比如你需要编码的客户端请求的实体主体
 *
 * @author Niu Li
 * @date 2016/7/27
 */
public class GzipInterceptor implements WriterInterceptor {
    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {

        MultivaluedMap<String, Object> headers = context.getHeaders();
        headers.add("Content-Encoding", "gzip");
        String ContentType = context.getMediaType().toString();
        headers.add("Content-Type",ContentType+";charset=utf-8");//解决乱码问题
        final OutputStream outputStream = context.getOutputStream();
        context.setOutputStream(new GZIPOutputStream(outputStream));
        context.proceed();
        System.out.println("GZIP拦截器压缩");
    }
}
