
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SririGUI {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SririGUI window = new SririGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SririGUI() {
		initialize();
	}

	private JFrame frame;
	private JButton btnBrowseGama;
	private JTextField txtPathGama;
	private JLabel lbPathGama;
	private JButton btnBrowseXml;
	private JTextField txtPathXml;
	private JLabel lbPathXml;
	private JButton btnBrowseOut;
	private JTextField txtPathOut;
	private JLabel lbPathOut;
	private JTextArea txtConsole;
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Run GAMA headless");
		frame.setBounds(100, 100, 900, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		int y=10;
		lbPathGama=new JLabel("Location of Gama's headless plugin");
		lbPathGama.setBounds(10, y, 414, 21);
		frame.getContentPane().add(lbPathGama);
		txtPathGama = new JTextField();
		y+=30;
		txtPathGama.setBounds(10, y, 414, 21);
		frame.getContentPane().add(txtPathGama);
		txtPathGama.setColumns(10);
		txtPathGama.setText("E:\\GAMA1.7_Win_64_09.28.17_dd9946d\\headless\\");

		y+=30;
		btnBrowseGama = new JButton("Browse");
		btnBrowseGama.setBounds(10, y, 87, 23);
		frame.getContentPane().add(btnBrowseGama);

		btnBrowseGama.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				// For Directory
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				// For File
				// fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				fileChooser.setAcceptAllFileFilterUsed(false);

				int rVal = fileChooser.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					txtPathGama.setText(fileChooser.getSelectedFile().toString());
				}
			}
		});
		

		y+=30;
		lbPathXml=new JLabel("Location of headless xml");
		lbPathXml.setBounds(10, y, 414, 21);
		frame.getContentPane().add(lbPathXml);
		txtPathXml = new JTextField();
		y+=30;
		txtPathXml.setBounds(10, y, 414, 21);
		frame.getContentPane().add(txtPathXml);
		txtPathXml.setColumns(10);
		txtPathXml.setText("D:\\toto\\test.xml");

		y+=30;
		btnBrowseXml = new JButton("Browse");
		btnBrowseXml.setBounds(10, y, 87, 23);
		frame.getContentPane().add(btnBrowseXml);

		btnBrowseXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				// For Directory
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				// For File
				// fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				fileChooser.setAcceptAllFileFilterUsed(false);

				int rVal = fileChooser.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					txtPathXml.setText(fileChooser.getSelectedFile().toString());
				}
			}
		});
		

		y+=30;
		lbPathOut=new JLabel("Location of output");
		lbPathOut.setBounds(10, y, 414, 21);
		frame.getContentPane().add(lbPathOut);
		txtPathOut = new JTextField();
		y+=30;
		txtPathOut.setBounds(10, y, 414, 21);
		frame.getContentPane().add(txtPathOut);
		txtPathOut.setColumns(10);
		txtPathOut.setText("D:\\toto\\tout");

		y+=30;
		btnBrowseOut = new JButton("Browse");
		btnBrowseOut.setBounds(10, y, 87, 23);
		frame.getContentPane().add(btnBrowseOut);

		btnBrowseOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				// For Directory
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				// For File
				// fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				fileChooser.setAcceptAllFileFilterUsed(false);

				int rVal = fileChooser.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					txtPathOut.setText(fileChooser.getSelectedFile().toString());
				}
			}
		});
		

		y+=30;
		JButton btnRun = new JButton("Run");
		btnRun.setBounds(10, y, 87, 23);

		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				sh gama-headless.sh ./abcd_headless-2.xml /Volumes/drive/ABCD/outputHeadlessABCD
				String scriptfile=System.getProperty("os.name").startsWith("Windows")?"gama-headless.bat":"gama-headless.sh";
				final String out=console(txtPathGama.getText()+ "\\"+scriptfile+" -v  \""+txtPathXml.getText()+"\" \""+txtPathOut.getText()+"\"",txtPathGama.getText());
				txtConsole.setText(txtConsole.getText()+"\r\n"+out);
			}
		});
		
		frame.getContentPane().add(btnRun);
		

		y=10;
		txtConsole = new JTextArea();
		txtConsole.setEditable(false);
		txtConsole.setWrapStyleWord(true);
		txtConsole.setBounds(450, y, 414, 310);
		txtConsole.setColumns(10);

	    JScrollPane scroll = new JScrollPane (txtConsole);
	    scroll.setBounds(450, y, 414, 310);
	    frame.getContentPane().add(scroll);
		
	}

	public final String LN = "\r\n";//java.lang.System.getProperty("line.separator");
	public final String TAB = "\t";
	public String console(final String s, String directory) {
				
		if (s == null || s.isEmpty())
			return "";
		final StringBuilder output = new StringBuilder();
		final List<String> commands = new ArrayList<>();
		commands.add(System.getProperty("os.name").startsWith("Windows") ? "cmd.exe" : "/bin/bash");
		commands.add(System.getProperty("os.name").startsWith("Windows") ? "/C" : "-c");
		commands.add(s.trim());
		// commands.addAll(Arrays.asList(s.split(" ")));
		final boolean nonBlocking = commands.get(commands.size() - 1).endsWith("&");
		if (nonBlocking) {
			// commands.(commands.size() - 1);
		}
		final ProcessBuilder b = new ProcessBuilder(commands);
		b.redirectErrorStream(true);
		b.directory(new File(directory));
		try {
			final Process p = b.start();
			if (nonBlocking)
				return "";
			final BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			final int returnValue = p.waitFor();
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + LN);
			}
			if (returnValue != 0) { System.out.println("Error in console command." + output.toString()); }
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return output.toString();

	}
}
