import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 思路：提供一个交互窗口，用户可多次、自主的选择要统计的java文件，并计算其有效行数 
 * 1.有效行数不包括空行 
 * 2.不考虑代码间有多行注释的情况
 */

public class EffectiveLines {
	Frame m_jf = new Frame();
	FileDialog m_OpenDilog = new FileDialog(m_jf, "要统计的JAVA文件", FileDialog.LOAD);
	Button m_Button = new Button("点击选择文件");

	/**
	 * 窗口初始化
	 */
	public void init() throws IOException {
		m_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_OpenDilog.setVisible(true);    //弹出文件选择对话框

				String fileName = m_OpenDilog.getFile();   //获取文件名
				String path = m_OpenDilog.getDirectory() + fileName; // 获取文件路径

				if (!path.equals("nullnull") && fileName.endsWith(".java")) // 选择了一个JAVA文件，未选择或非java将不进行统计
				{
					System.out.println("文件名：" + fileName); // 打印当前选择的文件名
					int lineNum; // 有效行数
					try {
						lineNum = CountLines(path); // 统计JAVA文件有效行数
						System.out.println("行数：" + lineNum); // 打印有效行数
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
		});

		m_jf.add(m_Button);
		m_jf.pack();
		m_jf.setVisible(true);
		m_jf.addWindowListener(new WindowAdapter() // 关闭Frame
		{
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

	/**
	 * 统计Java文件有效行数
	 * 
	 * @param path
	 *            Java文件的路径
	 * @return 有效行数
	 */
	public int CountLines(String path) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(path));// 选择路径，为文件流对象
		int lineNum = 0;
		while (br.ready()) {
			String str = br.readLine().trim(); // 读取一行字符

			if (str.isEmpty() || str.startsWith("//")
					|| (str.startsWith("/*") && str.endsWith("*/")))
				continue;

			++lineNum; // 有效行数加1
		}
		br.close(); // 关闭
		return lineNum;
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) throws IOException {
		new EffectiveLines().init();
	}
}
