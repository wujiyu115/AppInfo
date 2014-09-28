import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;

public class PListUtil {
        public static String INFO_PLIST = "Payload/.*\\.app/Info\\.plist";

        public static String getZipFileContent(String zipFile, String findFile,
                        boolean regexp) {
                try {
                        if (isInfoPlist(findFile)) {
                                byte[] bytes = FileUtil.readZipFileByte(zipFile,
                                                findFile, regexp);
                                if (bytes != null) {
                                        NSDictionary rootDict = (NSDictionary) PropertyListParser
                                                        .parse(bytes);
                                        return rootDict.toXMLPropertyList();
                                }
                        } else {
                                return FileUtil. readZipFileString(zipFile, findFile, regexp);
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        public static boolean isInfoPlist(String findFile) {
                return  FileUtil.matchFile(findFile, INFO_PLIST, false)
                                ||  FileUtil.matchFile(findFile, INFO_PLIST, true);
        }

      
}
