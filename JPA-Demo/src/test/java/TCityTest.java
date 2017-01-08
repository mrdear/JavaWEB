import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import cn.mrdear.entity.QTCity;
import cn.mrdear.entity.QTHotel;
import cn.mrdear.entity.TCity;
import cn.mrdear.repository.TCityRepository;

/**
 * @author Niu Li
 * @date 2017/1/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class TCityTest {
    @Autowired
    private TCityRepository tCityRepository;
    //动态条件
    QTCity qtCity = QTCity.tCity;
    QTHotel qtHotel = QTHotel.tHotel;

    //单表操作系列 start
    /**
     * 非动态查询建议使用Query注解
     */
    @Test
    public void findDynamic(){
        Predicate predicate = qtCity.id.longValue().lt(3)
                                       .and(qtCity.name.like("shanghai"));
        //分页排序
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        PageRequest pageRequest = new PageRequest(0,10,sort);
        //查找结果
        Page<TCity> tCityPage = tCityRepository.findAll(predicate,pageRequest);
        System.out.println(tCityPage);
    }
    //单表操作系列 end

    //多表操作系列 start
    //针对返回的是Object[]提供了一个很好地解决方案
    @Test
    public void findByLeftJoin(){
        QTCity qtCity = QTCity.tCity;
        QTHotel qtHotel = QTHotel.tHotel;
        Predicate predicate = qtCity.name.like("shanghai");
        List<Tuple> result = tCityRepository.findCityAndHotel(predicate);
        for (Tuple row : result) {
            System.out.println("qtCity:"+row.get(qtCity));
            System.out.println("qtHotel:"+row.get(qtHotel));
            System.out.println("--------------------");
        }
        System.out.println(result);
    }
    @Test
    public void findByLeftJoinPage(){
        QTCity qtCity = QTCity.tCity;
        QTHotel qtHotel = QTHotel.tHotel;
        Predicate predicate = qtCity.name.like("shanghai");
        PageRequest pageRequest = new PageRequest(0,10);
        QueryResults<Tuple> result = tCityRepository.findCityAndHotelPage(predicate,pageRequest);
        for (Tuple row : result.getResults()) {
            System.out.println("qtCity:"+row.get(qtCity));
            System.out.println("qtHotel:"+row.get(qtHotel));
            System.out.println("--------------------");
        }
        System.out.println(result.getTotal());
    }
    //多表操作系列 end
}
