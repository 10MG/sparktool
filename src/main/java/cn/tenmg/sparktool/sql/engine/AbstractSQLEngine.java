package cn.tenmg.sparktool.sql.engine;

import java.util.Calendar;
import java.util.Date;

import cn.tenmg.dsl.parser.PlaintextParamsParser;
import cn.tenmg.dsl.utils.DSLUtils;
import cn.tenmg.dsql.NamedSQL;
import cn.tenmg.sparktool.sql.SQLEngine;

/**
 * 抽象SQL引擎
 * 
 * @author June wjzhao@aliyun.com
 *
 */
public abstract class AbstractSQLEngine extends PlaintextParamsParser implements SQLEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -867998927447365357L;

	abstract String parse(Date date);

	@Override
	protected String convert(Object value) {
		if (value instanceof Date) {
			return parse((Date) value);
		} else if (value instanceof Calendar) {
			Date date = ((Calendar) value).getTime();
			if (date == null) {
				return "null";
			} else {
				return parse(date);
			}
		} else {
			return value.toString();
		}
	}

	@Override
	public String parse(NamedSQL namedSQL) {
		return DSLUtils.toScript(namedSQL.getScript(), namedSQL.getParams(), this).getValue();
	}

}
