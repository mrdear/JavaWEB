package cn.mrdear.client.service;

import cn.mrdear.client.dto.UserDTO;

import java.util.Collection;
import java.util.List;

/**
 * 用户服务,一般都会在返回层再包裹一层,这里简而化之
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

  /**
   * 根据id删除用户
   */
  Boolean deleteById(Long id);


}
