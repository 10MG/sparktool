# Sparktool
## 简介

Sparktool是运行Spark SQL的中间件，它能够运行动态化的、可传参的Spark SQL，解决Spark SQL传参的难题。所谓“可传参”即执行Spark SQL时可接收参数，而不是写死的SQL代码；所谓“动态化”即执行Spark SQL时最终执行的SQL可根据传入参数不同而不同（参见[DSQL](https://gitee.com/tenmg/dsql)）。使用Sparktool，开发者不再需要繁杂的逻辑判断和SQL拼接。

## 起步

### 1. 添加依赖

如果采用Maven构建项目，可以通过在pom.xml中添加如下配置引入Sqltool。其中，${sparktool.version}为版本号，可定义属性或直接使用版本号替换。

```
<!-- https://mvnrepository.com/artifact/cn.tenmg/sparktool -->
<dependency>
    <groupId>cn.tenmg</groupId>
    <artifactId>sparktool</artifactId>
    <version>${sparktool.version}</version>
</dependency>
```
### 2. 调用API

Sparktool通过SparkDao向外提供API服务，它可以让Spark SQL迸发魔力。目前SparkDao只有一种实现即DSQLSparkDao，构建DSQLSparkDao需要指定DSQLFactory：

```
// 构建SparkDao
SparkDao sparkDao = DSQLSparkDao.build(new XMLFileDSQLFactory(basePackages, suffix));

// 如果已经构建了Sqltool的Dao，构建SparkDao时可以重用DSQLFactory
SparkDao sparkDao = DSQLSparkDao.build(dao.getDSQLFactory());

// 使用SparkDao解析执行DSQL从数据库加载数据集
Dataset<Row> dataset1 = sparkDao.load(sparkSession, dbOptions, dsqlId, params);

// 使用SparkDao将带参数DSQL提交给Spark SQL执行
Dataset<Row> dataset2 = sparkDao.sql(sparkSession, plainDSQLText, params);
```

## 相关链接

DSQL开源地址：https://gitee.com/tenmg/dsql

DSL开源地址：https://gitee.com/tenmg/dsl