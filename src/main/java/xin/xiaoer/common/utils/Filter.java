package xin.xiaoer.common.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public class Filter extends HashMap<String, Object> {

	@Override
	public Object get(Object key) {
		Object value = super.get(key);
		if (value == null || "".equals(value))
			return null;
		if (value instanceof String) {
			if (StringUtility.checkAttackString((String) value))
				return "";
		}
		if (value.getClass().isArray() && ((Object[]) value).length == 0)
			return null;
		if (value instanceof List && ((List) value).size() == 0)
			return null;
		return value;
	}

	public Object get(String key, Class<?> type) {
		Object value = get(key);

		if (value == null || "".equals(value))
			return null;

		if (value instanceof String) {
			if (type.equals(Boolean.class)) {
				if ("true".equals(value))
					return true;
				return false;
			} else if (type.equals(Integer.class)) {
				return Integer.parseInt((String) value);
			} else if (type.isEnum()) {
				try {
					Method method = type.getMethod("valueOf", String.class);
					return method.invoke(type, value);
				} catch (Exception e) {
					return null;
				}
			}
		} else if (type.equals(value.getClass())) {
			return value;
		}
		return value;
	}

	@Override
	public Object put(String key, Object value) {
		if (value == null || "".equals(value))
			return null;
		if (value instanceof String) {
			if (StringUtility.checkAttackString((String) value))
				return super.put(key, "");
		}
		if (value instanceof Object && value.getClass().isArray() && ((Object[]) value).length == 1)
			return super.put(key, ((Object[]) value)[0]);
		return super.put(key, value);
	}

	public Object set(String key, Object value) {
		if (value == null || "".equals(value))
			return null;
		if (value instanceof String) {
			if (StringUtility.checkAttackString((String) value))
				return super.put(key, "");
		}
		return super.put(key, value);
	}
}
