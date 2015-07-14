import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class VersionCodeUtil {
	private static SAXReader reader = new SAXReader();

	public static AppInfoBean getAppInfoBeanByXML(String xmlInfo, int type) {
		AppInfoBean appInfo = null;
		appInfo = getAppInfoBeanAPK(xmlInfo);
		appInfo = getAppInfoBeanIPA(xmlInfo);
		return appInfo;
	}

	public static AppInfoBean getAppInfoBeanAPK(String xmlInfo) {
		AppInfoBean appInfoBean = null;
		try {
			appInfoBean = new AppInfoBean();
			Document document = reader.read(new File(
					"C:/Users/wjy/Desktop/test.xml"));
			Element node = document.getRootElement();
			appInfoBean.setVersionCode(node.attributeValue("versionCode"));
			appInfoBean.setPackageName(node.attributeValue("package"));
			appInfoBean.setVersionStr(node.attributeValue("versionName"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return appInfoBean;
	}

	public static AppInfoBean getAppInfoBeanIPA(String xmlInfo) {
		AppInfoBean appInfoBean = null;
		try {
			appInfoBean = new AppInfoBean();
			Document document = reader.read(new File(
					"C:/Users/wjy/Desktop/test1.xml"));
			Element node = document.getRootElement();
			Element ele = node.element("dict");
			if (ele != null) {
				List<Element> keys = ele.elements("key");
				List<Element> values = ele.elements("string");
				String CFBundleVersion = "";
				String CFBundleIdentifier = "";
				String CFBundleShortVersionString = "";
				for (int i = 0; i < keys.size(); i++) {
					Element key = keys.get(i);
					String keyValue = key.getStringValue();
					String value_str = values.get(i).getStringValue();
					if (keyValue.equalsIgnoreCase("CFBundleVersion")) {
						CFBundleIdentifier = value_str;
					} else if (keyValue.equalsIgnoreCase("CFBundleIdentifier")) {
						CFBundleIdentifier = value_str;
					} else if (keyValue.equalsIgnoreCase("CFBundleShortVersionString")) {
						CFBundleShortVersionString = value_str;
					}
				}
				appInfoBean.setVersionCode(CFBundleVersion);
				appInfoBean.setPackageName(CFBundleIdentifier);
				appInfoBean.setVersionStr(CFBundleIdentifier);
				System.out.println(appInfoBean);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return appInfoBean;
	}

	public static void main(String[] args) {
		// getAppInfoBeanByXML("", 1);
		getAppInfoBeanIPA("");
	}
}
