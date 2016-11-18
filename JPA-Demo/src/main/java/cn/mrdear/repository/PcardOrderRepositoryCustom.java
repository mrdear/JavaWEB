package cn.mrdear.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

import cn.mrdear.jpa.QueryParams;

/**
 * @author Niu Li
 * @date 2016/11/17
 */
public interface PcardOrderRepositoryCustom {

    List findByQueryParam(QueryParams queryParams, Pageable pageable);
}
