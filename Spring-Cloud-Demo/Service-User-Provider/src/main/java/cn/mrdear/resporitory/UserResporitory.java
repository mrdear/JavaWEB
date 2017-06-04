package cn.mrdear.resporitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.mrdear.entity.User;

/**
 * @author Niu Li
 * @since 2017/6/3
 */
@Repository
public interface UserResporitory extends JpaRepository<User,Long>{

}
