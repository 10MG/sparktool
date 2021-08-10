# Sparktool
## 一、Sparktool简介

<p style="text-indent:2em">
Sparktool是运行Spark SQL的中间件，能够解决Spark SQL传参的难题。刚开始使用Spark时，发现分布式环境下传统的ORM框架无法正常使用，于是诞生了Sqltool；刚开始使用Spark SQL时，发现其不能像JDBC那样传参，于是sqltool的早期版本封装了SQL引擎以及相关的数据加载器。但是，Sqltool是一个通用的动态结构化查询语言（DSQL）解析和执行的框架，并非只能应用于Spark程序中。因此，分离专门应用于Spark程序之上的框架成为必然，Sparktool由此诞生。
</p>

## 二、Sparktool的用途

<p style="text-indent:2em">
Sparktool的用途是运行动态化的、可传参的Spark SQL。所谓“可传参”即执行Spark SQL时可接收参数，而不是写死的SQL代码；所谓“动态化”即执行Spark SQL时最终执行的SQL可根据传入参数不同而不同（参见[DSQL](https://gitee.com/tenmg/dsql)）。这样，Spark SQL传参的难题迎刃而解；同时，对于大多数情况而言，不再需要繁杂的逻辑判断和SQL拼接。
</p>

## 三、Sparktool使用示例

<p style="text-indent:2em">
Sparktool通过SparkDao向外提供API服务，目前SparkDao只有一种实现即DSQLSparkDao构建，DSQLSparkDao需要指定DSQLFactory。sparkDao可以让Spark SQL迸发魔力：
</p>

```
// 构建SparkDao
SparkDao sparkDao = DSQLSparkDao.build(new XMLFileDSQLFactory(basePackages, suffix));

// 如果已经构建了Sqltool的Dao，构建SparkDao时可以重用DSQLFactory
SparkDao sparkDao = DSQLSparkDao.build(dao.getDSQLFactory());

// 使用SparkDao解析执行DSQL从数据库加载数据集
Dataset<Row> dataset1 = sparkDao.load(sparkSession, dbOptions, dsql, params);

// 使用SparkDao将带参数DSQL提交给Spark SQL执行
Dataset<Row> dataset2 = sparkDao.sql(sparkSession, dsql, params);
```


## 四、获取Sparktool

gitee开源地址：https://gitee.com/tenmg/sparktool

github开源地址：https://github.com/10MG/sparktool

maven中央仓库地址：https://mvnrepository.com/artifact/cn.tenmg/sparktool