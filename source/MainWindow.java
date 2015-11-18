package zodziai;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * Pradinis programos langas
 */
public class MainWindow {

	private JFrame frame;
	private JLabel mainL;
	private JMenuBar menuBar;
	private JPanel menuP;
	private JLabel copyrightL;
	
	public JMenuItem homeMenuItem;
	public JMenuItem newMenuItem;
	public JMenuItem saveMenuItem;
	public JMenuItem optionsMenuItem;
	public JMenuItem exitMenuItem;
	public JMenuItem homePageMenuItem;
	public JMenuItem statisticsMenuItem;
	
	private LessonEnglish lessonE;
	private LessonLithuanian lessonL;
	private LessonCreator creator;
	private Test test;
	private OptionsPanel options;
	
	private boolean translateLithuanian;
	private boolean translateEnglish;
	private boolean showSentence;
	private boolean saveStats;
	private boolean alwaysOnTop;
	private String nickName;
	
	private boolean optionsChecked = false;

	public static void main(String[] args) {
		MainWindow window = new MainWindow();
		window.go();
		window.loadOptions();
	}

	public void go() {
		/*
		 * Pagrindinio lango parametrai
		 */
		frame = new JFrame("Þodþiai");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(SystemColor.window);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		/*
		 * Programos pavadinimas
		 */
		mainL = new JLabel("MANO ÞODÞIAI");
		mainL.setHorizontalAlignment(SwingConstants.CENTER);
		mainL.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		/*
		 * Pagrindinë panelë (ji turëtø keistis)
		 */
		menuP = new JPanel();
		menuP.setBackground(new Color(255, 255, 153));
		menuP.setBorder(new LineBorder(Color.LIGHT_GRAY));
		menuP.setLayout(null);
		
		/*
		 * 'Pamokos' mygtukas
		 */
		JButton playLessonButton = new JButton("Pamokos");
		playLessonButton.setBackground(new Color(51, 102, 255));
		playLessonButton.setForeground(new Color(255, 255, 255));
		playLessonButton.setBounds(29, 43, 146, 33);
		playLessonButton.setIcon(new ImageIcon("C:\\Users\\Mantas\\Desktop\\SkyDrive\\IT\\Java\\\u017Dod\u017Eiai\\bin\\images\\iconmonstr-book-17-icon-24.png"));
		playLessonButton.addActionListener(new PlayLessonListener());
		
		/*
		 * 'Testas' mygtukas
		 */
		JButton playTestButton = new JButton("Testas");
		playTestButton.setBackground(new Color(51, 102, 255));
		playTestButton.setForeground(new Color(255, 255, 255));
		playTestButton.setBounds(29, 87, 146, 33);
		playTestButton.setIcon(new ImageIcon("C:\\Users\\Mantas\\Desktop\\SkyDrive\\IT\\Java\\\u017Dod\u017Eiai\\bin\\images\\iconmonstr-checkbox-12-icon-24.png"));
		playTestButton.addActionListener(new PlayTestListener());
		
		/*
		 * 'Kurti pamokà' mygtukas
		 */
		JButton createLessonButton = new JButton("Kurti pamok\u0105");
		createLessonButton.setBackground(new Color(51, 102, 255));
		createLessonButton.setForeground(new Color(255, 255, 255));
		createLessonButton.setBounds(29, 131, 146, 33);
		createLessonButton.setIcon(new ImageIcon("C:\\Users\\Mantas\\Desktop\\SkyDrive\\IT\\Java\\\u017Dod\u017Eiai\\bin\\images\\iconmonstr-plus-5-icon-24.png"));
		createLessonButton.addActionListener(new CreateLessonListener());
		
		/*
		 * 'Parametrai' mygtukas
		 */
		JButton openParametersButton = new JButton("Parametrai");
		openParametersButton.setBackground(new Color(51, 102, 255));
		openParametersButton.setForeground(new Color(255, 255, 255));
		openParametersButton.setBounds(29, 175, 144, 33);
		openParametersButton.setIcon(new ImageIcon("C:\\Users\\Mantas\\Desktop\\SkyDrive\\IT\\Java\\\u017Dod\u017Eiai\\bin\\images\\iconmonstr-gear-10-icon-24.png"));
		openParametersButton.addActionListener(new OpenParameterListener());
		
		/*
		 * Mano logotipas
		 */
		JLabel myLogo = new JLabel("");
		myLogo.setIcon(new ImageIcon("C:\\Users\\Mantas\\Desktop\\SkyDrive\\IT\\Java\\\u017Dod\u017Eiai\\bin\\images\\logo.jpg"));
		myLogo.setBounds(204, 71, 208, 104);
		
		/*
		 * 'All rights reserved' uþraðas
		 */
		copyrightL = new JLabel("\u00A9 2014 MS Visos teis\u0117s saugomos");
		copyrightL.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*
		 * Menu
		 */
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
			homeMenuItem = new JMenuItem("Home");
			homeMenuItem.setBackground(new Color(255, 255, 255));
				homeMenuItem.setEnabled(false);
				homeMenuItem.addActionListener(new HomeMenuListener());
			newMenuItem = new JMenuItem("New");
			newMenuItem.setBackground(new Color(255, 255, 255));
				newMenuItem.setEnabled(false);
				newMenuItem.addActionListener(new NewMenuListener());
			saveMenuItem = new JMenuItem("Save");
			saveMenuItem.setBackground(new Color(255, 255, 255));
				saveMenuItem.setEnabled(false);
				saveMenuItem.addActionListener(new SaveMenuListener());
			optionsMenuItem = new JMenuItem("Options");
			optionsMenuItem.setBackground(new Color(255, 255, 255));
				optionsMenuItem.setEnabled(false);
				optionsMenuItem.addActionListener(new OptionsMenuListener());
			exitMenuItem = new JMenuItem("Exit");
			exitMenuItem.setBackground(new Color(255, 255, 255));
				exitMenuItem.addActionListener(new ExitMenuListener());
		JMenu onlineMenu = new JMenu("Online");
			homePageMenuItem = new JMenuItem("Home page");
			homePageMenuItem.setBackground(new Color(255, 255, 255));
			statisticsMenuItem = new JMenuItem("Statistics");
			statisticsMenuItem.setBackground(new Color(255, 255, 255));
		
		
		menuBar.add(fileMenu);
			fileMenu.add(homeMenuItem);
			fileMenu.add(newMenuItem);
			fileMenu.add(saveMenuItem);
			fileMenu.add(optionsMenuItem);
			fileMenu.add(exitMenuItem);
		menuBar.add(onlineMenu);
			onlineMenu.add(homePageMenuItem);
			onlineMenu.add(statisticsMenuItem);
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(mainL, BorderLayout.NORTH);
		frame.getContentPane().add(menuP, BorderLayout.CENTER);
			menuP.add(playLessonButton);
			menuP.add(playTestButton);
			menuP.add(createLessonButton);
			menuP.add(openParametersButton);
			menuP.add(myLogo);
		frame.getContentPane().add(copyrightL, BorderLayout.SOUTH);
		
		/*
		 * Pagrindinio lango parametrai
		 */
		frame.setResizable(false);
		frame.setSize(450, 340);
		frame.setVisible(true);
	}
	
	public class PlayLessonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			frame.getContentPane().remove(mainL);
			frame.getContentPane().remove(menuP);
			frame.getContentPane().remove(copyrightL);
			
			if (optionsChecked) {
				translateLithuanian = options.getTranslateLithuanian();
				translateEnglish = options.getTranslateEnglish();
			}
			
			homeMenuItem.setEnabled(true);
			optionsMenuItem.setEnabled(true);
			if (translateEnglish) {
				lessonE = new LessonEnglish(showSentence, saveStats, nickName);
				frame.getContentPane().add(lessonE, BorderLayout.CENTER);
			}
			else {
				lessonL = new LessonLithuanian(showSentence, saveStats, nickName);
				frame.getContentPane().add(lessonL, BorderLayout.CENTER);
			}
			frame.getContentPane().revalidate();
			frame.getContentPane().repaint();
		}
	}
	
	public class PlayTestListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			frame.getContentPane().remove(mainL);
			frame.getContentPane().remove(menuP);
			frame.getContentPane().remove(copyrightL);
			homeMenuItem.setEnabled(true);
			test = new Test();
			frame.getContentPane().add(test, BorderLayout.CENTER);
			frame.getContentPane().revalidate();
			frame.getContentPane().repaint();
		}
	}
	
	public class CreateLessonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			frame.getContentPane().remove(mainL);
			frame.getContentPane().remove(menuP);
			frame.getContentPane().remove(copyrightL);
			homeMenuItem.setEnabled(true);
			newMenuItem.setEnabled(true);
			saveMenuItem.setEnabled(true);
			creator = new LessonCreator();
			frame.getContentPane().add(creator, BorderLayout.CENTER);
			frame.getContentPane().revalidate();
			frame.getContentPane().repaint();
		}
	}
	
	public class OpenParameterListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			frame.getContentPane().remove(mainL);
			frame.getContentPane().remove(menuP);
			frame.getContentPane().remove(copyrightL);
			homeMenuItem.setEnabled(true);
			options = new OptionsPanel(showSentence, saveStats, alwaysOnTop, translateLithuanian, translateEnglish, nickName);
			frame.getContentPane().add(options, BorderLayout.CENTER);
			frame.getContentPane().revalidate();
			frame.getContentPane().repaint();
			optionsChecked = true;
			showSentence = options.getShowSentence();
			saveStats = options.getSaveStats();
			alwaysOnTop = options.getAlwaysOnTop();
			frame.setAlwaysOnTop(alwaysOnTop);
		}
	}
	
	/*
	 * Gráþimas á pagrindiná menu (File -> Home)
	 */
	public class HomeMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(mainL, BorderLayout.NORTH);
			frame.getContentPane().add(menuP, BorderLayout.CENTER);
			frame.getContentPane().add(copyrightL, BorderLayout.SOUTH);
			frame.getContentPane().revalidate();
			frame.getContentPane().repaint();
			homeMenuItem.setEnabled(false);
			newMenuItem.setEnabled(false);
			saveMenuItem.setEnabled(false);
			optionsMenuItem.setEnabled(false);
		}
	}
	
	/*
	 * Naujos pamokos kûrimas (File -> New)
	 */
	public class NewMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			creator.wordList.clear();
			creator.clearWord();
		}
	}
	
	/*
	 * Pamokos iðsaugojimas árenginyje (File -> Save)
	 */
	public class SaveMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			System.out.println("SAVE");
			JFileChooser fileSave = new JFileChooser("C:\\Users\\Mantas\\Desktop\\SkyDrive\\IT\\Java\\Þodþiai\\lessons");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
			fileSave.setFileFilter(filter);
			fileSave.showSaveDialog(new JFrame());
			File file = new File(fileSave.getSelectedFile() +  ".txt");
			creator.saveFile(file);
		}
	}
	
	/*
	 * Programos parametrai (File -> Exit)
	 * Naujam lange
	 */
	public class OptionsMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			JFrame newFrame = new JFrame();
			options = new OptionsPanel(showSentence, saveStats, alwaysOnTop, translateLithuanian, translateEnglish, nickName);
			newFrame.getContentPane().add(options, BorderLayout.CENTER);
			newFrame.setResizable(false);
			newFrame.setSize(450, 340);
			newFrame.setVisible(true);
			optionsChecked = true;
			showSentence = options.getShowSentence();
			saveStats = options.getSaveStats();
			alwaysOnTop = options.getAlwaysOnTop();
			frame.setAlwaysOnTop(alwaysOnTop);
		}
	}
	
	/*
	 * Programos iðjungimas (File -> Exit)
	 */
	public class ExitMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			System.exit(0);
		}
	}
	
	public void loadOptions() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("options.txt"));
				translateLithuanian = Boolean.valueOf(reader.readLine());
				translateEnglish = Boolean.valueOf(reader.readLine());
				showSentence = Boolean.valueOf(reader.readLine());
				saveStats = Boolean.valueOf(reader.readLine());
				alwaysOnTop = Boolean.valueOf(reader.readLine());
				nickName = reader.readLine();
			reader.close();
		} catch(Exception ex) {
			System.out.println("Error. Couldn't read the card file");
			ex.printStackTrace();
		}
	}
}
