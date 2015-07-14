import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import android.content.res.AXmlResourceParser;

public class FileUtil {
	public static String IPA_STUFFIX = ".ipa";
	public static String APK_STUFFIX = ".apk";

	public static String getStuffix(String path) {
		if (path == null)
			return null;
		return path.substring(path.lastIndexOf("."));
	}

	public static String getSynatx(String path) {
		if (PListUtil.isInfoPlist(path) || ApkUtil.isMainFestXML(path)) {
			return SyntaxConstants.SYNTAX_STYLE_XML;
		} else {
			String stuffix = getStuffix(path);
			String syntax = stuffixMaps.get(stuffix);
			if (syntax == null)
				return SyntaxConstants.SYNTAX_STYLE_NONE;
			return syntax;
		}
	}

	public static String getOSDefaultXML(String zipFile) {
		String fileStuffix = getStuffix(zipFile);
		if (fileStuffix.equals(IPA_STUFFIX)) {
			return PListUtil.INFO_PLIST;
		} else if (fileStuffix.equals(APK_STUFFIX)) {
			return ApkUtil.ANDROID_MAINFEST_XML;
		}
		return null;
	}

	public static Map<String, String> stuffixMaps = new HashMap<String, String>();

	static {
		stuffixMaps.put(".as", SyntaxConstants.SYNTAX_STYLE_ACTIONSCRIPT);
		stuffixMaps.put(".asm", SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86);
		stuffixMaps.put(".c", SyntaxConstants.SYNTAX_STYLE_C);
		stuffixMaps.put(".clojure", SyntaxConstants.SYNTAX_STYLE_CLOJURE);
		stuffixMaps.put(".cpp", SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
		stuffixMaps.put(".cs", SyntaxConstants.SYNTAX_STYLE_CSHARP);
		stuffixMaps.put(".css", SyntaxConstants.SYNTAX_STYLE_CSS);
		stuffixMaps.put(".dpr", SyntaxConstants.SYNTAX_STYLE_DELPHI);
		stuffixMaps.put(".dtd", SyntaxConstants.SYNTAX_STYLE_DTD);
		stuffixMaps.put(".groovy", SyntaxConstants.SYNTAX_STYLE_GROOVY);
		stuffixMaps.put(".htaccess", SyntaxConstants.SYNTAX_STYLE_HTACCESS);
		stuffixMaps.put(".html", SyntaxConstants.SYNTAX_STYLE_HTML);
		stuffixMaps.put(".htm", SyntaxConstants.SYNTAX_STYLE_HTML);
		stuffixMaps.put(".java", SyntaxConstants.SYNTAX_STYLE_JAVA);
		stuffixMaps.put(".js", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
		stuffixMaps.put(".json", SyntaxConstants.SYNTAX_STYLE_JSON);
		stuffixMaps.put(".jsp", SyntaxConstants.SYNTAX_STYLE_JSP);
		stuffixMaps.put(".latex", SyntaxConstants.SYNTAX_STYLE_LATEX);
		stuffixMaps.put(".lisp", SyntaxConstants.SYNTAX_STYLE_LISP);
		stuffixMaps.put(".cl", SyntaxConstants.SYNTAX_STYLE_LISP);
		stuffixMaps.put(".lua", SyntaxConstants.SYNTAX_STYLE_LUA);
		stuffixMaps.put(".make", SyntaxConstants.SYNTAX_STYLE_MAKEFILE);
		stuffixMaps.put(".mxml", SyntaxConstants.SYNTAX_STYLE_MXML);
		stuffixMaps.put(".nsi ", SyntaxConstants.SYNTAX_STYLE_NSIS);
		stuffixMaps.put(".prl", SyntaxConstants.SYNTAX_STYLE_PERL);
		stuffixMaps.put(".php", SyntaxConstants.SYNTAX_STYLE_PHP);
		stuffixMaps.put(".properties",
				SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE);
		stuffixMaps.put(".py", SyntaxConstants.SYNTAX_STYLE_PYTHON);
		stuffixMaps.put(".rb", SyntaxConstants.SYNTAX_STYLE_RUBY);
		stuffixMaps.put(".scala", SyntaxConstants.SYNTAX_STYLE_SCALA);
		stuffixMaps.put(".sql", SyntaxConstants.SYNTAX_STYLE_SQL);
		stuffixMaps.put(".sh", SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL);
		stuffixMaps.put(".vb", SyntaxConstants.SYNTAX_STYLE_VISUAL_BASIC);
		stuffixMaps.put(".bat", SyntaxConstants.SYNTAX_STYLE_WINDOWS_BATCH);
		stuffixMaps.put(".xml", SyntaxConstants.SYNTAX_STYLE_XML);

	}

	public static byte[] readZipFileByte(String zipFile, String findEntry,
			boolean regexp) throws Exception {
		ZipFile zf = new ZipFile(zipFile);
		InputStream in = new BufferedInputStream(new FileInputStream(zipFile));
		ZipInputStream zin = new ZipInputStream(in);
		ZipEntry ze;
		byte[] datas = null;
		while ((ze = zin.getNextEntry()) != null) {
			if (matchFile(ze.getName(), findEntry, regexp)) {
				InputStream inputStream = zf.getInputStream(ze);
				datas = new byte[inputStream.available()];
				inputStream.read(datas);
				inputStream.close();
				break;
			}
		}
		zin.closeEntry();
		return datas;
	}

	public static AXmlResourceParser getAXMLInfo(String zipFile,
			String findEntry) throws Exception {
		ZipFile zf = new ZipFile(zipFile);
		InputStream in = new BufferedInputStream(new FileInputStream(zipFile));
		ZipInputStream zin = new ZipInputStream(in);
		ZipEntry ze;
		AXmlResourceParser parser = new AXmlResourceParser();
		while ((ze = zin.getNextEntry()) != null) {
			if (matchFile(ze.getName(), findEntry, false)) {
				InputStream inputStream = zf.getInputStream(ze);
				parser.open(inputStream);
				break;
			}
		}
		zin.closeEntry();
		return parser;
	}

	public static String readZipFileString(String zipFile, String findEntry,
			boolean regexp) throws Exception {
		ZipFile zf = new ZipFile(zipFile);
		InputStream in = new BufferedInputStream(new FileInputStream(zipFile));
		ZipInputStream zin = new ZipInputStream(in);
		ZipEntry ze;
		StringBuffer datas = new StringBuffer();
		while ((ze = zin.getNextEntry()) != null) {
			if (matchFile(ze.getName(), findEntry, regexp)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						zf.getInputStream(ze)));
				String line;
				while ((line = br.readLine()) != null) {
					datas.append(line);
				}
				br.close();
			}
		}
		zin.closeEntry();
		return datas.toString();
	}

	public static boolean matchFile(String entryName, String findEntry,
			boolean regexp) {
		if (regexp) {
			return entryName.matches(findEntry);
		}
		return entryName.equals(findEntry);
	}

	public static String getFileData(String zipFile, String findFile,
			boolean regexp) {
		String fileStuffix = getStuffix(zipFile);
		String info = "";
		if (fileStuffix.equals(IPA_STUFFIX)) {
			if (findFile == null || findFile.equals("")) {
				findFile = PListUtil.INFO_PLIST;
			}
			info = PListUtil.getZipFileContent(zipFile, findFile, regexp);
		} else if (fileStuffix.equals(APK_STUFFIX)) {
			if (findFile == null || findFile.equals("")) {
				findFile = ApkUtil.ANDROID_MAINFEST_XML;
			}
			info = ApkUtil.getZipFileContent(zipFile, findFile, regexp);
		}
		return info;
	}

	public static AppInfoBean getAppInfoBean(String zipFile, String findFile,
			boolean regexp) {
		AppInfoBean appInfo = null;
		String fileStuffix = getStuffix(zipFile);
		String info = "";
		if (fileStuffix.equals(IPA_STUFFIX)) {
			if (findFile == null || findFile.equals("")) {
				findFile = PListUtil.INFO_PLIST;
			}
			info = PListUtil.getZipFileContent(zipFile, findFile, regexp);
			appInfo = AppInfoUtil.getAppInfoBeanIPA(info);
			appInfo.setXmlInfo(info);
		} else if (fileStuffix.equals(APK_STUFFIX)) {
			if (findFile == null || findFile.equals("")) {
				findFile = ApkUtil.ANDROID_MAINFEST_XML;
			}
			info = ApkUtil.getZipFileContent(zipFile, findFile, regexp);
			appInfo = AppInfoUtil.getAppInfoBeanAPK(info);
			appInfo.setXmlInfo(info);
		}
		return appInfo;
	}
}
