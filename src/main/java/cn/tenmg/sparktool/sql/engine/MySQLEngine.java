package cn.tenmg.sparktool.sql.engine;

/**
 * MySQL方言的SQL引擎
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class MySQLEngine extends BasicSQLEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3906596407170164697L;

	private static final MySQLEngine INSTANCE = new MySQLEngine();

	private MySQLEngine() {
		super();
	}

	public static final MySQLEngine getInstance() {
		return INSTANCE;
	}

}
