package cn.mrdear.consumer.manager;

import cn.mrdear.client.dto.UserDTO;

/**
 * manager
 * @author Niu Li
 * @since 2017/6/13
 */

public interface UserManager {

  UserDTO findById(Long id);

  UserDTO findByIdBack(Long id);

}
