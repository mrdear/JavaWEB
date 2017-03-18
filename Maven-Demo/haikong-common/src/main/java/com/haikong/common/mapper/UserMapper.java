package com.haikong.common.mapper;

import com.haikong.common.entity.User;

/**
 * @author Niu Li
 * @date 2016/9/6
 */
public interface UserMapper {

    public User find(String openId);

}
