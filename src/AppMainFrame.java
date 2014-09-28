import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.fife.ui.rtextarea.RTextScrollPane;

/***
 * 
 * zip文件,压缩包中要查找的文件,是否是正则匹配,是否显示在UI界面 zipFile,findFile,regexp,showUI
 * */
public class AppMainFrame extends JFrame {
        private XMLRSyntaxTextArea textArea;

        public AppMainFrame() {
                JPanel cp = new JPanel(new BorderLayout());
                textArea = new XMLRSyntaxTextArea(30, 70);
                RTextScrollPane sp = new RTextScrollPane(textArea);
                cp.add(sp);
                setContentPane(cp);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                pack();
                setLocationRelativeTo(null);
        }

        public AppMainFrame(String zipFile, String findFile, boolean regexp) {
                this();
                String info = FileUtil.getFileData(zipFile, findFile, regexp);
                textArea.setText(info);
        }

        public static void main(String[] args) throws Exception {
                if (args != null && args.length == 4) {
                        final String zipFile = args[0];
                        final String findFile = args[1];
                        final boolean regexp = Boolean.parseBoolean(args[2]);
                        final boolean showUI = Boolean.parseBoolean(args[3]);
                        // System.out.println(ipaFile);
                        // System.out.println(findFile);
                        // System.out.println(regexp);
                        // System.out.println(showUI);
                        if (showUI) {
                                SwingUtilities.invokeLater(new Runnable() {
                                        public void run() {
                                                new AppMainFrame(zipFile,
                                                                findFile,
                                                                regexp)
                                                                .setVisible(true);
                                        }
                                });
                        } else {
                                String info = FileUtil.getFileData(zipFile,
                                                findFile, regexp);
                                System.out.println(info);
                        }
                } else {
                        SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                        new AppMainFrame().setVisible(true);
                                }
                        });
                }

        }

}
