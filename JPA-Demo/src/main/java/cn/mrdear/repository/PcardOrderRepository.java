package cn.mrdear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.mrdear.entity.PcardOrder;

/**
 * @author Niu Li
 * @date 2016/11/17
 */
public interface PcardOrderRepository extends JpaRepository<PcardOrder,String>
        ,PcardOrderRepositoryCustom,JpaSpecificationExecutor<PcardOrder> {

}
