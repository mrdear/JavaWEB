package cn.mrdear.exception;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常配置
 * @author Niu Li
 * @since 2017/4/19
 */
@ControllerAdvice
public class GlobalException {

  private static Logger logger = LoggerFactory.getLogger(GlobalException.class);
  /**
   * 全局异常处理
   *
   * @param request  请求
   * @param response 返回
   * @param ex       异常
   */
  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.OK)//错误码可以自己定义
  @ResponseBody
  public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
    logger.error("ExceptionHander catch error: ", ex);
    //判断是否为ajax请求
    String xRequested = request.getHeader("x-requested-with");
    if (StringUtils.equalsIgnoreCase(xRequested,"XMLHttpRequest")){
      return handlerAjax(ex);
    }

    //重定向到错误页面,错误页面需要自己定义
    request.setAttribute("errorMessage",ex.getMessage());
    redirect("/error", HttpStatus.NOT_FOUND,request,response);
    return null;
  }

  /**
   * 处理ajax异常
   * @param ex 异常
   * @return json异常信息
   */
  private JSONObject handlerAjax(Exception ex){
    JSONObject resultVO = new JSONObject();
    resultVO.put("status",0);
    resultVO.put("msg",ex.getMessage());
    return resultVO;
  }

  /**
   * 重定向到错误页面
   * @param url 链接
   */
  private void redirect(String url,HttpStatus status,HttpServletRequest request,
      HttpServletResponse response){
    try {
      response.setStatus(status.value());
      request.getRequestDispatcher(url).forward(request,response);
    } catch (IOException | ServletException e) {
      logger.error("redirect fail,e:{}",e);
    }
  }
}
