# SpringDataJPA学习记录(一)--环境配置

标签（空格分隔）： springJPA

---

1.maven配置
---------
首先需要spring相关架包,其实spring-data-jpa里面已经依赖了,如果你想用自己的版本则需要额外引入spring相关包.JPA实现还都是hibernate去实现的,所以还需要hibernate相关包.mysql就更不用说了.
```
    <!--JPA start-->
      <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-jpa</artifactId>
        <version>1.10.4.RELEASE</version>
      </dependency>
    <!--JPA end-->

    <!--hibernate start-->
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-core</artifactId>
      <version>${hibernate.version}</version>
    </dependency>
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-entitymanager</artifactId>
      <version>${hibernate.version}</version>
    </dependency>
    <!--hibernate end-->

    <!--mysql start-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>${mysql.version}</version>
    </dependency>
    <!--mysql end-->
```

2.整合Spring
------

整合Spring主要有以下几点要注意:
1.数据源配置
2.JPA提供者,JPA属性配置
3.事务配置
4.jpa:repositories 配置,具体如下代码:

```
<!--配置数据库连接池Druid-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <!-- 数据库基本信息配置 -->
        <property name="url" value="${jdbc.url}" />
        <property name="username" value="${jdbc.username}" />
        <property name="password" value="${jdbc.password}" />
        <property name="driverClassName" value="${jdbc.driver}" />
        <property name="filters" value="${filters}" />
        <!-- 最大并发连接数 -->
        <property name="maxActive" value="${maxActive}" />
        <!-- 初始化连接数量 -->
        <property name="initialSize" value="${initialSize}" />
        <!-- 配置获取连接等待超时的时间 -->
        <property name="maxWait" value="${maxWait}" />
        <!-- 最小空闲连接数 -->
        <property name="minIdle" value="${minIdle}" />
        <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        <property name="timeBetweenEvictionRunsMillis" value="${timeBetweenEvictionRunsMillis}" />
        <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        <property name="minEvictableIdleTimeMillis" value="${minEvictableIdleTimeMillis}" />
        <property name="validationQuery" value="SELECT 1" />
        <property name="testWhileIdle" value="${testWhileIdle}" />
        <property name="testOnBorrow" value="${testOnBorrow}" />
        <property name="testOnReturn" value="${testOnReturn}" />
        <property name="maxOpenPreparedStatements" value="${maxOpenPreparedStatements}" />
        <!-- 打开removeAbandoned功能 -->
        <property name="removeAbandoned" value="${removeAbandoned}" />
        <!-- 1800秒，也就是30分钟 -->
        <property name="removeAbandonedTimeout" value="${removeAbandonedTimeout}" />
        <!-- 关闭abanded连接时输出错误日志 -->
        <property name="logAbandoned" value="${logAbandoned}" />
    </bean>

    <!--第二步-->
    <!--定义实体的工厂bean-->
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--实体类位置-->
        <property name="packagesToScan" value="cn.mrdear.entity"/>
        <!--持久化单元名-->
        <property name="persistenceUnitName" value="TestJPA" />
        <!--JPA提供者-->
        <property name="persistenceProviderClass" value="org.hibernate.ejb.HibernatePersistence"/>
        <!--JPA属性-->
        <property name="jpaProperties">
            <props>
                <!--配置方言-->
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</prop>
                <!--激活查询日志功能-->
                <prop key="hibernate.show_sql">false</prop>
                <!--优雅地输出Sql-->
                <prop key="hibernate.format_sql">false</prop>
                <!--添加一条解释型标注-->
                <prop key="hibernate.use_sql_comments">false</prop>
                <!--配置如何根据java模型生成数据库表结构,常用update,validate-->
                <prop key="hibernate.hbm2ddl.auto">none</prop>
            </props>
        </property>
    </bean>

    <!--第三步-->
    <!--定义事务管理器-->
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="dataSource" ref="dataSource"/>
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>

    <!--第四步-->
    <!--定义repository接口的存放目录-->
    <!--定义接口实现的后缀，通常用Impl-->
    <!--定义实体工厂的引用-->
    <!--定义事务管理器的引用-->
    <jpa:repositories base-package="cn.mrdear.repository"
                      repository-impl-postfix="Impl"
                      entity-manager-factory-ref="entityManagerFactory"
                      transaction-manager-ref="transactionManager"/>

    <!--第五步-->
    <!--声明采用注解的方式申明事务-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
```


----------

3.创建实体类

实体类中常用注解:
* @Entity ：声明这个类是一个实体类
* @Table：指定映射到数据库的表格
* @Id ：映射到数据库表的主键属性,一个实体只能有一个属性被映射为主键
* @GeneratedValue：主键的生成策略
* @Column配置单列属性

-------
```
@Entity//标识该为一个实体
@Table(name = "user")//关联数据库中的user表
public class User {

    @Id//标识该属性为主键
    private Integer id;

    private String name;

    private String address;

    private String phone;

    //省略get和set
}
```


3.Repository接口
--------------
* Repository： 最顶层的接口，是一个空接口，目的是为了统一所有的Repository的类型，且能让组件扫描时自动识别
* CrudRepository: Repository的子接口，提供CRUD 的功能。
* PagingAndSortingRepository:CrudRepository的子接口, 添加分页排序。
* JpaRepository: PagingAndSortingRepository的子接口,增加批量操作等。
* JpaSpecificationExecutor: 用来做复杂查询的接口。

(图片漏了一个字母J)
![这里写图片描述](http://img.blog.csdn.net/20161101215927300)

由图来看,一般使用JpaRepository这个接口做查询即可.这个接口拥有如下方法:

* delete删除或批量删除
* findOne查找单个
* findAll查找所有
* save保存单个或批量保存
* saveAndFlush保存并刷新到数据库

创建repository
```
//User表示该Repository与实体User关联,主键类型为Integer
public interface UserRepository extends JpaRepository<User,Integer> {

}
```
这样就完成了一个基本Repository的创建,可以直接使用其中的方法,而不需要去写实现类.


----------


4.测试
----
关于测试这里,我把测试案例写到test文件夹的话,总是报实体类未被JPA管理,所以改写到java文件夹,具体原因未知.

```
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserRepository userRepository = (UserRepository) applicationContext.getBean("userRepository");
        System.out.println(userRepository.findAll());
        System.out.println(userRepository.findOne(1));
        System.out.println(userRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"id"))));
    }
```

![这里写图片描述](http://img.blog.csdn.net/20161101221534182)