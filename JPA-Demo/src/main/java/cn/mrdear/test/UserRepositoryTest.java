package cn.mrdear.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import cn.mrdear.entity.PcardOrder;
import cn.mrdear.jpa.Filter;
import cn.mrdear.jpa.QueryParams;
import cn.mrdear.repository.PcardOrderRepository;

/**
 * @author Niu Li
 * @date 2016/11/1
 */
public class UserRepositoryTest {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        PcardOrderRepository pcardOrderRepository = (PcardOrderRepository) applicationContext.getBean("pcardOrderRepository");
        QueryParams<PcardOrder> queryParams = new QueryParams<>();
        //使用Specification条件查询,使用JPQL字段查询
        queryParams
                .and(Filter.eq("acctId","0014779934917371041"),Filter.ne("orderAmt",0L),
                        Filter.eq("orderRespCd","00"))
                .or(Filter.eq("orderTypeId","A003"),Filter.eq("orderTypeId","A007"),
                        Filter.eq("orderTypeId","A021"),Filter.eq("orderTypeId","A018"))
                .orderDESC("createTime");

        Page<PcardOrder> JPQLlist = pcardOrderRepository.findAll(queryParams,new PageRequest(0,2));

        //使用原生sql查询,注意这里使用原生sql字段,并非JPQL字段,
        //本质是根据BaseRepository.analysisQueryParams 来拼接条件,可以根据自己的需求更改
        queryParams.clearAll()
                .and(Filter.eq("acct_id","0014779934917371041"),Filter.ne("order_amt",0L),
                        Filter.eq("order_resp_cd","00"))
                .or(Filter.eq("order_type_id","A003"),Filter.eq("order_type_id","A007"),
                        Filter.eq("order_type_id","A021"),Filter.eq("order_type_id","A018"))
                .orderDESC("create_time");
        List nativeSqlList = pcardOrderRepository.findByQueryParam(queryParams,new PageRequest(0,2));

        System.out.println(nativeSqlList);
    }
}
