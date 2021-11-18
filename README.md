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

## DSQL

[DSQL](https://gitee.com/tenmg/dsql)的全称是动态结构化查询语言(Dynamic Structured Query Language)，它使用特殊字符`#[]`标记动态片段。当实际执行查询时，判断实际传入参数值是否为空（`null`）决定是否保留该片段，同时自动去除`#[]`。以此来避免程序员手动拼接繁杂的SQL，使得程序员能从繁杂的业务逻辑中解脱出来。

### 例子

假设有如下动态查询语句：

```
SELECT
  *
FROM STAFF_INFO S
WHERE S.STATUS = 'VALID'
#[AND S.STAFF_ID = :staffId]
#[AND S.STAFF_NAME LIKE :staffName]
```

参数staffId为空（`null`），而staffName为非空（非`null`）时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE S.STATUS = 'VALID'
 AND S.STAFF_NAME LIKE :staffName
```

相反，参数staffName为空（`null`），而staffId为非空（非`null`）时，实际执行的语句为：


```
SELECT
   *
 FROM STAFF_INFO S
 WHERE S.STATUS = 'VALID'
 AND S.STAFF_ID = :staffId
```

或者，参数staffId、staffName均为空（`null`）时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE S.STATUS = 'VALID'
```

最后，参数staffId、staffName均为非空（非`null`）时，实际执行的语句为：

```
SELECT
   *
 FROM STAFF_INFO S
 WHERE S.STATUS = 'VALID'
 AND S.STAFF_ID = :staffId
 AND S.STAFF_NAME LIKE :staffName
```

通过上面这个小例子，我们看到了动态结构化查询语言（DSQL）的魔力。这种魔力的来源是巧妙的运用了一个值：空(`null`)，因为该值往往在结构化查询语言(SQL)中很少用到，而且即便使用也是往往作为特殊的常量使用，比如：
```
NVL(EMAIL,'无')
```
和
```
WHERE EMAIL IS NOT NULL
```
等等。更多有关[DSQL](https://gitee.com/tenmg/dsql)的介绍，详见[https://gitee.com/tenmg/dsql](https://gitee.com/tenmg/dsql)

## 相关链接

DSQL开源地址：https://gitee.com/tenmg/dsql

DSL开源地址：https://gitee.com/tenmg/dsl