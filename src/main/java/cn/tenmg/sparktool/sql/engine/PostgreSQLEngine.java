package cn.tenmg.sparktool.sql.engine;

/**
 * PostgreSQL方言的SQL引擎
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public class PostgreSQLEngine extends BasicSQLEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5846436770042482606L;

	private PostgreSQLEngine() {
		super();
	}

	private static class InstanceHolder {
		private static final PostgreSQLEngine INSTANCE = new PostgreSQLEngine();
	}

	public static final PostgreSQLEngine getInstance() {
		return InstanceHolder.INSTANCE;
	}

}
