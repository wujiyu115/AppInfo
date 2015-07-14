import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.fife.ui.rtextarea.RTextScrollPane;

/***
 * 
 * zip文件,压缩包中要查找的文件,是否是正则匹配,是否显示在UI界面 zipFile,findFile,regexp,showUI
 * */
public class AppMainFrame extends JFrame {
	private XMLRSyntaxTextArea textArea;
	private JLabel nameLabel;
	private JLabel name_text;
	private JLabel versionLabel;
	private JLabel version_text;
	private JLabel versionCodeLabel;
	private JLabel versionCode_text;
	private IDropInterface dropInterface = new IDropInterface() {

		@Override
		public void drop(AppInfoBean appInfo) {
			name_text.setText("");
			version_text.setText("");
			versionCode_text.setText("");
			name_text.setText(appInfo.getPackageName());
			version_text.setText(appInfo.getVersionStr());
			versionCode_text.setText(appInfo.getVersionCode());
			versionLabel.setText(appInfo.getVersionStrLabel());
			versionCodeLabel.setText(appInfo.getVersionCodeLabel());
		}
	};

	static int WIN_WIDTH = 560;
	static int WIN_HEIGHT = 680;

	public static void initGlobalFontSetting(Font fnt) {
		FontUIResource fontRes = new FontUIResource(fnt);
		for (Enumeration keys = UIManager.getDefaults().keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource)
				UIManager.put(key, fontRes);
		}
	}

	public AppMainFrame() {
		initGlobalFontSetting(new Font("微软雅黑",Font.PLAIN,12));
		JPanel mainPanel = new JPanel(new VerticalFlowLayout());
		mainPanel.setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));

		textArea = new XMLRSyntaxTextArea(30, 70);
		RTextScrollPane sp = new RTextScrollPane(textArea);
		textArea.setDropCallBack(dropInterface);

		JPanel packagePanel = new JPanel(new VerticalFlowLayout(
				VerticalFlowLayout.TOP));
		packagePanel.setPreferredSize(new Dimension(WIN_WIDTH, 150));
		packagePanel.setBorder(BorderFactory
				.createTitledBorder("拖动apk或者ipa到文本区域"));

		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nameLabel = new JLabel("软件名称");
		name_text = new JLabel("");
		namePanel.add(nameLabel);
		namePanel.add(name_text);

		JPanel versionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		versionLabel = new JLabel("versionStr:");
		version_text = new JLabel("");
		versionPanel.add(versionLabel);
		versionPanel.add(version_text);

		JPanel versionCodePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		versionCodeLabel = new JLabel("versionCode:");
		versionCode_text = new JLabel("");
		versionCodePanel.add(versionCodeLabel);
		versionCodePanel.add(versionCode_text);

		packagePanel.add(namePanel);
		packagePanel.add(versionPanel);
		packagePanel.add(versionCodePanel);

		mainPanel.add(packagePanel);
		mainPanel.add(sp);
		setContentPane(mainPanel);
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
						new AppMainFrame(zipFile, findFile, regexp)
								.setVisible(true);
					}
				});
			} else {
				String info = FileUtil.getFileData(zipFile, findFile, regexp);
				// System.out.println(info);
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
