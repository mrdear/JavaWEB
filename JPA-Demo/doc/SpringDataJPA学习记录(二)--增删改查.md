# SpringDataJPA学习记录(二)--增删改查

标签（空格分隔）： springJPA

---

环境配置后,开始增删改查,查是用的最多的,放在最后.

1.增加
----
增加可以使用JpaRepository接口里面的save方法.查看源码可以发现实际上是使用了`em.persist(entity)`来使对象进入持久化状态,最后提交事务的时候再一起更新到数据库.

```
        User user = new User();
        user.setId(99);
        user.setAddress("上海");
        user.setName("张三");
        user.setPhone("110");

        //保存单个
        userRepository.save(user);
        //保存或更新
        userRepository.saveAndFlush(user);

        List<User> users = new ArrayList<>();
        users.add(user);
        //保存多个
        userRepository.save(users);
```

这里还可以批量插入,对于mysql支持`INSERT user VALUES (20,'王二','111','111'),(21,'王二','111','111');`类似这样的sql语句,具体实现就需要自己去写实现了,这样可以一次插入多条记录,效率很高.至于一次插入多少条就可以根据你的业务量来自己制定.


----------

2.删除
----
删除都是根据主键来删除的,区别就是多条sql和单条sql
```
        User user = new User();
        user.setId(21);
        user.setName("王二");

        /**
         * 删除都是根据主键删除
         */
        //删除单条,根据主键值
        userRepository.delete(20);
        //删除全部,先findALL查找出来,再一条一条删除,最后提交事务
        userRepository.deleteAll();
        //删除全部,一条sql
        userRepository.deleteAllInBatch();

        List<User> users = new ArrayList<>();
        users.add(user);
        //删除集合,一条一条删除
        userRepository.delete(users);
        //删除集合,一条sql,拼接or语句  如 id=1 or id=2
        userRepository.deleteInBatch(users);
```


----------

3.修改
----
修改也是根据主键来更新的
```
        User user = new User();
        user.setId(1);
        user.setName("张三");
        /**
         * 更新也是根据主键来更新  update XX  xx where id=1
         */
        userRepository.saveAndFlush(user);
```

批量更新的话,就调用entityManager的merge函数来更新.

首先在service层获取持久化管理器:
```
	@PersistenceContext
	private EntityManager em;
```
批量更新方法,同理插入,删除也都可以如此做.
```
	@Transactional
	public void batchUpateCustom(List<User> users) {
		// TODO Auto-generated method stub
		for(int i = 0; i < users.size(); i++) {  
            em.merge(users.get(i));  
            if(i % 30== 0) {  
                em.flush();  
                em.clear();  
            }  
        }
	}
```


----------

4.查询
----

单表查询,大部分都可以使用下面三种方法解决,多表联合查询的话,下面方法就不是很实用,下一节分析多表查询.

### 1.使用JpaRepository方法
```
        //查找全部
        userRepository.findAll();
        //分页查询全部,返回封装了分页信息
        Page<User> pageInfo = userRepository.findAll(new PageRequest(1, 3, Sort.Direction.ASC,"id"));
        //查找全部,并排序
        userRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"id")));

        User user = new User();
        user.setName("小红");
        //条件查询,可以联合分页,排序
        userRepository.findAll(Example.of(user));
        //查询单个
        userRepository.findOne(1);
```

### 2.解析方法名创建查询

规则:
find+全局修饰+By+实体的属性名称+限定词+连接词+ ...(其它实体属性)+OrderBy+排序属性+排序方向

例如：
```
    //分页查询出符合姓名的记录,同理Sort也可以直接加上
    public List<User> findByName(String name, Pageable pageable);
```

>全局修饰： Distinct， Top， First
关键词： IsNull， IsNotNull， Like， NotLike， Containing， In， NotIn，
IgnoreCase， Between， Equals， LessThan， GreaterThan， After， Before...
排序方向： Asc， Desc
连接词： And， Or


>And --- 等价于 SQL 中的 and 关键字，比如 findByUsernameAndPassword(String user, Striang pwd)；
Or --- 等价于 SQL 中的 or 关键字，比如 findByUsernameOrAddress(String user, String addr)；
Between --- 等价于 SQL 中的 between 关键字，比如 findBySalaryBetween(int max, int min)；
LessThan --- 等价于 SQL 中的 "<"，比如 findBySalaryLessThan(int max)；
GreaterThan --- 等价于 SQL 中的">"，比如 findBySalaryGreaterThan(int min)；
IsNull --- 等价于 SQL 中的 "is null"，比如 findByUsernameIsNull()；
IsNotNull --- 等价于 SQL 中的 "is not null"，比如 findByUsernameIsNotNull()；
NotNull --- 与 IsNotNull 等价；
Like --- 等价于 SQL 中的 "like"，比如 findByUsernameLike(String user)；
NotLike --- 等价于 SQL 中的 "not like"，比如 findByUsernameNotLike(String user)；
OrderBy --- 等价于 SQL 中的 "order by"，比如 findByUsernameOrderBySalaryAsc(String user)；
Not --- 等价于 SQL 中的 "！ ="，比如 findByUsernameNot(String user)；
In --- 等价于 SQL 中的 "in"，比如 findByUsernameIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
NotIn --- 等价于 SQL 中的 "not in"，比如 findByUsernameNotIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；

嵌套实体:
> 主实体中子实体的名称+ _ +子实体的属性名称
List<Person> findByAddress_ZipCode(ZipCode zipCode)
表示查询所有 Address（地址）的zipCode（邮编）为指定值的所有Person(人员)


----------

### 3.JPQL查询

一个类似HQL的语法,在接口上使用@Query标识
```
 @Query("select a from user a where a.id = ?1") 
 public User findById(Long id); 
```

使用@Modifying标识修改
```
 @Modifying 
 @Query("update User a set a.name = ?1 where a.id < ?2") 
 public int updateName(String name, Long id);
```

携带分页信息:
```
    @Query("select u from User u where u.name=?1")
    public List<User> findByName(String name, Pageable pageable);
```

除此之外也可以使用原生sql,只需要`@Query(nativeQuery=true)`标识即可.


创建查询顺序:

>创建查询的顺序
Spring Data JPA 在为接口创建代理对象时，如果发现同时存在多种上述情况可用，它该优先采用哪种策略呢？为此，<jpa:repositories> 提供了 query-lookup-strategy 属性，用以指定查找的顺序。它有如下三个取值：
create --- 通过解析方法名字来创建查询。即使有符合的命名查询，或者方法通过 @Query 指定的查询语句，都将会被忽略。
create-if-not-found --- 如果方法通过 @Query 指定了查询语句，则使用该语句实现查询；如果没有，则查找是否定义了符合条件的命名查询，如果找到，则使用该命名查询；如果两者都没有找到，则通过解析方法名字来创建查询。这是 query-lookup-strategy 属性的默认值。
use-declared-query --- 如果方法通过 @Query 指定了查询语句，则使用该语句实现查询；如果没有，则查找是否定义了符合条件的命名查询，如果找到，则使用该命名查询；如果两者都没有找到，则抛出异常。



5.计数
----
计数就直接使用JpaRepository的count方法
```
        //查找总数量
        userRepository.count();

        User user = new User();
        user.setName("小红");
        //条件计数
        userRepository.count(Example.of(user));
```

6.判断是否存在
--------
计数就直接使用JpaRepository的exists方法
```
        //根据主键判断是否存在
        userRepository.exists(1);

        User user = new User();
        user.setName("小红");
        //根据条件判断是否存在
        userRepository.exists(Example.of(user));
```


----------


7.自定义查询
----------

首先自定义一个接口,用于定义自定义方法,如`UserRepositoryCustom`

然后让`UserRepository`实现该接口,这样的话就可以使用其中的方法.

然后写`UserRepositoryImpl`实现`UserRepositoryCustom`接口

最后设置`jpa:repositories`的`repository-impl-postfix="Impl"`,这样的话JPA会查找自定义实现类命名规则,这样的话JPA在相应`UserRepository`包下面查找实现类,找到则会使用其中的实现方法,而不去自己实现.

具体可以看项目demo,或者下一节的复杂查询


8.备注
----

###1.尽量避免getSingleResult

下面是该方法的部分源码,可以看出,如果查询出来的个数为0或者大于1都抛出异常,一般来说我们希望查找不到就返回null,而抛异常则是不想看到的,如果要使用,则需要try一下该代码块,在catch里面返回null.

```
        try {
            List he = this.query.list();
            if(he.size() == 0) {
                NoResultException uniqueResult1 = new NoResultException("No entity found for query");
                this.getEntityManager().handlePersistenceException(uniqueResult1);
                throw uniqueResult1;
            } else if(he.size() > 1) {
                HashSet uniqueResult = new HashSet(he);
                if(uniqueResult.size() > 1) {
                    NonUniqueResultException nure = new NonUniqueResultException("result returns more than one elements");
                    this.getEntityManager().handlePersistenceException(nure);
                    throw nure;
                } else {
                    return uniqueResult.iterator().next();
                }
            } else {
                return he.get(0);
            }
        } catch (QueryExecutionRequestException var4) {
            throw new IllegalStateException(var4);
        } catch (TypeMismatchException var5) {
            throw new IllegalArgumentException(var5);
        } catch (HibernateException var6) {
            throw this.getEntityManager().convert(var6);
        }
```


基本增删改查就是以上这些,都很容易上手,个人感觉比mybatis方便多了.初学该框架,借鉴了很多博客,更多使用技巧还要在实战中发现,如果问题,请留言交流.