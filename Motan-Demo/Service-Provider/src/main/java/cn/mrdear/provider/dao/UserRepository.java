package cn.mrdear.provider.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.mrdear.provider.domain.User;

import java.util.Collection;
import java.util.List;

/**
 * JPA repo层
 * @author Niu Li
 * @since 2017/6/12
 */
public interface UserRepository extends JpaRepository<User,Long>{

  User findById(Long id);

  @Query("select p from User p where id in (?1)")
  List<User> queryByIds(Collection<Long> ids);


}
