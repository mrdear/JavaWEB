# SpringDataJPA学习记录(三)--复杂查询的封装

标签（空格分隔）： springJPA

---

###1.使用CriteriaBuilder构建JPQL

在UserRepositoryImpl中使用CriteriaBuilder实现根据id查询,下面是代码:
```
    public void findById(Integer id){
        //select u from User u where u.id = 1
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class); //from User
        cq.select(root); //select * from User
        javax.persistence.criteria.Predicate pre = cb.equal(root.get("id").as(Integer.class),id);//id=1
        cq.where(pre);//where id=1
        Query query = entityManager.createQuery(cq);//select u from User u where u.id = 1

        System.out.println(query.getResultList());
    }
```

缺点:
1. 代码量多
2. 不易维护
3. 条件复杂的话,则会显得很混乱.

个人认为,CriteriaQuery不适合用来复杂sql的查询实现,不过可以使用其对一些通用的sql查询封装到BaseDao中.具体封装接着往下看.

----------

###2.使用JpaSpecificationExecutor查询

该接口有如下方法,里面传入条件都是Specification,该接口会返回一个Predicate条件集合,因此就可以在这里封装.
```
public interface JpaSpecificationExecutor<T> {

	T findOne(Specification<T> spec);

	List<T> findAll(Specification<T> spec);

	Page<T> findAll(Specification<T> spec, Pageable pageable);

	List<T> findAll(Specification<T> spec, Sort sort);

	long count(Specification<T> spec);
}
```

#### 1.构造过滤条件集合
写法学习自shop++项目.
Operator枚举类里面的operator属性为了构建原生sql使用
```
/**
 * @author Niu Li
 * @date 2016/11/17
 */

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 筛选
 * copy from shopXX
 */
public class Filter implements Serializable {

    private static final long serialVersionUID = -8712382358441065075L;

    /**
     * 运算符
     */
    public enum Operator {

        /** 等于 */
        eq(" = "),

        /** 不等于 */
        ne(" != "),

        /** 大于 */
        gt(" > "),

        /** 小于 */
        lt(" < "),

        /** 大于等于 */
        ge(" >= "),

        /** 小于等于 */
        le(" <= "),

        /** 类似 */
        like(" like "),

        /** 包含 */
        in(" in "),

        /** 为Null */
        isNull(" is NULL "),

        /** 不为Null */
        isNotNull(" is not NULL ");
        Operator(String operator) {
            this.operator = operator;
        }

        private String operator;

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }
    }

    /** 默认是否忽略大小写 */
    private static final boolean DEFAULT_IGNORE_CASE = false;

    /** 属性 */
    private String property;

    /** 运算符 */
    private Filter.Operator operator;

    /** 值 */
    private Object value;

    /** 是否忽略大小写 */
    private Boolean ignoreCase = DEFAULT_IGNORE_CASE;

    /**
     * 构造方法
     */
    public Filter() {
    }

    /**
     * 构造方法
     *
     * @param property
     *            属性
     * @param operator
     *            运算符
     * @param value
     *            值
     */
    public Filter(String property, Filter.Operator operator, Object value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    /**
     * 构造方法
     *
     * @param property
     *            属性
     * @param operator
     *            运算符
     * @param value
     *            值
     * @param ignoreCase
     *            忽略大小写
     */
    public Filter(String property, Filter.Operator operator, Object value, boolean ignoreCase) {
        this.property = property;
        this.operator = operator;
        this.value = value;
        this.ignoreCase = ignoreCase;
    }

    /**
     * 返回等于筛选
     *
     * @param property
     *            属性
     * @param value
     *            值
     * @return 等于筛选
     */
    public static Filter eq(String property, Object value) {
        return new Filter(property, Filter.Operator.eq, value);
    }

    /**
     * 返回等于筛选
     *
     * @param property
     *            属性
     * @param value
     *            值
     * @param ignoreCase
     *            忽略大小写
     * @return 等于筛选
     */
    public static Filter eq(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Filter.Operator.eq, value, ignoreCase);
    }

    /**
     * 返回不等于筛选
     *
     * @param property
     *            属性
     * @param value
     *            值
     * @return 不等于筛选
     */
    public static Filter ne(String property, Object value) {
        return new Filter(property, Filter.Operator.ne, value);
    }

    /**
     * 返回不等于筛选
     *
     * @param property
     *            属性
     * @param value
     *            值
     * @param ignoreCase
     *            忽略大小写
     * @return 不等于筛选
     */
    public static Filter ne(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Filter.Operator.ne, value, ignoreCase);
    }

    /**
     * 返回大于筛选
     *
     * @param property
     *            属性
     * @param value
     *            值
     * @return 大于筛选
     */
    public static Filter gt(String property, Object value) {
        return new Filter(property, Filter.Operator.gt, value);
    }

    /**
     * 返回小于筛选
     *
     * @param property
     *            属性
     * @param value
     *            值
     * @return 小于筛选
     */
    public static Filter lt(String property, Object value) {
        return new Filter(property, Filter.Operator.lt, value);
    }

    /**
     * 返回大于等于筛选
     *
     * @param property
     *            属性
     * @param value
     *            值
     * @return 大于等于筛选
     */
    public static Filter ge(String property, Object value) {
        return new Filter(property, Filter.Operator.ge, value);
    }

    /**
     * 返回小于等于筛选
     *
     * @param property
     *            属性
     * @param value
     *            值
     * @return 小于等于筛选
     */
    public static Filter le(String property, Object value) {
        return new Filter(property, Filter.Operator.le, value);
    }

    /**
     * 返回相似筛选
     *
     * @param property
     *            属性
     * @param value
     *            值
     * @return 相似筛选
     */
    public static Filter like(String property, Object value) {
        return new Filter(property, Filter.Operator.like, value);
    }

    /**
     * 返回包含筛选
     *
     * @param property
     *            属性
     * @param value
     *            值
     * @return 包含筛选
     */
    public static Filter in(String property, Object value) {
        return new Filter(property, Filter.Operator.in, value);
    }

    /**
     * 返回为Null筛选
     *
     * @param property
     *            属性
     * @return 为Null筛选
     */
    public static Filter isNull(String property) {
        return new Filter(property, Filter.Operator.isNull, null);
    }

    /**
     * 返回不为Null筛选
     *
     * @param property
     *            属性
     * @return 不为Null筛选
     */
    public static Filter isNotNull(String property) {
        return new Filter(property, Filter.Operator.isNotNull, null);
    }

    /**
     * 返回忽略大小写筛选
     *
     * @return 忽略大小写筛选
     */
    public Filter ignoreCase() {
        this.ignoreCase = true;
        return this;
    }

    /**
     * 获取属性
     *
     * @return 属性
     */
    public String getProperty() {
        return property;
    }

    /**
     * 设置属性
     *
     * @param property
     *            属性
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * 获取运算符
     *
     * @return 运算符
     */
    public Filter.Operator getOperator() {
        return operator;
    }

    /**
     * 设置运算符
     *
     * @param operator
     *            运算符
     */
    public void setOperator(Filter.Operator operator) {
        this.operator = operator;
    }

    /**
     * 获取值
     *
     * @return 值
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value
     *            值
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * 获取是否忽略大小写
     *
     * @return 是否忽略大小写
     */
    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    /**
     * 设置是否忽略大小写
     *
     * @param ignoreCase
     *            是否忽略大小写
     */
    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
     * 重写equals方法
     *
     * @param obj
     *            对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Filter other = (Filter) obj;
        return new EqualsBuilder().append(getProperty(), other.getProperty()).append(getOperator(), other.getOperator()).append(getValue(), other.getValue()).isEquals();
    }

    /**
     * 重写hashCode方法
     *
     * @return HashCode
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getProperty()).append(getOperator()).append(getValue()).toHashCode();
    }

}

```

####2.构造排序字段
```
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.io.Serializable;
/**
 * 排序
 *
 * @author copy from shopxx
 */
public class Order implements Serializable {

    private static final long serialVersionUID = -3078342809727773232L;

    /**
     * 方向
     */
    public enum Direction {

        /** 递增 */
        asc,

        /** 递减 */
        desc
    }

    /** 默认方向 */
    private static final Order.Direction DEFAULT_DIRECTION = Order.Direction.desc;

    /** 属性 */
    private String property;

    /** 方向 */
    private Order.Direction direction = DEFAULT_DIRECTION;

    @Override
    public String toString() {
        return property+" " + direction.name();
    }

    /**
     * 构造方法
     */
    public Order() {
    }

    /**
     * 构造方法
     *
     * @param property
     *            属性
     * @param direction
     *            方向
     */
    public Order(String property, Order.Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    /**
     * 返回递增排序
     *
     * @param property
     *            属性
     * @return 递增排序
     */
    public static Order asc(String property) {
        return new Order(property, Order.Direction.asc);
    }

    /**
     * 返回递减排序
     *
     * @param property
     *            属性
     * @return 递减排序
     */
    public static Order desc(String property) {
        return new Order(property, Order.Direction.desc);
    }

    /**
     * 获取属性
     *
     * @return 属性
     */
    public String getProperty() {
        return property;
    }

    /**
     * 设置属性
     *
     * @param property
     *            属性
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * 获取方向
     *
     * @return 方向
     */
    public Order.Direction getDirection() {
        return direction;
    }

    /**
     * 设置方向
     *
     * @param direction
     *            方向
     */
    public void setDirection(Order.Direction direction) {
        this.direction = direction;
    }

    /**
     * 重写equals方法
     *
     * @param obj
     *            对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Order other = (Order) obj;
        return new EqualsBuilder().append(getProperty(), other.getProperty()).append(getDirection(), other.getDirection()).isEquals();
    }

    /**
     * 重写hashCode方法
     *
     * @return HashCode
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getProperty()).append(getDirection()).toHashCode();
    }

}

```

####3.查询语句生成

#####3.1基本框架
```
/**
 * 封装查询条件的实体
 * @author Niu Li
 * @date 2016/11/17
 */
public class QueryParams<T> implements Specification<T> {

    /** 属性分隔符 */
    private static final String PROPERTY_SEPARATOR = ".";
    /**
     * and条件
     */
    private List<Filter> andFilters = new ArrayList<>();
    /**
     * or条件
     */
    private List<Filter> orFilters = new ArrayList<>();
    /**
     * 排序属性
     */
    private List<Order> orders = new ArrayList<>();
        /**
     * 获取Path
     *
     * @param path
     *            Path
     * @param propertyPath
     *            属性路径
     * @return Path
     */
    @SuppressWarnings("unchecked")
    private <X> Path<X> getPath(Path<?> path, String propertyPath) {
        if (path == null || StringUtils.isEmpty(propertyPath)) {
            return (Path<X>) path;
        }
        String property = StringUtils.substringBefore(propertyPath, PROPERTY_SEPARATOR);
        return getPath(path.get(property), StringUtils.substringAfter(propertyPath, PROPERTY_SEPARATOR));
    }
}
```

#####3.2分析and条件:

```
    
    
    /**
     * 转换为Predicate
     *
     * @param root
     *            Root
     * @return Predicate
     */
    @SuppressWarnings("unchecked")
    private Predicate toAndPredicate(Root<T> root,CriteriaBuilder criteriaBuilder) {
        Predicate restrictions = criteriaBuilder.conjunction();
        if (root == null || CollectionUtils.isEmpty(andFilters)) {
            return restrictions;
        }
        for (Filter filter : andFilters) {
            if (filter == null) {
                continue;
            }
            String property = filter.getProperty();
            Filter.Operator operator = filter.getOperator();
            Object value = filter.getValue();
            Boolean ignoreCase = filter.getIgnoreCase();
            Path<?> path = getPath(root, property);
            if (path == null) {
                continue;
            }
            //根据运算符生成相应条件
            switch (operator) {
                case eq:
                    if (value != null) {
                        if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
                            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
                        } else {
                            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(path, value));
                        }
                    } else {
                        restrictions = criteriaBuilder.and(restrictions, path.isNull());
                    }
                    break;
                case ne:
                    if (value != null) {
                        if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
                            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
                        } else {
                            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.notEqual(path, value));
                        }
                    } else {
                        restrictions = criteriaBuilder.and(restrictions, path.isNotNull());
                    }
                    break;
                case gt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.gt((Path<Number>) path, (Number) value));
                    }
                    break;
                case lt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lt((Path<Number>) path, (Number) value));
                    }
                    break;
                case ge:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge((Path<Number>) path, (Number) value));
                    }
                    break;
                case le:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le((Path<Number>) path, (Number) value));
                    }
                    break;
                case like:
                    if (String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
                        if (BooleanUtils.isTrue(ignoreCase)) {
                            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
                        } else {
                            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like((Path<String>) path, (String) value));
                        }
                    }
                    break;
                case in:
                    restrictions = criteriaBuilder.and(restrictions, path.in(value));
                    break;
                case isNull:
                    restrictions = criteriaBuilder.and(restrictions, path.isNull());
                    break;
                case isNotNull:
                    restrictions = criteriaBuilder.and(restrictions, path.isNotNull());
                    break;
            }
        }
        return restrictions;
    }
```

#####3.3分析or条件:
把and中的and改为or即可
```
 /**
     * 转换为Predicate
     *
     * @param root
     *            Root
     * @return Predicate
     */
    @SuppressWarnings("unchecked")
    private Predicate toOrPredicate(Root<T> root,CriteriaBuilder criteriaBuilder) {
        Predicate restrictions = criteriaBuilder.disjunction();
        if (root == null || CollectionUtils.isEmpty(andFilters)) {
            return restrictions;
        }
        for (Filter filter : orFilters) {
            if (filter == null) {
                continue;
            }
            String property = filter.getProperty();
            Filter.Operator operator = filter.getOperator();
            Object value = filter.getValue();
            Boolean ignoreCase = filter.getIgnoreCase();
            Path<?> path = getPath(root, property);
            if (path == null) {
                continue;
            }
            switch (operator) {
                case eq:
                    if (value != null) {
                        if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
                            restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.equal(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
                        } else {
                            restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.equal(path, value));
                        }
                    } else {
                        restrictions = criteriaBuilder.or(restrictions, path.isNull());
                    }
                    break;
                case ne:
                    if (value != null) {
                        if (BooleanUtils.isTrue(ignoreCase) && String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
                            restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.notEqual(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
                        } else {
                            restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.notEqual(path, value));
                        }
                    } else {
                        restrictions = criteriaBuilder.or(restrictions, path.isNotNull());
                    }
                    break;
                case gt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.gt((Path<Number>) path, (Number) value));
                    }
                    break;
                case lt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.lt((Path<Number>) path, (Number) value));
                    }
                    break;
                case ge:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.ge((Path<Number>) path, (Number) value));
                    }
                    break;
                case le:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && value instanceof Number) {
                        restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.le((Path<Number>) path, (Number) value));
                    }
                    break;
                case like:
                    if (String.class.isAssignableFrom(path.getJavaType()) && value instanceof String) {
                        if (BooleanUtils.isTrue(ignoreCase)) {
                            restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.like(criteriaBuilder.lower((Path<String>) path), ((String) value).toLowerCase()));
                        } else {
                            restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.like((Path<String>) path, (String) value));
                        }
                    }
                    break;
                case in:
                    restrictions = criteriaBuilder.or(restrictions, path.in(value));
                    break;
                case isNull:
                    restrictions = criteriaBuilder.or(restrictions, path.isNull());
                    break;
                case isNotNull:
                    restrictions = criteriaBuilder.or(restrictions, path.isNotNull());
                    break;
            }
        }
        return restrictions;
    }

```

#####3.4分析排序条件:

```
/**
     * 转换为Order
     *
     * @param root
     *            Root
     * @return Order
     */
    private List<javax.persistence.criteria.Order> toOrders(Root<T> root,CriteriaBuilder criteriaBuilder) {
        List<javax.persistence.criteria.Order> orderList = new ArrayList<javax.persistence.criteria.Order>();
        if (root == null || CollectionUtils.isEmpty(orders)) {
            return orderList;
        }
        for (Order order : orders) {
            if (order == null) {
                continue;
            }
            String property = order.getProperty();
            Order.Direction direction = order.getDirection();
            Path<?> path = getPath(root, property);
            if (path == null || direction == null) {
                continue;
            }
            switch (direction) {
                case asc:
                    orderList.add(criteriaBuilder.asc(path));
                    break;
                case desc:
                    orderList.add(criteriaBuilder.desc(path));
                    break;
            }
        }
        return orderList;
    }

```

最后在toPredicate方法中构造最终条件
```
    /**
     * 生成条件的
     * @param root 该对象的封装
     * @param query 查询构建器
     * @param cb 构建器
     * @return 条件集合
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate restrictions = cb.and(toAndPredicate(root,cb));
        restrictions = cb.and(restrictions,toOrPredicate(root,cb));
        query.orderBy(toOrders(root,cb));
        return restrictions;
    }
```

加上方便的链式调用方法
```
    /**
     * 添加一个and条件
     * @param filter 该条件
     * @return 链式调用
     */
    public  QueryParams and(Filter filter){
        this.andFilters.add(filter);
        return this;
    }
    /**
     * 添加多个and条件
     * @param filter 该条件
     * @return 链式调用
     */
    public  QueryParams and(Filter ...filter){
        this.andFilters.addAll(Arrays.asList(filter));
        return this;
    }
    /**
     * 添加一个or条件
     * @param filter 该条件
     * @return 链式调用
     */
    public  QueryParams or(Filter filter){
        this.orFilters.add(filter);
        return this;
    }
    /**
     * 添加多个or条件
     * @param filter 该条件
     * @return 链式调用
     */
    public  QueryParams or(Filter ...filter){
        this.orFilters.addAll(Arrays.asList(filter));
        return this;
    }
    /**
     * 升序字段
     * @param property 该字段对应变量名
     * @return 链式调用
     */
    public  QueryParams orderASC(String property){
        this.orders.add(Order.asc(property));
        return this;
    }
    /**
     * 降序字段
     * @param property 该字段对应变量名
     * @return 链式调用
     */
    public  QueryParams orderDESC(String property){
        this.orders.add(Order.desc(property));
        return this;
    }

    /**
     * 清除所有条件
     * @return 该实例
     */
    public QueryParams clearAll(){
        if (!this.andFilters.isEmpty()) this.andFilters.clear();
        if (!this.orFilters.isEmpty()) this.orFilters.clear();
        if (!this.orders.isEmpty()) this.orders.clear();
        return this;
    }
    /**
     * 清除and条件
     * @return 该实例
     */
    public QueryParams clearAnd(){
        if (!this.andFilters.isEmpty()) this.andFilters.clear();
        return this;
    }
    /**
     * 清除or条件
     * @return 该实例
     */
    public QueryParams clearOr(){
        if (!this.orFilters.isEmpty()) this.andFilters.clear();
        return this;
    }
    /**
     * 清除order条件
     * @return 该实例
     */
    public QueryParams clearOrder(){
        if (!this.orders.isEmpty()) this.orders.clear();
        return this;
    }
    //省略get和set方法
```

#####3.5测试:

首先让PcardOrderRepository接口继承加上JpaSpecificationExecutor
```
public interface PcardOrderRepository extends JpaRepository<PcardOrder,String>
        ,PcardOrderRepositoryCustom,JpaSpecificationExecutor<PcardOrder> {

}
```

编写测试代码,这个使用的是CriteriaBuilder构建查询的,所以**查询字段都是JPQL字段**,并不是原生sql
```
     QueryParams<PcardOrder> queryParams = new QueryParams<>();
        //使用Specification条件查询,使用JPQL字段查询
        queryParams
                .and(Filter.eq("acctId","0014779934917371041"),Filter.ne("orderAmt",0L),
                        Filter.eq("orderRespCd","00"))
                .or(Filter.eq("orderTypeId","A003"),Filter.eq("orderTypeId","A007"),
                        Filter.eq("orderTypeId","A021"),Filter.eq("orderTypeId","A018"))
                .orderDESC("createTime");

        Page<PcardOrder> JPQLlist = pcardOrderRepository.findAll(queryParams,new PageRequest(0,2));
        
        //构造出来的条件
        where
        1=1 
        and pcardorder0_.acct_id=? 
        and pcardorder0_.order_amt<>0 
        and pcardorder0_.order_resp_cd=? 
        and (
            0=1 
            or pcardorder0_.order_type_id=? 
            or pcardorder0_.order_type_id=? 
            or pcardorder0_.order_type_id=? 
            or pcardorder0_.order_type_id=?
        ) 
    order by
        pcardorder0_.create_time desc limit ?
```


----------

###3.原生sql查询
还是利用上面的Filter,具体还是遍历+拼接,示例中我卸载了公共方法中,需要使用的Impl直接extends即可.
####1.解析条件
解析条件实际上就是拼接sql,代码很简单.
```
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

}

```

####2.测试

在自定义接口中加入方法
```
/**
 * @author Niu Li
 * @date 2016/11/17
 */
public interface PcardOrderRepositoryCustom {

    List findByQueryParam(QueryParams queryParams, Pageable pageable);
}
```
然后实现类中需要写部分sql
```
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
```

测试代码:
```
//使用原生sql查询,注意这里使用原生sql字段,并非JPQL字段,
        //本质是根据BaseRepository.analysisQueryParams 来拼接条件,可以根据自己的需求更改
        queryParams.clearAll()
                .and(Filter.eq("acct_id","0014779934917371041"),Filter.ne("order_amt",0L),
                        Filter.eq("order_resp_cd","00"))
                .or(Filter.eq("order_type_id","A003"),Filter.eq("order_type_id","A007"),
                        Filter.eq("order_type_id","A021"),Filter.eq("order_type_id","A018"))
                .orderDESC("create_time");
        List nativeSqlList = pcardOrderRepository.findByQueryParam(queryParams,new PageRequest(0,2));
        //构造出来的sql
        where
        1=1  
        and (
            order_type_id  =  ? 
            or order_type_id  =  ? 
            or order_type_id  =  ? 
            or order_type_id  =  ? 
        ) 
        and acct_id  =  ? 
        and order_amt  !=  ? 
        and order_resp_cd  =  ? 
    order by
        create_time desc limit ?
```


----------


###4.使用原生sql查询出Map集合

使用原生sql进行表关联查询,返回值一般都用List<Object[]>来接收,这种方法很不利于项目的维护,你并不一定记得Object[0]是什么字段,一旦sql发生变化,则整个Object[]数组的顺序就会发生变化.
```
    public List<Object[]> findById(int id) {
        String sql = "select u.*,c.* from user u,commont c where u.id = c.id and id=?1";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1,id);
        return query.getResultList();
    }
```

那么就要改进,使其返回Map

```
    public List findById (int id) {
        String sql = "select u.*,c.* from user u,commont c where u.id = c.id and id=?1";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1,id);
        //转换为Map集合
        query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }
    
    //实际上返回的是一个List<Map>集合
```
这样的返回值是一个List<Map>类型,取出的时候直接根据键值取即可.


