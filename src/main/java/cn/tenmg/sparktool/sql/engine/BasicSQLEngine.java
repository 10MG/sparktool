package cn.tenmg.sparktool.sql.engine;

import java.util.Date;

import cn.tenmg.dsql.utils.DateUtils;

/**
 * 基本SQL引擎
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public class BasicSQLEngine extends AbstractSQLEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3122690010713609511L;

	private static final String CALENDAR_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	@Override
	String parse(Date date) {
		return "'" + DateUtils.format(date, CALENDAR_FORMAT) + "'";
	}

}
