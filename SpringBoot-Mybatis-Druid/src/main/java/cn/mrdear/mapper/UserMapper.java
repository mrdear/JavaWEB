package cn.mrdear.mapper;

import cn.mrdear.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * UserMapper.xml代理
 * @author Niu Li
 * @date 2016/8/13
 */
@Mapper
public interface UserMapper {
    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User findById(int id);
}
