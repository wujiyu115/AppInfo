import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class AppInfoUtil {
	private static SAXReader reader = new SAXReader();

	 

	public static AppInfoBean getAppInfoBeanAPK(String xmlInfo) {
		AppInfoBean appInfoBean = null;
		try {
			appInfoBean = new AppInfoBean();
			appInfoBean.setVersionStrLabel("versionName:");
			appInfoBean.setVersionCodeLabel("versionCode:");
			Document document = reader.read(new StringReader(xmlInfo));
//			Document document = reader.read(new File(
//					"C:/Users/wjy/Desktop/test.xml"));
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
			appInfoBean.setVersionStrLabel("CFBundleShortVersionString:");
			appInfoBean.setVersionCodeLabel("CFBundleVersion:");
			Document document = reader.read(new StringReader(xmlInfo));
//			Document document = reader.read(new File(
//					"C:/Users/Administrator/Desktop/test1.xml"));
			Element node = document.getRootElement();
			Element ele = node.element("dict");
			if (ele != null) {
				Iterator<Element> nodeIt = ele.elementIterator();
				HashMap<String, String> values = new HashMap<String, String>();
				int count = 1;
				String lastNodeName = "";
				while (nodeIt.hasNext()) {
					Element child = nodeIt.next();
					String nodeName = child.getName();
					String nodeValue = child.getStringValue();
					if (count % 2 == 0) {
//						System.out.println(lastNodeName+","+ nodeValue);
						values.put(lastNodeName, nodeValue);
					}
					if (nodeName.equalsIgnoreCase("key")) {
						lastNodeName = nodeValue;
					}
					count++;
				}
				String CFBundleVersion = values.get("CFBundleVersion");
				String CFBundleIdentifier = values.get("CFBundleIdentifier");
				String CFBundleShortVersionString = values
						.get("CFBundleShortVersionString");

				appInfoBean.setVersionCode(CFBundleVersion);
				appInfoBean.setPackageName(CFBundleIdentifier);
				appInfoBean.setVersionStr(CFBundleShortVersionString);
//				System.out.println(appInfoBean);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return appInfoBean;
	}


}
