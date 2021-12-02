package cn.tenmg.sparktool.sql.engine;

/**
 * PostgreSQL方言的SQL引擎
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class PostgreSQLEngine extends BasicSQLEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5846436770042482606L;

	private static final PostgreSQLEngine INSTANCE = new PostgreSQLEngine();

	private PostgreSQLEngine() {
		super();
	}

	public static final PostgreSQLEngine getInstance() {
		return INSTANCE;
	}

}
