package cn.tenmg.sparktool.dao;

import java.util.Map;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import cn.tenmg.dsql.DSQLFactory;
import cn.tenmg.dsql.NamedSQL;
import cn.tenmg.sparktool.SparkDao;
import cn.tenmg.sparktool.sql.engine.SparkSQLEngine;
import cn.tenmg.sparktool.utils.SQLEngineUtils;

/**
 * 基于DSQL的Spark数据访问对象
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class DSQLSparkDao implements SparkDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4516839414204232733L;

	private DSQLFactory DSQLFactory;

	public DSQLSparkDao(DSQLFactory DSQLFactory) {
		super();
		this.DSQLFactory = DSQLFactory;
	}

	public static DSQLSparkDao build(DSQLFactory DSQLFactory) {
		return new DSQLSparkDao(DSQLFactory);
	}

	@Override
	public Dataset<Row> load(SparkSession sparkSession, Map<String, String> options, String dsql, Object... params) {
		return load(sparkSession, options, DSQLFactory.parse(dsql, params));
	}

	@Override
	public Dataset<Row> load(SparkSession sparkSession, Map<String, String> options, String dsql,
			Map<String, Object> params) {
		return load(sparkSession, options, DSQLFactory.parse(dsql, params));
	}

	@Override
	public Dataset<Row> sql(SparkSession sparkSession, String dsql, Object... params) {
		return sql(sparkSession, DSQLFactory.parse(dsql, params));
	}

	@Override
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
