package cn.mrdear.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Niu Li
 * @since 2017/4/19
 */
@Controller
public class ErrorController {

  @GetMapping("/error")
  public String error(){
    return "error";
  }
}
