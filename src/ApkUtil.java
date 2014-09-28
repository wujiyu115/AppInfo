import android.content.res.AXmlResourceParser;
import android.util.TypedValue;

public class ApkUtil {
        public static String ANDROID_MAINFEST_XML = "AndroidManifest.xml";

        public static String getZipFileContent(String zipFile, String findFile,
                        boolean regexp) {
                try {
                        if (isMainFestXML(findFile)) {
                                try {
                                        AXmlResourceParser parser = FileUtil
                                                        .getAXMLInfo(zipFile,
                                                                        findFile);
                                        return getXML(parser);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }

                        } else {
                                return FileUtil.readZipFileString(zipFile,
                                                findFile, regexp);
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }

        public static boolean isMainFestXML(String findFile) {
                return FileUtil.matchFile(findFile, ANDROID_MAINFEST_XML, false);
        }

        private static final float[] RADIX_MULTS = { 0.0039063F,
                        3.051758E-005F, 1.192093E-007F, 4.656613E-010F };

        private static final String[] DIMENSION_UNITS = { "px", "dip", "sp",
                        "pt", "in", "mm", "", "" };

        private static final String[] FRACTION_UNITS = { "%", "%p", "", "", "",
                        "", "", "" };

        public static String getXML(AXmlResourceParser parser) {
                StringBuffer resultXML = new StringBuffer();
                try {
                        StringBuilder indent = new StringBuilder(10);
                        while (true) {
                                int type = parser.next();
                                if (type == 1) {
                                        break;
                                }
                                switch (type) {
                                        case 0:
                                                resultXML.append(formatItem(
                                                                "<?xml version=\"1.0\" encoding=\"utf-8\"?>",
                                                                new Object[0]));
                                                break;
                                        case 2:
                                                resultXML.append(formatItem(
                                                                "%s<%s%s",
                                                                new Object[] {
                                                                                indent,
                                                                                getNamespacePrefix(parser
                                                                                                .getPrefix()),
                                                                                parser.getName() }));
                                                indent.append("\t");

                                                int namespaceCountBefore = parser
                                                                .getNamespaceCount(parser
                                                                                .getDepth() - 1);
                                                int namespaceCount = parser
                                                                .getNamespaceCount(parser
                                                                                .getDepth());
                                                for (int i = namespaceCountBefore; i != namespaceCount; i++) {
                                                        resultXML.append(formatItem(
                                                                        "%sxmlns:%s=\"%s\"",
                                                                        new Object[] {
                                                                                        indent,
                                                                                        parser.getNamespacePrefix(i),
                                                                                        parser.getNamespaceUri(i) }));
                                                }

                                                for (int i = 0; i != parser
                                                                .getAttributeCount(); i++) {
                                                        resultXML.append(formatItem(
                                                                        "%s%s%s=\"%s\"",
                                                                        new Object[] {
                                                                                        indent,
                                                                                        getNamespacePrefix(parser
                                                                                                        .getAttributePrefix(i)),
                                                                                        parser.getAttributeName(i),
                                                                                        getAttributeValue(
                                                                                                        parser,
                                                                                                        i) }));
                                                }
                                                resultXML.append(formatItem(
                                                                "%s>",
                                                                new Object[] { indent }));
                                                break;
                                        case 3:
                                                indent.setLength(indent
                                                                .length()
                                                                - "\t".length());
                                                resultXML.append(formatItem(
                                                                "%s</%s%s>",
                                                                new Object[] {
                                                                                indent,
                                                                                getNamespacePrefix(parser
                                                                                                .getPrefix()),
                                                                                parser.getName() }));
                                                break;
                                        case 4:
                                                resultXML.append(formatItem(
                                                                "%s%s",
                                                                new Object[] {
                                                                                indent,
                                                                                parser.getText() }));
                                        case 1:
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return resultXML.toString();
        }

        private static String getNamespacePrefix(String prefix) {
                if ((prefix == null) || (prefix.length() == 0)) {
                        return "";
                }
                return prefix + ":";
        }

        private static String getAttributeValue(AXmlResourceParser parser,
                        int index) {
                int type = parser.getAttributeValueType(index);
                int data = parser.getAttributeValueData(index);
                if (type == 3) {
                        return parser.getAttributeValue(index);
                }
                if (type == 2) {
                        return String.format("?%s%08X",
                                        new Object[] { getPackage(data),
                                                        Integer.valueOf(data) });
                }
                if (type == 1) {
                        return String.format("@%s%08X",
                                        new Object[] { getPackage(data),
                                                        Integer.valueOf(data) });
                }
                if (type == 4) {
                        return String.valueOf(Float.intBitsToFloat(data));
                }
                if (type == 17) {
                        return String.format("0x%08X",
                                        new Object[] { Integer.valueOf(data) });
                }
                if (type == 18) {
                        return data != 0 ? "true" : "false";
                }
                if (type == 5) {
                        return Float.toString(complexToFloat(data))
                                        + DIMENSION_UNITS[(data & 0xF)];
                }
                if (type == 6) {
                        return Float.toString(complexToFloat(data))
                                        + FRACTION_UNITS[(data & 0xF)];
                }
                if ((type >= 28) && (type <= 31)) {
                        return String.format("#%08X",
                                        new Object[] { Integer.valueOf(data) });
                }
                if ((type >= 16) && (type <= 31)) {
                        return String.valueOf(data);
                }
                return String.format("<0x%X, type 0x%02X>", new Object[] {
                                Integer.valueOf(data), Integer.valueOf(type) });
        }

        private static String getPackage(int id) {
                if (id >>> 24 == 1) {
                        return "android:";
                }
                return "";
        }

        private static String formatItem(String format, Object[] arguments) {
                return String.format(format, arguments) + "\n";
        }

        public static float complexToFloat(int complex) {
                return (complex & 0xFFFFFF00)
                                * RADIX_MULTS[(complex >> 4 & 0x3)];
        }
}
