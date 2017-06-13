package cn.mrdear.provider.convert;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import cn.mrdear.client.dto.UserDTO;
import cn.mrdear.provider.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Niu Li
 * @since 2017/6/12
 */
public class UserConvert {

  public static UserDTO toDTO(User user) {
    UserDTO userDTO = new UserDTO();
    BeanUtils.copyProperties(user,userDTO);
    return userDTO;
  }


  public static List<UserDTO> toDTOS(List<User> users) {
    if (CollectionUtils.isEmpty(users)) {
      return new ArrayList<>(1);
    }
    List<UserDTO> results = new ArrayList<>();
    return users.stream().map(UserConvert::toDTO)
        .collect(Collectors.toList());
  }


  public static User toDO(UserDTO userDTO) {
    User user = new User();
    BeanUtils.copyProperties(userDTO,user);
    return user;
  }

}
