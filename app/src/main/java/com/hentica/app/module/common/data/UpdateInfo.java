package com.hentica.app.module.common.data;

public class UpdateInfo {
	/** 是否需要更新 */
	private String NeedUpdate;
	/** 版本号 */
	private String VersionCode;
	/** 版本名 */
	private String VersionName;
	/** 更新网址 */
	private String Desc;
	/**  */
	private String DownloadUrl;

	/** 是否需要更新 */
	public String getNeedUpdate() {
		return NeedUpdate;
	}

	/** 是否需要更新 */
	public void setNeedUpdate(String NeedUpdate) {
		this.NeedUpdate = NeedUpdate;
	}

	/** 版本号 */
	public String getVersionCode() {
		return VersionCode;
	}

	/** 版本号 */
	public void setVersionCode(String VersionCode) {
		this.VersionCode = VersionCode;
	}

	/** 版本名 */
	public String getVersionName() {
		return VersionName;
	}

	/** 版本名 */
	public void setVersionName(String VersionName) {
		this.VersionName = VersionName;
	}

	/** 更新网址 */
	public String getDesc() {
		return Desc;
	}

	/** 更新网址 */
	public void setDesc(String Desc) {
		this.Desc = Desc;
	}

	/**  */
	public String getDownloadUrl() {
		return DownloadUrl;
	}

	/**  */
	public void setDownloadUrl(String DownloadUrl) {
		this.DownloadUrl = DownloadUrl;
	}

}
