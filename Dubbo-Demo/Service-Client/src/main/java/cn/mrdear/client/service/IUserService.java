package cn.mrdear.client.service;

import cn.mrdear.client.dto.UserDTO;

import java.util.Collection;
import java.util.List;

/**
 * 用户服务
 * @author Niu Li
 * @since 2017/6/12
 */
public interface IUserService {

  /**
   * 根据id查找
   */
  UserDTO findById(Long id);

  /**
   * 根据id批量查询
   */
  List<UserDTO> queryByIds(Collection<Long> ids);

  /**
   * 更新用户
   * @return 返回更新后的实体
   */
  UserDTO updateById(UserDTO userDTO);


}
