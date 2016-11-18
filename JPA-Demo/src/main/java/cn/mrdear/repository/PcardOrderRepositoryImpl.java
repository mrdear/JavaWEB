package cn.mrdear.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cn.mrdear.jpa.QueryParams;

/**
 * @author Niu Li
 * @date 2016/11/18
 */
@NoRepositoryBean
public class PcardOrderRepositoryImpl extends BaseRepository implements PcardOrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List findByQueryParam(QueryParams queryParams, Pageable pageable) {
        StringBuilder sql = new StringBuilder("select * from tbl_pcard_order where 1=1 ");
        List values = analysisQueryParams(sql,queryParams);
        Query query = entityManager.createNativeQuery(sql.toString());
        for (int i = 0; i < values.size(); i++) {
            query.setParameter(i+1,values.get(i));
        }
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }
}
