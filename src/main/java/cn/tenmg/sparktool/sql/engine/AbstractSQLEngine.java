package cn.tenmg.sparktool.sql.engine;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.tenmg.dsl.utils.DSLUtils;
import cn.tenmg.dsl.utils.NamedScriptUtils;
import cn.tenmg.dsl.utils.StringUtils;
import cn.tenmg.dsql.NamedSQL;
import cn.tenmg.dsql.utils.CollectionUtils;
import cn.tenmg.sparktool.sql.SQLEngine;

/**
 * 抽象SQL引擎
 * 
 * @author 赵伟均 wjzhao@aliyun.com
 *
 */
public abstract class AbstractSQLEngine implements SQLEngine {

	/**
	 * 
	 */
	private static final long serialVersionUID = -867998927447365357L;

	private static final String COMMA_SPACE = ", ";

	abstract String parse(Date date);

	@Override
	public String parse(NamedSQL namedSQL) {
		String source = namedSQL.getScript();
		if (StringUtils.isBlank(source)) {
			return source;
		}
		Map<String, Object> params = namedSQL.getParams();
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		int len = source.length(), i = 0, backslashes = 0;
		char a = DSLUtils.BLANK_SPACE, b = DSLUtils.BLANK_SPACE;
		boolean isString = false;// 是否在字符串区域
		boolean isParam = false;// 是否在参数区域
		StringBuilder sb = new StringBuilder(), paramName = new StringBuilder();
		while (i < len) {
			char c = source.charAt(i);
			if (isString) {
				if (c == DSLUtils.BACKSLASH) {
					backslashes++;
				} else {
					if (NamedScriptUtils.isStringEnd(a, b, c, backslashes)) {// 字符串区域结束
						isString = false;
					}
					backslashes = 0;
				}
				sb.append(c);
			} else {
				if (c == NamedScriptUtils.SINGLE_QUOTATION_MARK) {// 字符串区域开始
					isString = true;
					sb.append(c);
				} else if (isParam) {// 处于参数区域
					if (NamedScriptUtils.isParamChar(c)) {
						paramName.append(c);
					} else {
						isParam = false;// 参数区域结束
						String name = paramName.toString();
						parseParam(sb, name, params.get(name));
						sb.append(c);
					}
				} else {
					if (NamedScriptUtils.isParamBegin(a, b, c)) {
						isParam = true;// 参数区域开始
						paramName.setLength(0);
						paramName.append(c);
						sb.setLength(sb.length() - 1);// 去掉 “:”
					} else {
						sb.append(c);
					}
				}
			}
			a = b;
			b = c;
			i++;
		}
		if (isParam) {
			String name = paramName.toString();
			parseParam(sb, name, params.get(name));
		}
		return sb.toString();
	}

	private void parseParam(StringBuilder sb, String name, Object value) {
		if (value == null) {
			appendNull(sb);
		} else {
			if (value instanceof Collection<?>) {
				Collection<?> collection = (Collection<?>) value;
				if (CollectionUtils.isEmpty(collection)) {
					appendNull(sb);
				} else {
					boolean flag = false;
					for (Iterator<?> it = collection.iterator(); it.hasNext();) {
						if (flag) {
							sb.append(COMMA_SPACE);
						} else {
							flag = true;
						}
						append(sb, it.next());
					}
				}
			} else if (value instanceof Object[]) {
				Object[] objects = (Object[]) value;
				if (objects.length == 0) {
					appendNull(sb);
				} else {
					for (int j = 0; j < objects.length; j++) {
						if (j > 0) {
							sb.append(COMMA_SPACE);
						}
						append(sb, objects[j]);
					}
				}
			} else {
				append(sb, value);
			}
		}
	}

	private void append(StringBuilder sb, Object value) {
		if (value instanceof String || value instanceof char[]) {
			appendString(sb, (String) value);
		} else if (value instanceof Date) {
			sb.append(parse((Date) value));
		} else if (value instanceof Calendar) {
			Date date = ((Calendar) value).getTime();
			if (date == null) {
				appendNull(sb);
			} else {
				sb.append(parse(date));
			}
		} else {
			sb.append(value.toString());
		}
	}

	private static final void appendNull(StringBuilder sb) {
		sb.append("NULL");
	}

	private static final void appendString(StringBuilder sb, String value) {
		sb.append("'").append(value).append("'");
	}

}
