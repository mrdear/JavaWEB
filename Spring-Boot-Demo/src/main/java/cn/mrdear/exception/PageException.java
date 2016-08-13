package cn.mrdear.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常类
 * @author Niu Li
 * @date 2016/8/10
 */
@ControllerAdvice//配置文件没了,所以使用注解让spring管理
public class PageException implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(PageException.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest Request, HttpServletResponse Response, Object o, Exception ex) {
        logger.warn("==============异常开始=============");
        logger.error("异常:",ex);
        logger.warn("==============异常结束=============");
        ModelAndView mv = new ModelAndView();
        mv.addObject("error", ex.getMessage());
        mv.setViewName("error");
        return mv;
    }
}
