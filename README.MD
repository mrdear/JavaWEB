### 2017.9.27
随着开发经验的增加,现在觉得这种Demo对于学习一个框架起不到太大的作用,顶多是个参考,并且由于使用经验的增加,对于之前一些错误的用法很难得到纠正,如果想要快速入门的话官方+造轮子才是最佳选择,类似豆瓣有许多开放的API,都可以用来练手,最后该项目不再更新,这点经验希望能帮助你.


﻿#该仓库主要记录学习javaWEB中一些Demo

1.SSM-Demo
----------
使用maven整合的Spring,SpringMVC,Mybatis的案例,数据库使用mysql,数据源为druid,可以直接拿过来使用,进行开发.


----------

2.Jersey-Demo
--------------
该Demo是做微信设备功能开发时学习整合的,Demo里面写了Jersey常用配置,以及和Spring的整合,还有微信服务器的验证入口,对于微信设备开发可以直接使用,随着不断的学习会继续更新该项目


----------

3.Spring-Boot-Demo
--------------
该Demo是做学习Spring-Boot时整合环境,整合了thymeleaf,Mybatis,Druid,ehcache可以拉下来直接进行二次开发,如果是WebService服务的话,去掉thymeleaf即可.

4.Maven-Demo
--------------
该demo针对具体项目所整合.主要设计mybatis多模块使用,spring多模块使用,要点就是无论什么模块,只要配置文件加载位置正确配置,就不会出问题.

5.HtmlUnit-Demo   
--------------
使用htmlUnit模拟浏览器请求,抓取Shadowsocks的一些免费账户使用,在win下配合bat脚本启动,很是方便.详情可以看项目的Main方法使用说明.


6.Pay-Demo
--------------
对接银联无跳转支付,支付宝wap支付,微信支付,项目结构清晰,注释全面,可以很好的帮你理清对接支付的顺序,测试Demo配置好Config后都可以直接运行,希望对你有帮助.参考博客地址:http://blog.csdn.net/u012706811/article/details/52988782

7.JPA-Demo
--------------
SpringDataJPA+QueryDSL使用,需要配合博客博文http://www.jianshu.com/p/e3812fc23f83 ,两者配合很好地解决了单表动态查询,多表动态查询.

8.Util-Demo
--------------
开发中常用工具类的写法,比如PropertiesUtil,XmlUtil,HttpUtil等,该Demo希望大家能不断扩充.

9.gRPC-Demo
--------------
参考官方案例的gRPC的Demo,里面有Hello World和 RouteGuide案例,适合入门.相关博文参考 http://www.jianshu.com/nb/9491747

10.Excel-Demo
--------------
使用Apache-poi封装的一个通用表单导出模块和解析模块,可以很容易的迁移到项目中,配合Jackxml使用更佳

11.Spring-Data-Redis
--------------
SpringDataRedis学习的一个Demo,内容不多,配合博文:[redis学习记录(四)-SpringDataRedis分析](http://mrdear.cn/2017/03/29/linux/redis%E5%AD%A6%E4%B9%A0%E8%AE%B0%E5%BD%95(%E5%9B%9B)-SpringDataRedis%E5%88%86%E6%9E%90/)可以很好的入手

12.Spring-Cloud-Demo
--------------
学习Spring Cloud的一个Demo,其中对于Eureka,Ribbon,Feign等都有相应的Demo,相应博文会更新在[我的博客](mrdear.cn)上,希望对你有帮助

13.Dubbo-Demo
--------------
增加Dubbo-Demo,一个基本的RPC调用Demo

14.Motan-Demo
--------------
增加Motan-Demo,一个基本的RPC调用Demo

15.Spring-Security-Demo
--------------
增加Spring Security Demo,项目中使用了JWT Token验证机制,该项目可以很好的了解整个流程的作用,需要配合我博客中Spring Security相关博文食用.

16.待添加
--------------
