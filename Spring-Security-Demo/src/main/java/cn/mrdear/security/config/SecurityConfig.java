package cn.mrdear.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;

import cn.mrdear.security.util.JwtTokenUtil;

import javax.annotation.Resource;

/**
 * Spring Security配置类
 * @author Niu Li
 * @since 2017/6/16
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource
  private JwtTokenUtil jwtTokenUtil;

  /**
   * 在此配置不过滤的请求
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    //每一个请求对应一个空的filter链,这里一般不要配置过多,
    // 因为查找处是一个for循环,过多就导致每个请求都需要循环一遍直到找到
    web.ignoring().antMatchers("/","/login","/favicon.ico");
  }

  /**
   * 在此配置过滤链
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        //角色定义,Spring Security会在其前面自动加上ROLE_,因此存储权限的时候也要加上ROLE_ADMIN
        .antMatchers("/detail").access("hasRole('ADMIN')")
        .anyRequest().permitAll().and()
        //异常处理,可以再此使用entrypoint来定义错误输出
        .exceptionHandling().and()
        //不需要session来控制,所以这里可以去掉
        .securityContext().securityContextRepository(new NullSecurityContextRepository()).and()
        //开启匿名访问
        .anonymous().and()
        //退出登录自己来控制
        .logout().disable()
        //因为没用到cookies,所以关闭cookies
        .csrf().disable()
        //允许跨域
        .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
        //验证token
        .addFilterBefore(new VerifyTokenFilter(jwtTokenUtil),
            UsernamePasswordAuthenticationFilter.class);
  }
}
