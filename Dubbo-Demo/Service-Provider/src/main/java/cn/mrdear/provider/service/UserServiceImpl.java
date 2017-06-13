package cn.mrdear.provider.service;

import com.alibaba.dubbo.config.annotation.Service;

import cn.mrdear.client.dto.UserDTO;
import cn.mrdear.client.service.IUserService;
import cn.mrdear.provider.convert.UserConvert;
import cn.mrdear.provider.dao.UserRepository;
import cn.mrdear.provider.domain.User;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.List;

/**
 * 用户服务的具体实现,作为服务提供方提供出去
 * @author Niu Li
 * @since 2017/6/12
 */
@Service(version = "1.0.0")
public class UserServiceImpl implements IUserService {

  @Resource
  private UserRepository userRepository;

  @Override
  public UserDTO findById(Long id) {
    User user = userRepository.findById(id);
    return UserConvert.toDTO(user);
  }

  @Override
  public List<UserDTO> queryByIds(Collection<Long> ids) {
    List<User> users = userRepository.queryByIds(ids);
    return UserConvert.toDTOS(users);
  }

  @Override
  public UserDTO updateById(UserDTO userDTO) {
    User user = UserConvert.toDO(userDTO);
    userRepository.save(user);
    return this.findById(userDTO.getId());
  }

  @Override
  public Boolean deleteById(Long id) {
    userRepository.delete(id);
    return true;
  }
}
