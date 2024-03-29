package cn.tenmg.sparktool.sql.engine;

import java.sql.Timestamp;
import java.util.Date;

import cn.tenmg.dsql.utils.DateUtils;

/**
 * Oracle方言的SQL引擎
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class OracleEngine extends AbstractSQLEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6048522993125955852L;

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss", TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	private static final String DATE_PATTERN = "YYYY-MM-DD HH24:MI:SS", TIMESTAMP_PATTERN = "YYYY-MM-DD HH24:MI:SS.FF6";

	private static final OracleEngine INSTANCE = new OracleEngine();

	private OracleEngine() {
		super();
	}

	public static final OracleEngine getInstance() {
		return INSTANCE;
	}

	@Override
	String parse(Date date) {
		if (date instanceof Timestamp) {
			return "TO_TIMESTAMP('".concat(DateUtils.format(date, TIMESTAMP_FORMAT)).concat("', '")
					.concat(TIMESTAMP_PATTERN).concat("')");
		}
		return "TO_DATE('".concat(DateUtils.format(date, DATE_FORMAT)).concat("', '").concat(DATE_PATTERN).concat("')");
	}

}
