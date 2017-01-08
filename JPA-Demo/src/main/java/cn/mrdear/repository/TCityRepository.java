package cn.mrdear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import cn.mrdear.entity.TCity;

/**
 * @author Niu Li
 * @date 2017/1/7
 */
public interface TCityRepository extends JpaRepository<TCity,Long>,QueryDslPredicateExecutor<TCity>, cn.mrdear.repository.custom.TCityRepositoryCustom {

}
