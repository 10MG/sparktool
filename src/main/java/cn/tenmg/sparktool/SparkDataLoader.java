package cn.tenmg.sparktool;

import java.io.Serializable;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import cn.tenmg.sparktool.sql.engine.SparkSQLEngine;
import cn.tenmg.sparktool.utils.SQLEngineUtils;
import cn.tenmg.sqltool.DSQLFactory;
import cn.tenmg.sqltool.dsql.NamedSQL;

/**
 * Spark数据加载器
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public class SparkDataLoader implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3061843065600681110L;

	private DSQLFactory DSQLFactory;

	public SparkDataLoader(DSQLFactory DSQLFactory) {
		super();
		this.DSQLFactory = DSQLFactory;
	}

	/**
	 * 从数据库加载数据集
	 * 
	 * @param sparkSession
	 *            spark会话
	 * @param options
	 *            数据库配置项
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数集
	 * @return 返回加载的数据集
	 */
	public Dataset<Row> load(SparkSession sparkSession, Map<String, String> options, String dsql, Object... params) {
		return load(sparkSession, options, DSQLFactory.parse(dsql, params));
	}

	/**
	 * 从数据库加载数据集
	 * 
	 * @param sparkSession
	 *            Spark会话
	 * @param options
	 *            数据库配置项
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数集
	 * @return 返回加载的数据集
	 */
	public Dataset<Row> load(SparkSession sparkSession, Map<String, String> options, String dsql,
			Map<String, Object> params) {
		return load(sparkSession, options, DSQLFactory.parse(dsql, params));
	}

	/**
	 * 执行SparkSQL查询
	 * 
	 * @param sparkSession
	 *            spark会话
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数集
	 * @return 返回查询的数据集
	 */
	public Dataset<Row> sql(SparkSession sparkSession, String dsql, Object... params) {
		return sql(sparkSession, DSQLFactory.parse(dsql, params));
	}

	/**
	 * 执行SparkSQL查询
	 * 
	 * @param sparkSession
	 *            spark会话
	 * @param dsql
	 *            动态结构化查询语言
	 * @param params
	 *            查询参数集
	 * @return 返回查询的数据集
	 */
	public Dataset<Row> sql(SparkSession sparkSession, String dsql, Map<String, Object> params) {
		return sql(sparkSession, DSQLFactory.parse(dsql, params));
	}

	private Dataset<Row> load(SparkSession sparkSession, Map<String, String> options, NamedSQL namedSQL) {
		return sparkSession.sqlContext().read().options(options)
				.option("query", SQLEngineUtils.getSqlEngine(options.get("url")).parse(namedSQL)).format("jdbc").load();
	}

	private Dataset<Row> sql(SparkSession sparkSession, NamedSQL namedSQL) {
		return sparkSession.sqlContext().sql(SparkSQLEngine.getInstance().parse(namedSQL));
	}
}