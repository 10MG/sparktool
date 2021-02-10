package cn.tenmg.sparktool.sql.engine;

/**
 * Spark SQL 方言的SQL引擎
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public class SparkSQLEngine extends BasicSQLEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4709716690186443192L;

	private SparkSQLEngine() {

	}

	private static class InstanceHolder {
		private static final SparkSQLEngine INSTANCE = new SparkSQLEngine();
	}

	public static final SparkSQLEngine getInstance() {
		return InstanceHolder.INSTANCE;
	}

}