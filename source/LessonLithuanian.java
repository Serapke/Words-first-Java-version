package zodziai;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.io.*;

public class LessonLithuanian extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel lessonP;
	private StatisticsPanel statisticsPanel;
	
	private ArrayList<Word> wordList;
	private Word currentWord;
	private int currentWordIndex;
	
	private JTextArea words;
	private JTextField textF;
	private JTextArea sentence;
	private JButton nextB;
	private JProgressBar progressBar;
	private JLabel stateL;
	private JLabel correctAnswersL;
	
	private boolean isShowAnswer;
	
	private boolean showSentences;
	private boolean saveStats;
	private String nickName;
	
	/*
	 * Statistikos
	 */
	int max = -1;
	int err;
	
	// Nepamirðti uþsaugoti rezultatø á duomenø bazæ
	
	public LessonLithuanian(boolean s, boolean st, String n) {
		showSentences = s;
		saveStats = st;
		nickName = n;
	
		/*
		 * Panelës nustatymai
		 */
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		
		/*
		 * Menu srities pavadinimas
		 */
		JLabel lessonL = new JLabel("PAMOKOS [LT-ENG]");
		lessonL.setHorizontalAlignment(SwingConstants.CENTER);
		lessonL.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		/*
		 * Veiksni panelë
		 */	
		lessonP = new JPanel();
		lessonP.setBackground(new Color(255, 255, 153));
		lessonP.setLayout(null);
		
		/*
		 * Pamokos pasirinkimas
		 */
		JLabel loadL = new JLabel("Pasirinkite pamok\u0105");
		loadL.setBounds(10, 22, 112, 19);
		loadL.setFont(new Font("Times New Roman", Font.BOLD, 13));
		loadL.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton loadB = new JButton("Pasirinkti");
		loadB.addActionListener(new LoadListener());
		loadB.addKeyListener(new LoadEnterListener());
		loadB.setBounds(132, 20, 89, 23);
		
		/*
		 * Pamokos panelë
		 */
		JPanel lessonView = new JPanel();
		lessonView.setBackground(new Color(255, 255, 153));
		lessonView.setBounds(10, 59, 430, 192);
		lessonView.setLayout(null);
		
		/*
		 * Þodis
		 */
		words = new JTextArea();
		words.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		words.setVisible(false);
		words.setEditable(false);
		words.setRows(2);
		words.setLineWrap(true);
		words.setWrapStyleWord(true);
		JScrollPane wScroller = new JScrollPane(words);
		wScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		wScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		wScroller.setBounds(20, 21, 142, 45);
		
		/*
		 * Vieta atsakymui
		 */
		textF = new JTextField();
		textF.setEnabled(false);
		textF.setHorizontalAlignment(SwingConstants.LEFT);
		textF.setColumns(10);
		textF.setBounds(208, 20, 146, 20);
		textF.addKeyListener(new EnterKeyListener());
		
		/*
		 * Indikatorius parodantis, ar atsakymas buvo teistingas, ar ne
		 */
		stateL = new JLabel("");
		stateL.setFont(new Font("Times New Roman", Font.BOLD, 11));
		stateL.setBounds(172, 52, 70, 14);
		
		/*
		 * Teisingas atsakymas
		 */
		correctAnswersL = new JLabel("");
		correctAnswersL.setFont(new Font("Tahoma", Font.BOLD, 11));
		correctAnswersL.setForeground(new Color(0, 128, 0));
		correctAnswersL.setBounds(252, 52, 146, 14);
		
		/*
		 * Pavyzdinis sakinys
		 */
		sentence = new JTextArea(3, 50);
		sentence.setVisible(false);
		sentence.setEditable(false);
		sentence.setWrapStyleWord(true);
		sentence.setLineWrap(true);
		JScrollPane sScroller = new JScrollPane(sentence);
		sScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sScroller.setBounds(20, 81, 378, 50);
		
		/*
		 * Progreso þymeklis
		 * Parodo, kiek þodþiø dar liko atsakyti
		 */
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setBounds(39, 150, 146, 23);
		
		/*
		 * Sekanèio þodþio mygtukas
		 */
		nextB = new JButton("Kitas");
		nextB.addActionListener(new NextButtonListener());
		nextB.setEnabled(false);
		nextB.setBounds(253, 150, 113, 23);
		
		/*
		 * Programos iðdëstymas
		 */
		add(lessonL, BorderLayout.NORTH);
		add(lessonP, BorderLayout.CENTER);
			lessonP.add(loadL);
			lessonP.add(loadB);
			lessonP.add(lessonView);
				lessonView.add(wScroller);
				lessonView.add(textF);
				lessonView.add(stateL);
				lessonView.add(correctAnswersL);
				lessonView.add(sScroller);
				lessonView.add(progressBar);
				lessonView.add(nextB);
				
	}
	
	/*
	 * Pamokos pasirinkimas ið esamø pamokø
	 */
	public class LoadListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			JFileChooser fileOpen = new JFileChooser("C:\\Users\\Mantas\\Desktop\\SkyDrive\\IT\\Java\\Þodþiai\\lessons");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
			fileOpen.setFileFilter(filter);
			fileOpen.showOpenDialog(new JFrame());
			loadFile(fileOpen.getSelectedFile());
		}
	}
	
	public class LoadEnterListener implements KeyListener {
		public void keyPressed(KeyEvent arg0) {
			new LoadListener().actionPerformed(null);
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/*
	 * Veiksmai pakrovus pamokà
	 */
	private void loadFile(File file) {
		wordList = new ArrayList<Word>();			// sukuriamas naujas þodþiø masyvas
		/*
		 * Þodþiai skaitomi ið failo ir áraðomi á masyvà
		 */
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				max++;
				makeWord(line);
				System.out.println(line);
			}	
			reader.close();
		} catch(Exception ex) {
			System.out.println("Nepavyko perskaityti þodþiø");
			ex.printStackTrace();
		}
		/*
		 * Paruoðti panelæ darbui
		 */
		words.setVisible(true);
		textF.setEnabled(true);
		nextB.setEnabled(true);
		progressBar.setMaximum(max+1);
		
		showNextWord();								// Paruoðti pirmàjá þodá
	}
	
	/*
	 * Þodþio paruoðimas
	 */
	private void makeWord(String lineToParse) {
		StringTokenizer parser = new StringTokenizer(lineToParse, ".");
		if (parser.hasMoreTokens()) {
		   Word nWord = new Word(parser.nextToken(), parser.nextToken(), parser.nextToken(), parser.nextToken());
		   wordList.add(nWord);
		}
	}
	
	private void showNextWord() {
		currentWord = wordList.get(currentWordIndex);
		progressBar.setValue(currentWordIndex+1);
		currentWordIndex++;
		textF.setText(" ");
		words.setText(currentWord.getLithuanian());
		stateL.setText("");
		correctAnswersL.setText("");
		sentence.setText("");
		if (showSentences)
			sentence.setVisible(true);
		
		nextB.setText("Tikrinti");
		isShowAnswer = true;
	}
	
	public class EnterKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				new NextButtonListener().actionPerformed(null);
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public class NextButtonListener implements ActionListener {
		private String eng;
		private String answer;
		public void actionPerformed(ActionEvent ev) {
			if (isShowAnswer) {
				eng = currentWord.getEnglish();
				answer = textF.getText();
				answer = answer.substring(1, eng.length()+1);
				System.out.println("|" + eng + "| |" + answer + "|");
			    if (eng.equals(answer)) {
					stateL.setForeground(new Color(0, 128, 0));
					stateL.setText("Teisingai");
				}
			    else {
					err++;
					stateL.setForeground(new Color(128, 0, 0));
					stateL.setText("Neteisingai");
				}
				correctAnswersL.setText(currentWord.getEnglish());
				if (showSentences)
					sentence.setText(currentWord.getSentence());
				nextB.setText("Kitas þodis");
				isShowAnswer = false;
			} else {
				if (currentWordIndex < wordList.size()) {
					showNextWord();
				} else {
					System.out.println("That was last word");
					ShowStatistics();
					nextB.setEnabled(false);
				}
			}
		}
	}
	
	public void ShowStatistics() {
		remove(lessonP);
		System.out.println(saveStats);
		statisticsPanel = new StatisticsPanel(max, err, nickName);
		if (saveStats) {
			statisticsPanel.saveStatistics();
		}
		add(statisticsPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
