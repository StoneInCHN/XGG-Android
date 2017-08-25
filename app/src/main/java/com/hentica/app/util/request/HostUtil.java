package com.hentica.app.util.request;


/** 域名拼接工具，只负责拼接域名和路径部分，不附带get参数 */
public class HostUtil {

	/**
	 * 拼接成最终地址
	 * 
	 * @param base
	 *            基本地址
	 * @param path
	 *            相对路径
	 * @return
	 */
	public static String splicelHost(String base, String path) {

		// 保证不为null
		if (base == null) {

			base = "";
		}

		// 末尾添加 /
		if (!base.endsWith("/")) {

			base += "/";
		}

		// 若相对路径不为空
		if (path != null && !(path.length() == 0)) {

			base += path;
		}

		return base;
	}
}
