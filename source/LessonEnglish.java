package zodziai;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LessonEnglish extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel lessonP;
	private StatisticsPanel statisticsPanel;
	
	private ArrayList<Word> wordList;
	private Word currentWord;
	private int currentWordIndex;
	
	private JLabel wordL;
	private JLabel posL;
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
	
	public LessonEnglish(boolean s, boolean st, String n) {
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
		JLabel lessonL = new JLabel("PAMOKOS [ENG-LT]");
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
		wordL = new JLabel("Þodis");
		wordL.setEnabled(false);
		wordL.setSize(100, 20);
		wordL.setLocation(20, 20);
		wordL.setFont(new Font("Times New Roman", Font.BOLD, 14));
		wordL.setHorizontalAlignment(SwingConstants.LEFT);
		
		/*
		 * Vieta atsakymui
		 */
		textF = new JTextField();
		textF.setEnabled(false);
		textF.setHorizontalAlignment(SwingConstants.LEFT);
		textF.setColumns(10);
		textF.setBounds(130, 21, 146, 20);
		textF.addKeyListener(new EnterKeyListener());
		
		/*
		 * Kalbos dalis		
		 */
		posL = new JLabel("(kalbos dalis)");
		posL.setEnabled(false);
		posL.setBounds(286, 21, 80, 20);
		posL.setFont(new Font("Times New Roman", Font.BOLD, 11));
		posL.setHorizontalAlignment(SwingConstants.LEFT);
		
		/*
		 * Indikatorius parodantis, ar atsakymas buvo teistingas, ar ne
		 */
		stateL = new JLabel("");
		stateL.setFont(new Font("Times New Roman", Font.BOLD, 11));
		stateL.setBounds(20, 51, 70, 14);
		
		/*
		 * Teisingas atsakymas
		 */
		correctAnswersL = new JLabel("");
		correctAnswersL.setFont(new Font("Tahoma", Font.BOLD, 11));
		correctAnswersL.setForeground(new Color(0, 128, 0));
		correctAnswersL.setBounds(106, 52, 260, 14);
		
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
		sScroller.setBounds(20, 80, 346, 51);
		
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
				lessonView.add(wordL);
				lessonView.add(textF);
				lessonView.add(posL);
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
		wordL.setEnabled(true);
		textF.setEnabled(true);
		posL.setEnabled(true);
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
		wordL.setText(currentWord.getEnglish());
		posL.setText(currentWord.getPartOfSpeech());
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
		private String lith;
		private String temp;
		private boolean found;
		private String answer;
		public void actionPerformed(ActionEvent ev) {
			if (isShowAnswer) {
				found = false;
				lith = currentWord.getLithuanian();
				StringTokenizer parser = new StringTokenizer(lith, ",");
				while (parser.hasMoreTokens()) {
					temp = parser.nextToken();
					answer = textF.getText();
					temp = temp.replaceAll("\\s","");
					answer = answer.replaceAll("\\s","");
				    if (temp.equals(answer)) {
						stateL.setForeground(new Color(0, 128, 0));
						stateL.setText("Teisingai");
						found = true;
						break;
					}
				}
				if (!found) {
					err++;
					stateL.setForeground(new Color(128, 0, 0));
					stateL.setText("Neteisingai");
				}
				correctAnswersL.setText(currentWord.getLithuanian());
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
