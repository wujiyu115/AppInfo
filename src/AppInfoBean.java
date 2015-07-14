public class AppInfoBean {
	private String packageName;
	private String versionStr;
	private String versionCode;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersionStr() {
		return versionStr;
	}

	public void setVersionStr(String versionStr) {
		this.versionStr = versionStr;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	
	@Override
	public String toString() {
		return  "packageName: "+packageName +" ,versionStr:"+versionStr+" ,versionCode"+versionCode  ;
	}

}
