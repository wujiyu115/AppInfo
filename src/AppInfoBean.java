public class AppInfoBean {
	private String packageName;
	private String versionStr;
	private String versionCode;
	private String xmlInfo;
	
	private String versionStrLabel;
	private String versionCodeLabel;

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

	public String getXmlInfo() {
		return xmlInfo;
	}

	public void setXmlInfo(String xmlInfo) {
		this.xmlInfo = xmlInfo;
	}

	public String getVersionStrLabel() {
		return versionStrLabel;
	}

	public void setVersionStrLabel(String versionStrLabel) {
		this.versionStrLabel = versionStrLabel;
	}

	public String getVersionCodeLabel() {
		return versionCodeLabel;
	}

	public void setVersionCodeLabel(String versionCodeLabel) {
		this.versionCodeLabel = versionCodeLabel;
	}

}
