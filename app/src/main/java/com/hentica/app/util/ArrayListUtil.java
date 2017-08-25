package com.hentica.app.util;

import java.util.Collection;
import java.util.List;

/** 数组工具 */
public class ArrayListUtil {

	/** 对象匹配接口 */
	public interface ObjectMatchAble<T> {

		/** 此对象是否是需要的 */
		public boolean isObjectMatch(T obj);
	}

	/** 取字符串接口 */
	public interface StringGetter<T> {

		/** 把此对象转换为字符串 */
		public String getString(T obj);
	}

	/** 取得首个元素 */
	public static <T> T getFirstObject(List<T> list) {

		return (list != null && !list.isEmpty()) ? list.get(0) : null;
	}

	/** 取得第一个匹配的对象 */
	public static <T> T getFirstObject(List<T> list, ObjectMatchAble<T> matchAble) {

		if (list != null && matchAble != null && !list.isEmpty()) {

			for (T obj : list) {

				if (matchAble.isObjectMatch(obj)) {

					return obj;
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * 把list内容连接起来，以指定字符串分隔
	 * 
	 * @param list
	 *            要连接的内容
	 * @param str
	 *            分隔符
	 * @return
	 */
	public static <T> String join(List<T> list, String str) {

		return join(list, str, null);
	}

	/**
	 * 把list内容连接起来，以指定字符串分隔
	 * 
	 * @param list
	 *            要连接的内容
	 * @param str
	 *            分隔符
	 * @param getter
	 *            把每个list元素转换为字符串
	 * @return
	 */
	public static <T> String join(List<T> list, String str, StringGetter<T> getter) {

		StringBuilder builder = new StringBuilder();

		// 构造默认转换方法
		if (getter == null) {

			getter = new StringGetter<T>() {

				@Override
				public String getString(T obj) {
					return obj != null ? obj.toString() : "null";
				}
			};
		}

		if (list != null) {

			for (int i = 0, size = list.size(); i < size; i++) {

				T obj = list.get(i);

				if (obj != null) {

					// 若不是最后一个元素
					if (i < size - 1) {

						builder.append(getter.getString(obj)).append(str);
					}
					// 若是最后一个元素，不要末尾字符串
					else {
						builder.append(getter.getString(obj));
					}
				}
			}
		}

		return builder.toString();
	}

	/** 若容器是否是空 */
	public static <E> boolean isEmpty(Collection<E> array) {

		return array == null || array.isEmpty();
	}
}
