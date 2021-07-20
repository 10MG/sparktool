package cn.tenmg.sparktool.sql.engine;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import cn.tenmg.dsql.utils.DateUtils;

/**
 * SQLServer方言的SQL引擎
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public class SQLServerEngine extends AbstractSQLEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3853385545110009849L;

	private static final String CAST_FN = "CAST('%s' AS %s)", TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.S",
			DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss", DATETIME = "DATETIME", TIME_PATTERN = "HH:mm:ss.S",
			TIME = "TIME";

	private static final SQLServerEngine INSTANCE = new SQLServerEngine();

	private SQLServerEngine() {
		super();
	}

	public static final SQLServerEngine getInstance() {
		return INSTANCE;
	}

	@Override
	String parse(Date date) {
		if (date instanceof Timestamp) {
			return String.format(CAST_FN, DateUtils.format(date, TIMESTAMP_PATTERN), DATETIME);
		} else if (date instanceof Time) {
			return String.format(CAST_FN, DateUtils.format(date, TIME_PATTERN), TIME);
		}
		return String.format(CAST_FN, DateUtils.format(date, DATE_TIME_PATTERN), DATETIME);
	}

}
