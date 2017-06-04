package cn.medear.springcloud.restclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.medear.springcloud.entity.User;
import cn.medear.springcloud.restclient.failback.UserClientFailBack;

/**
 * @author Niu Li
 * @since 2017/6/4
 */
@FeignClient(name = "service-user-provider",fallbackFactory = UserClientFailBack.class)
public interface UserClient {

  @RequestMapping(value = "/{id}",method = RequestMethod.GET)
  User findById(@PathVariable("id") Long id);

}
