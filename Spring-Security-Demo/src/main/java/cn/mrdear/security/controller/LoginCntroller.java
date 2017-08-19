package cn.mrdear.security.controller;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.mrdear.security.dto.TokenUserDTO;
import cn.mrdear.security.util.JwtTokenUtil;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Niu Li
 * @since 2017/6/19
 */
@RestController
public class LoginCntroller {

  @Resource
  private JwtTokenUtil jwtTokenUtil;

  /**
   * 该链接获取token
   */
  @GetMapping("/login")
  public Map login(String username, String password) {
    Map<String, Object> map = new HashMap<>();
    if (!StringUtils.equals(username, "demo") || !StringUtils.equals(password, "demo")) {
      map.put("status", 4);
      map.put("msg", "登录失败,用户名密码错误");
      return map;
    }
    TokenUserDTO userDTO = new TokenUserDTO();
    userDTO.setUsername(username);
    userDTO.setRoles(Lists.newArrayList("ROLE_ADMIN"));
    userDTO.setId(1L);
    userDTO.setEmail("1015315668@qq.com");
    userDTO.setAvatar("ahahhahahhaha");
    map.put("status", 1);
    map.put("msg", "Success");
    map.put("token", jwtTokenUtil.create(userDTO));
    return map;
  }

  /**
   * 该链接尝试获取登录用户,返回该认证用户的信息,请求该链接需要在header中放入x-authorization: token
   */
  @GetMapping("/detail")
  public TokenUserDTO userDetail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.isNull(authentication)) {
      return null;
    }
    return (TokenUserDTO) authentication.getDetails();
  }

}
