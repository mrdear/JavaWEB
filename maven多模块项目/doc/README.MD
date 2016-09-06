##一.模块详解

1. core主要是分发器,接收请求后转向其他模块寻找对应的Service来调用其方法.
2. common里面我打算放置通用的数据库表(比如用户表,这个任何设备都通用)和一些utils工具(例如httpclient以及xml解析器)
3. switch里面主要封装了插座操作的entity,mapper,service,对外提供方法

##二.改动

1. mybatis全部采用mapper代理方式开发,配置多数据源也方便.
2. 替换jsp为thymeleaf模板引擎
3. springMVC的json解析器替换为fastjson

##三.扩展

1. 扩展的话直接另起一个maven项目,仿照switch里面来封装service,在core中控制行为即可.
2. 目前是都放在一个数据库中,等项目大了另起数据库的话,只需要在application中配置多数据源即可.