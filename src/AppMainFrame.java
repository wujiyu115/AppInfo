import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.fife.ui.rtextarea.RTextScrollPane;

/***
 * 
 * zip文件,压缩包中要查找的文件,是否是正则匹配,是否显示在UI界面 zipFile,findFile,regexp,showUI
 * */
public class AppMainFrame extends JFrame {
	private XMLRSyntaxTextArea textArea;
	private JLabel name_text;
	private JLabel versionLabel;
	private JLabel version_text;
	private JLabel versionCodeLabel;
	private JLabel versionCode_text;
	
	static int  WIN_WIDTH= 560;
	static int  WIN_HEIGHT= 680;

	public AppMainFrame() {
		JPanel mainPanel = new JPanel(new VerticalFlowLayout());
		mainPanel.setPreferredSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));

		textArea = new XMLRSyntaxTextArea(30, 70);
		RTextScrollPane sp = new RTextScrollPane(textArea);

		JPanel packagePanel = new JPanel(new VerticalFlowLayout(VerticalFlowLayout.TOP));
		packagePanel.setPreferredSize(new Dimension(WIN_WIDTH,120));
		packagePanel.setBorder(BorderFactory.createTitledBorder("软件信息"));
		
		JPanel namePanel = new JPanel(new FlowLayout());
		JLabel nameLabel = new JLabel("软件名称");
		name_text = new JLabel("com.far");
		namePanel.add(nameLabel);
		namePanel.add(name_text);
		
		JPanel versionPanel = new JPanel(new FlowLayout());
		versionLabel = new JLabel("软件名称");
		version_text = new JLabel("com.far");
		versionPanel.add(versionLabel);
		versionPanel.add(version_text);
		
		JPanel versionCodePanel = new JPanel(new FlowLayout());
		versionCodeLabel = new JLabel("软件名称");
		versionCode_text = new JLabel("com.far");
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
//				System.out.println(info);
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
