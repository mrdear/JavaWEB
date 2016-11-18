package cn.mrdear.repository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.mrdear.entity.PcardOrder;
import cn.mrdear.jpa.Filter;
import cn.mrdear.jpa.QueryParams;

/**
 * 公共方法的repository
 * @author Niu Li
 * @date 2016/11/17
 */
@NoRepositoryBean
public class BaseRepository {

    private static Logger logger = LoggerFactory.getLogger(BaseRepository.class);

    /**
     * 分析查询参数,并且合并到sql语句中
     * @param sql JPQL查询语句
     * @param params 查询参数
     * @return 参数对应的value
     */
    @SuppressWarnings("Unchecked")
    protected List<Object> analysisQueryParams(StringBuilder sql, QueryParams<?> params){
        List<String> strList = new ArrayList<>();
        List<Object> valueList = new ArrayList<>();
        int i = 1;
        //分析or条件
        for (Filter filter : params.getOrFilters()) {
            if (filter.getValue() != null){
                strList.add(filter.getProperty()+" " + filter.getOperator().getOperator()+" ?" + (i++));
                valueList.add(filter.getValue());
            }else {
                strList.add(filter.getProperty()+" " + filter.getOperator().getOperator()+" ");
            }
        }
        if (!strList.isEmpty()){
            sql.append(" and ").append("( ").append(StringUtils.join(strList," or ")).append(" )");
        }
        strList.clear();
        //分析and条件
        for (Filter filter : params.getAndFilters()) {
            if (filter.getValue() != null){
                strList.add(filter.getProperty()+" " + filter.getOperator().getOperator()+" ?" + (i++));
                valueList.add(filter.getValue());
            }else {
                strList.add(filter.getProperty()+" " + filter.getOperator().getOperator()+" ");
            }
        }
        sql.append(" and ").append(StringUtils.join(strList," and "));
        //分析排序字段
        if (!params.getOrders().isEmpty()){
            sql.append(" order by ");
            sql.append(StringUtils.join(params.getOrders(),","));
        }
        logger.debug("解析后的sql:"+sql.toString());
        logger.debug("对应的值为:"+valueList);
        return valueList;
    }


	public static void main(String[] args) {
		StringBuilder sql = new StringBuilder("select count(*) from PcardAcctChange p where 1=1 ");
		QueryParams<PcardOrder> queryParams = new QueryParams<>();
		queryParams.or(Filter.eq("username","niuli"));
		queryParams.or(Filter.eq("passwd",1234));
		queryParams.or(Filter.eq("date",new Date()));
		queryParams.and(Filter.isNotNull("nickname"));
		queryParams.orderDESC("date");
		queryParams.orderASC("date3");

        BaseRepository baseRepository = new BaseRepository();
		List<Object> objects = baseRepository.analysisQueryParams(sql,queryParams);
		System.out.println(sql);
		System.out.println(objects);
	}

}
