package cn.tenmg.sparktool;

import java.io.Serializable;
import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Spark数据访问对象
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public interface SparkDao extends Serializable {

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
	public Dataset<Row> load(SparkSession sparkSession, Map<String, String> options, String dsql, Object... params);

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
			Map<String, Object> params);

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
	public Dataset<Row> sql(SparkSession sparkSession, String dsql, Object... params);

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
	public Dataset<Row> sql(SparkSession sparkSession, String dsql, Map<String, Object> params);

}
