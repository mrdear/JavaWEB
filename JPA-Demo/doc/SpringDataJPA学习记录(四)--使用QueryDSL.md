# SpringDataJPA学习记录(四)--使用QueryDSL

标签（空格分隔）： springJPA

---

1.问题的提出
-------

最近再看公司项目中有如下代码,看了后简直不能忍.缺点列出来的话,如下:
1. 返回类型Object[]数组,至于每一个下标对应哪个字段,没法直观的看到,例如object[11]是什么类型?字段名是什么?这个就无法直观得知.
2. sql中复杂的关系导致不可维护,每一个接手的人都要研究sql半天
3. 动态拼接条件导致类似的代码会大量重复,所以IDEA打开的时候黄了半边天.
4. 该查询为分页查询,这样写的话,还要再copy一个count查询才能拿到总数,无疑又是代码重复.
5. JPA这种框架目的就是少些原生sql语句,大量这样的操作的话,还不如使用dbUtil这样的工具类查询.
```
	@Override
	public List<Object[]> findByPcardCardOrder(
			PcardCardOrder pcardCardOrder,String applyInstName2,Integer page, Integer rows) {
		StringBuffer sql = new StringBuffer(
				"SELECT p.*"
						+",p2.vcard_make_des" 
						+",p3.cardnum_rule_id,p3.vtype_nm"
						+",p4.cn_card_bin,p4.cn_nm"
						+",p5.inst_id,p5.inst_name,p5.apply_range,p5.card_name,p5.card_type,p5.bin_card_material"
						+",p6.inst_name AS apply_inst_name "
						+",p7.inst_name AS apply_inst_name2"
						+ ",p8.inst_name as receive_inst_name"
						+ " FROM "
						+" tbl_pcard_card_order p LEFT JOIN tbl_pcard_vcard_make p2 ON p.make_id = p2.vcard_make_id"
				     +" LEFT JOIN  tbl_pcard_vtype p3 ON p2.vcard_make_vtype_id=p3.vtype_id"
				     +" LEFT JOIN  tbl_pcard_cardnum_rule p4 ON p3.cardnum_rule_id=p4.cn_id"
				     +" LEFT JOIN  tbl_pcard_cardbin p5 ON p4.cn_card_bin=p5.card_bin"
				     +" LEFT JOIN  tbl_pcard_institution p6 ON p5.apply_range=p6.inst_id"
				     +" LEFT JOIN  tbl_pcard_institution p7 ON p.apply_inst_id=p7.inst_id"
				     +" LEFT JOIN  tbl_pcard_institution p8 ON p.receive_inst=p8.inst_id"
				     +" WHERE 1=1 ");
		int i = 1;
		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(pcardCardOrder.getCordId())) {
			sql.append(" and p.cord_id=");
			sql.append("?" + i);
			map.put(i + "", pcardCardOrder.getCordId());
			i++;
		}
		if (!StringUtils.isEmpty(pcardCardOrder.getAppointMchtcard())) {
			sql.append(" and p.appoint_mchtcard=");
			sql.append("?" + i);
			map.put(i + "", pcardCardOrder.getAppointMchtcard());
			i++;
		}

		if (!StringUtils.isEmpty(pcardCardOrder.getMakeId())) {
			sql.append(" and p.make_id like ");
			sql.append("?" + i);
			map.put(i + "","%%"+ pcardCardOrder.getMakeId()+"%%");
			i++;
		}

		if (!StringUtils.isEmpty(applyInstName2)) {
			sql.append(" and p7.inst_name like ");
			sql.append("?"+i);
			map.put(i+"","%%"+applyInstName2+"%%");
			i++;
		}
		
		sql.append(" order by p.ct_dm desc");
		Query query = entityManager.createNativeQuery(sql.toString());
		for (String key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		if (page != null && rows != null) {
			query.setFirstResult(rows * (page - 1));
			query.setMaxResults(rows);
		}
		return query.getResultList();
	}
```


----------

2.使用QueryDSL
----------
queryDSL就可以避免上面全部的问题,他有以下特点:
1. QueryDSL仅仅是一个通用的查询框架，专注于通过Java API构建类型安全的SQL查询。
2. Querydsl可以通过一组通用的查询API为用户构建出适合不同类型ORM框架或者是SQL的查询语句，也就是说QueryDSL是基于各种ORM框架以及SQL之上的一个通用的查询框架。
3. 借助QueryDSL可以在任何支持的ORM框架或者SQL平台上以一种通用的API方式来构建查询。目前QueryDSL支持的平台包括JPA,JDO,SQL,Java Collections,RDF,Lucene,Hibernate Search。
4. 官网地址:[点击进入][1]

###2.1配置到项目
首先对于queryDSL有两个版本,`com.mysema.querydsl`和`com.querydsl`,前者是3.X系列后者是4.X系列,这里使用的是后者.

第一步:Maven引入依赖:
```
    <!--query dsl-->
    <dependency>
      <groupId>com.querydsl</groupId>
      <artifactId>querydsl-jpa</artifactId>
      <version>${querydsl.version}</version>
    </dependency>
    <dependency>
      <groupId>com.querydsl</groupId>
      <artifactId>querydsl-apt</artifactId>
      <version>${querydsl.version}</version>
      <scope>provided</scope>
    </dependency>
    <!--query dsl end-->
```
第二步:加入插件,用于生成查询实例
```
<!--该插件可以生成querysdl需要的查询对象，执行mvn compile即可-->
      <plugin>
        <groupId>com.mysema.maven</groupId>
        <artifactId>apt-maven-plugin</artifactId>
        <version>1.1.3</version>
        <executions>
          <execution>
            <goals>
              <goal>process</goal>
            </goals>
            <configuration>
              <outputDirectory>target/generated-sources/java</outputDirectory>
              <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
            </configuration>
          </execution>
        </executions>
      </plugin>
```
执行mvn compile之后,可以找到该`target/generated-sources/java`,然后IDEA标示为源代码目录即可.
![1.jpg](http://upload-images.jianshu.io/upload_images/2148449-752f2ecd91207128.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

###2.2 单表动态分页查询
Spring Data JPA中提供了QueryDslPredicateExecutor接口,用于支持QueryDSL的查询操作,这样的话单表动态查询就可以参考如下代码:
```
        //动态条件
        QTCity qtCity = QTCity.tCity;
        //该Predicate为querydsl下的类,支持嵌套组装复杂查询条件
        Predicate predicate = qtCity.id.longValue().lt(3)
                                       .and(qtCity.name.like("shanghai"));
        //分页排序
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC,"id"));
        PageRequest pageRequest = new PageRequest(0,10,sort);
        //查找结果
        Page<TCity> tCityPage = tCityRepository.findAll(predicate,pageRequest);
```
###2.3多表动态查询
queryDSL对多表查询提供了一个很好地封装,看下面代码:
```
    /**
     * 关联查询示例,查询出城市和对应的旅店
     * @param predicate 查询条件
     * @return 查询实体
     */
    @Override
    public List<Tuple> findCityAndHotel(Predicate predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        JPAQuery<Tuple> jpaQuery = queryFactory.select(QTCity.tCity,QTHotel.tHotel)
                                        .from(QTCity.tCity)
                                        .leftJoin(QTHotel.tHotel)
                                        .on(QTHotel.tHotel.city.longValue().eq(QTCity.tCity.id.longValue()));
        jpaQuery.where(predicate);
        return jpaQuery.fetch();
    }
```
城市表左连接旅店表,当该旅店属于这个城市时查询出两者的详细字段,存放到一个Tuple的多元组中.相比原生sql,简单清晰了很多.
那么该怎么调用这个方法呢?
```
 @Test
    public void findByLeftJoin(){
        QTCity qtCity = QTCity.tCity;
        QTHotel qtHotel = QTHotel.tHotel;
        //查询条件
        Predicate predicate = qtCity.name.like("shanghai");
        //调用
        List<Tuple> result = tCityRepository.findCityAndHotel(predicate);
        //对多元组取出数据,这个和select时的数据相匹配
        for (Tuple row : result) {
            System.out.println("qtCity:"+row.get(qtCity));
            System.out.println("qtHotel:"+row.get(qtHotel));
            System.out.println("--------------------");
        }
        System.out.println(result);
    }
```
下面是自动生成的sql语句:
```
select
        tcity0_.id as id1_0_0_,
        thotel1_.id as id1_1_1_,
        tcity0_.country as country2_0_0_,
        tcity0_.map as map3_0_0_,
        tcity0_.name as name4_0_0_,
        tcity0_.state as state5_0_0_,
        thotel1_.address as address2_1_1_,
        thotel1_.city as city3_1_1_,
        thotel1_.name as name4_1_1_ 
    from
        t_city tcity0_ 
    left outer join
        t_hotel thotel1_ 
            on (
                cast(thotel1_.city as signed)=cast(tcity0_.id as signed)
            ) 
    where
        tcity0_.name like ? escape '!'
```

###2.4 多表动态分页查询
分页查询对于queryDSL无论什么样的sql只需要写一遍,会自动转换为相应的count查询,也就避免了文章开始的问题4.
```
      @Override
    public QueryResults<Tuple> findCityAndHotelPage(Predicate predicate,Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        JPAQuery<Tuple> jpaQuery = queryFactory.select(QTCity.tCity.id,QTHotel.tHotel)
                                               .from(QTCity.tCity)
                                               .leftJoin(QTHotel.tHotel)
                                               .on(QTHotel.tHotel.city.longValue().eq(QTCity.tCity.id.longValue()))
                                               .offset(pageable.getOffset())
                                               .limit(pageable.getPageSize());
        return jpaQuery.fetchResults();
    }
```
和上面不同之处在于这里使用了`offset`和`limit`限制查询结果.并且返回一个QueryResults,该类会自动实现count查询和结果查询,并进行封装.
调用形式如下:
```
    @Test
    public void findByLeftJoinPage(){
        QTCity qtCity = QTCity.tCity;
        QTHotel qtHotel = QTHotel.tHotel;
        //条件
        Predicate predicate = qtCity.name.like("shanghai");
        //分页
        PageRequest pageRequest = new PageRequest(0,10);
        //调用查询
        QueryResults<Tuple> result = tCityRepository.findCityAndHotelPage(predicate,pageRequest);
        //结果取出
        for (Tuple row : result.getResults()) {
            System.out.println("qtCity:"+row.get(qtCity));
            System.out.println("qtHotel:"+row.get(qtHotel));
            System.out.println("--------------------");
        }
        //取出count查询总数
        System.out.println(result.getTotal());
    }
```
生成的原生sql,当该count查询结果为0的话,则直接返回,并不会再次查询结果:
```
    select
        count(tcity0_.id) as col_0_0_ 
    from
        t_city tcity0_ 
    left outer join
        t_hotel thotel1_ 
            on (
                cast(thotel1_.city as signed)=cast(tcity0_.id as signed)
            )
```
生成的原生sql:
```
    select
        tcity0_.id as col_0_0_,
        thotel1_.id as col_1_0_,
        thotel1_.id as id1_1_,
        thotel1_.address as address2_1_,
        thotel1_.city as city3_1_,
        thotel1_.name as name4_1_ 
    from
        t_city tcity0_ 
    left outer join
        t_hotel thotel1_ 
            on (
                cast(thotel1_.city as signed)=cast(tcity0_.id as signed)
            ) limit ?
```

3.总结
----
仿照上面的例子就可以很好地对文章开头的sql改造,无非是多了几个leftJoin,条件的组装放到service层,或者一个专门组装条件的工厂都可以,对于Repository来说只需要一个predicate即可.

近期逐渐把公司项目切换为queryDSL,个人感觉和Spring Data JPA是绝配.文章写得不是很详细,更多请参考Demo代码:

github: https://github.com/nl101531/JavaWEB


  [1]: http://www.querydsl.com/static/querydsl/4.1.3/reference/html_single/