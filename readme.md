电商项目服务端开发
前置知识
json 轻量级数据交换格式。
JAVA实体类
 class  Person{
  int age;
  String name;
  boolean  b;
  double d;
  }
json表示单个对象
   {
     "age":20,
     "name":"zhangsan",
     "b":false,
     "d":3.0
    }
json表示集合
  [
    { "age":20,
              "name":"zhangsan",
              "b":false,
              "d":3.0},
    { "age":21,
              "name":"zhangsan",
              "b":false,
              "d":3.0},
    { "age":22,
              "name":"zhangsan",
              "b":false,
              "d":3.0}
  ]
项目需求
前台
购买
  商品->首页、商品列表、商品详情
  购物车->商品添加到购物车、更改购物车中商品数量、删除商品、全选、取消全选、单选、结算
  订单
      下单：订单确认(地址管理)，订单提交
      订单中心：订单列表、订单详情、取消订单
  地址 : crud
  支付: 支付宝
用户体系
   登录
   注册
   修改密码
后台
管理员登录
商品管理： 添加商品、修改、商品上下架
品类管理:查看、添加
订单管理：查看、发货
数据表结构设计
1，用户表（用户名唯一、MD5加密）

create table neuedu_user(
 `id`       int(11)      not null  auto_increment comment '用户id',
 `username`       varchar(50)      not null   comment '用户名',
 `password`       varchar(50)      not null   comment '用户密码,MD5加密',
 `email`       varchar(50)      not null   comment '用户email',
 `phone`       varchar(20)    not null   comment '用户phone',
 `question`      varchar(100)      not null   comment '找回密码问题',
 `answer`      varchar(100)      not null   comment '找回密码答案',
 `role`       int(4)      not null   comment '角色0-管理员,1-普通用户',
 `create_time`       datetime      not null   comment '创建时间',
`update_time`       datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`),
 UNIQUE KEY `user_name_unique` (`username`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
2，类别表（无限层级表结构）

create table neuedu_category(
 `id`       int(11)      not null  auto_increment comment '类别id',
 `parent_id`       int(11)      default null   comment '父类Id,当pareng_id=0,说明是根节点，一级类别',
 `name`       varchar(50)      DEFAULT null   comment '类别名称',
 `status`       tinyint(1)      DEFAULT '1'  comment '类别状态1-正常，2-已废弃',
 `sort_order`       int(4)    DEFAULT null   comment '排序编号，同类展示顺序，数值相等则自然排序',
 `create_time`       datetime      not null   comment '创建时间',
`update_time`       datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=100032 DEFAULT CHARSET=utf8;

 id    name     parent_id       
 1     电子产品      0
 2     手机          1
 3     华为手机      2 
 4     小米手机      2
 5     meta系列      3
 
查询电子产品下所有的子类别？-->递归查询
3,商品表

create table neuedu_product(
`id`       int(11)      not null  auto_increment comment '商品id',
`category_id`       int(11)      not  null   comment '类别id，对应neuedu_category表的主键',
`name`       varchar(100)      not null   comment '商品名称',
`subtitle`       varchar(200)      DEFAULT null   comment '商品副标题',
`main_image`       varchar(500)      DEFAULT null   comment '产品主图，url相对地址',
`sub_images`       text       comment '图片地址，json格式',
`detail`       text        comment '商品详情',
`price`       DECIMAL (20,2)      not NULL   comment '价格，单位-元保留两位小数',
`stock`       int(11)   not NULL   comment '库存数量',
`status`       int(6)    DEFAULT '1'   comment '商品状态，1-在售 2-下架 3-删除',
`create_time`       datetime      not null   comment '创建时间',
`update_time`        datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`)
 )ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;
4,购物车表

create table neuedu_cart(
`id`       int(11)      not null  auto_increment,
`user_id`       int(11)      not  null  ,
`product_id`       int(11)      not  null  ,
`quantity`       int(11)      not null   comment '商品数量',
`checked`       int(11)      DEFAULT null   comment '是否选择，1=已勾选，0=未勾选',
`create_time`       datetime      not null   comment '创建时间',
`update_time`       datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`),
 key `user_id_index`(`user_id`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;
5，订单表

create table neuedu_order(
 `id`       int(11)      not null  auto_increment comment '订单id',
 `user_id`       int(11)      DEFAULT  null  ,
 `order_no`       bigint(20)      DEFAULT  null comment '订单号'  ,
 `shipping_id`       int(11)      DEFAULT  null  ,
 `payment`       decimal(20,2)      DEFAULT  null  comment '实际付款金额，单位元，保留两位小数' ,
 `payment_type`       int(4)      DEFAULT  null comment '支付类型，1-在线支付' ,
 `postage`       int(10)      DEFAULT null   comment '运费，单位是元',
 `status`       int (10)      DEFAULT null   comment '订单状态：0-已取消 10-未付款 20-已付款 40-已发货 50-交易成功 60-交易关闭',
 `payment_time`      datetime     DEFAULT null   comment '支付时间',
 `send_time`       datetime      DEFAULT null   comment '发货时间',
 `end_time`       datetime      DEFAULT null   comment '交易完成时间',
 `close_time`       datetime      DEFAULT null   comment '交易关闭时间',
 `create_time`       datetime      not null   comment '创建时间',
 `update_time`       datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`),
 UNIQUE  KEY `order_no_index` (`order_no`) USING  BTREE
 )ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
6，订单明细表

create table neuedu_order_item(
 `id`       int(11)      not null  auto_increment comment '订单id',
 `user_id`       int(11)      DEFAULT  null  ,
 `order_no`       bigint(20)      DEFAULT  null comment '订单号'  ,
 `product_id`       int(11)      DEFAULT  null  comment '商品id',
 `product_name`       varchar(100)      DEFAULT  null  comment '商品名称' ,
 `product_image`       varchar(500)      DEFAULT  null comment '商品图片地址' ,
 `current_unit_price`       decimal(20,2)      DEFAULT null   comment '生成订单时的商品单价，单位元，保留两位小数',
 `quantity`       int (10)      DEFAULT null   comment '商品数量',
 `total_price`      decimal(20,2)     DEFAULT null   comment '商品总价，单位元，保留两位小数',
 `create_time`       datetime      not null   comment '创建时间',
 `update_time`       datetime      not null   comment '最后一次更新时间',
  PRIMARY KEY(`id`),
  KEY `order_no_index` (`order_no`) USING  BTREE,
  KEY `order_no_user_id_index` (`user_id`,`order_no`) USING  BTREE
  )ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;
7，支付信息表

create table neuedu_payinfo(
`id`       int(11)      not null  auto_increment,
`user_id`       int(11)      DEFAULT  null  ,
`order_no`       bigint(20)      DEFAULT  null comment '订单号'  ,
`pay_platform`       int(10)      DEFAULT null   comment '支付平台 1-支付宝 2-微信',
 `platform_number`       VARCHAR (200)      DEFAULT null   comment '支付宝支付流水号',
 `platform_status`       VARCHAR (20)      DEFAULT null   comment '支付宝支付状态',
`create_time`       datetime      not null   comment '创建时间',
`update_time`       datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`)
 )ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
8，收货地址表

create table neuedu_shopping(
`id`       int(11)      not null  auto_increment,
`user_id`       int(11)      not  null  ,
`receiver_name`       varchar(20)      default   null  COMMENT '收货姓名' ,
`receiver_phone`       varchar(20)      default   null  COMMENT '收货固定电话' ,
`receiver_mobile`       varchar(20)      default   null  COMMENT '收货移动电话' ,
`receiver_province`       varchar(20)      default   null  COMMENT '省份' ,
`receiver_city`       varchar(20)      default   null  COMMENT '城市' ,
`receiver_district`       varchar(20)      default   null  COMMENT '区/县' ,
`receiver_address`       varchar(200)      default   null  COMMENT '详细地址' ,
 `receiver_zip`       varchar(6)      default   null  COMMENT '邮编' ,
`create_time`       datetime      not null   comment '创建时间',
`update_time`       datetime      not null   comment '最后一次更新时间',
 PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
插件
插件一、mybatis-generator插件
1,pom.xml添加插件

    <plugin>
     <groupId>org.mybatis.generator</groupId>
     <artifactId>mybatis-generator-maven-plugin</artifactId>
     <version>1.3.6</version>
     <configuration>
       <verbose>true</verbose>
       <overwrite>true</overwrite>
     </configuration>
    </plugin>
2,pom中添加依赖

      <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <version>5.1.47</version>
         </dependency>
         <dependency>
           <groupId>org.mybatis.generator</groupId>
           <artifactId>mybatis-generator-core</artifactId>
           <version>1.3.5</version>
         </dependency>
3，创建插件生成的配置文件

generatorConfig.xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE generatorConfiguration PUBLIC
           "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
           "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
   <generatorConfiguration>
       <classPathEntry location=""/>
       <context id="context" targetRuntime="MyBatis3Simple">
           <commentGenerator>
               <property name="suppressAllComments" value="false"/>
               <property name="suppressDate" value="true"/>
           </commentGenerator>
           <jdbcConnection userId="" password="" driverClass="" connectionURL=""/>
           <javaTypeResolver>
               <property name="forceBigDecimals" value="false"/>
           </javaTypeResolver>
           <javaModelGenerator targetPackage="" targetProject=".">
               <property name="enableSubPackages" value="false"/>
               <property name="trimStrings" value="true"/>
           </javaModelGenerator>
           <sqlMapGenerator targetPackage="" targetProject=".">
               <property name="enableSubPackages" value="false"/>
           </sqlMapGenerator>
           <javaClientGenerator targetPackage="" type="XMLMAPPER" targetProject=".">
               <property name="enableSubPackages" value="false"/>
           </javaClientGenerator>
           <table schema="" tableName="" enableCountByExample="false" enableDeleteByExample="false"
                  enableSelectByExample="false" enableUpdateByExample="false"/>
       </context>
   </generatorConfiguration>
插件二、mybatis分页插件
1，pom中添加依赖

  <dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>4.1.0</version>
  </dependency>
  <dependency>
    <groupId>com.github.miemiedev</groupId>
    <artifactId>mybatis-paginator</artifactId>
    <version>1.2.17</version>
  </dependency>
  <dependency>
    <groupId>com.github.jsqlparser</groupId>
    <artifactId>jsqlparser</artifactId>
    <version>0.9.4</version>
  </dependency>
2，spring配置文件，sqlSessionFactoryBean中添加分页插件属性

  <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
      <property name="configLocation" value="classpath:mybatis-config.xml"></property>
      <property name="mapperLocations" value="classpath:com/neuedu/mapper/*Mapper.xml" ></property>
     <property name="dataSource" ref="dataSource"></property>
      <!-- 分页插件 -->
      <property name="plugins">
          <array>
              <bean class="com.github.pagehelper.PageHelper">
                  <property name="properties">
                      <value>
                          dialect=mysql
                      </value>
                  </property>
              </bean>
          </array>
      </property>
  </bean>
插件三-接口测试插件-restlet
搭建ssm框架
1,pom.xml
2,添加配置文件 
 spring配置文件
 springmvc配置文件
 mybatis配置文件
 web.xml
3,使用框架 
模块一、用户模块
1，功能介绍
登录
用户名验证
注册
忘记密码
提交问题答案
重置密码
获取用户信息
更新用户信息
退出登录
2，学习目标
 横向越权、纵向越权安全漏洞
 横向越权：攻击者尝试访问与他拥有相同权限的用户的资源
 纵向越权：低级别攻击者尝试访问高级别用户的资源
 MD5明文加密及增加salt值
 Guava缓存的使用
 高服用服务响应对象的设计思想及抽象封装
3，服务端接口返回前端的统一对象
     class ServerResponse<T>{
       int status;//接口返回状态码
       T  data;//封装了接口的返回数据
       String msg;//封装错误信息
      }
4，业务逻辑
4.1登录功能
     step1:参数非空校验
     step2:校验用户名是否存在
     step3:根据用户名和密码查询用户
     step4:返回结果
4.2注册功能
      step1:参数非空校验
      step2:校验用户名是否存在
      step3:校验邮箱是否存在
      step4:调用Dao接口插入用户信息
      step5:返回结果
4.3忘记密码之修改密码功能
4.3.1 根据username查询密保问题
     step1:参数非空校验
     step2:校验username是否存在
     step3:根据username查询密保问题
     step4:返回数据  
4.3.2 提交问题答案
     step1:参数非空校验
     step2:校验答案是否正确
     step3:为防止横向越权，服务端生成forgetToken保存，并将其返回给客户端
     step4:返回结果
4.3.3 重置密码
     step1:参数非空校验
     step2:校验token是否有效
     step3:修改密码
     step4:返回结果
迭代开发-线上部署
    step1:在阿里云服务器上建库、建表
    step2:修改代码中数据库的连接参数
    step3:项目打成war包
    step4:将war包上传到阿里云服务器的tomcat/webapps
    step5:访问测试  
模块二、类别模块
1，功能介绍
    获取节点
    增加节点
    修改名称
    获取分类
    递归子节点ID
2，学习目标
    如何设计及封装无限层级的树状数据结构
    递归算法的设计思想
    如何处理复杂对象重排
    重写hashcode和equals的注意事项
3,业务逻辑
3.1 获取节点
     step1:判断管理员权限
     step2:参数非空校验
     step3:查询子节点
     step4:返回结果   
3.2 增加节点
     step1:判断管理员权限
     step2:参数非空校验
     step3:添加节点
     step4:返回结果 
3.3 修改名称
        step1:判断管理员权限
        step2:参数非空校验
        step3:修改节点名称
        step4:返回结果      
3.4 递归子节点ID
        step1:判断管理员权限
        step2:参数非空校验
        step3:递归查询子节点
        step4:返回结果    
模块三、 商品模块
1，功能介绍
前台功能
            产品搜索          
            动态排序列表
            商品详情 
后台功能
            商品列表
            商品搜索
            图片上传
            富文本上传
            商品详情
            商品上下架
            增加商品    
            更新商品
2，学习目标
FTP服务的对接
SpringMVC文件上传
流读取Properties配置文件
抽象POJO、BO、VO对象之间的转换关系及解决思路
joda-time快速入门
静态块
Mybatis-PageHelper高效准确地分页及动态排序
Mybatis对List遍历的实现方法
Mybatis对Where语句动态拼接
POJO、BO business object、VO view object
POJO、VO
3，业务逻辑
3.1 后台-添加或更新商品
     step1:判断管理员权限
     step2:参数非空校验
     step3:设置商品的主图
     step4：添加或更新商品
     step5：返回结果
3.2 后台-商品上下架
     step1:判断管理员权限
     step2:参数非空校验
     step3:更新商品状态
     step4：返回结果
3.3 后台-查看商品详细
     step1:判断管理员权限
     step2:参数非空校验
     step3:根据商品id查询商品信息Product
     step4：将Product转成ProductDetailVO
     step5：返回结果 
3.4 后台-分页查看商品列表
        step1:判断管理员权限
        step2:参数非空校验
        step3:集成mybatis分页插件
        step4:查询所有商品
        step5：将List<Product>转成List<ProductListVO>
        step6：返回结果          
3.5 后台-商品搜索
        step1:判断管理员权限
        step2:集成mybatis分页插件
        step3:按照商品id或名称模糊查询
        step4:将List<Product>转成List<ProductListVO>
        step5:返回结果   
3.6 后台-图片上传
        step1:图片重命名
        step2:图片保存到应用服务器
        step3:上传到图片服务器
        step4:将应用服务器的图片删除
        step5:返回结果  
3.7 前台-商品详情
        step1:参数校验
        step2:查询商品
        step3:校验商品状态
        step4:商品转成VO
        step5:返回结果          
3.7 前台-搜索商品并动态排序
        step1:参数校验
        step2:根据类别和关键字查询
        step3:集成分页插件
        step4:转成VO
        step5:返回结果           
模块四、购物车模块
学习目标
购物车模块的设计思想
如何封装一个高复用的购物车核心方法
解决浮点型在商业运算中丢失精度的问题