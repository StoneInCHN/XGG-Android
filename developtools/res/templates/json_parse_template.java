	/** 解析{$CommentDes}列表 */
	public static List<{$ClassName}> parse{$ClassName}s(String json) {

		Type listType = new TypeToken<List<{$ClassName}>>() {
		}.getType();

		try {
			Gson gson = new Gson();
			return gson.fromJson(json, listType);

		} catch (Exception e) {

			CrashHandler.getInstance().handleException("JsonListParseError_{$ClassName}", e);
			e.printStackTrace();
		}
		return null;
	}
