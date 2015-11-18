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

	int max = -1;
	int err;

	public LessonEnglish(boolean s, boolean st, String n) {
		showSentences = s;
		saveStats = st;
		nickName = n;

		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JLabel lessonL = new JLabel("Lessons [ENG-LT]");
		lessonL.setHorizontalAlignment(SwingConstants.CENTER);
		lessonL.setFont(new Font("Times New Roman", Font.BOLD, 18));

		lessonP = new JPanel();
		lessonP.setBackground(new Color(255, 255, 153));
		lessonP.setLayout(null);

		JLabel loadL = new JLabel("Choose a lesson");
		loadL.setBounds(10, 22, 112, 19);
		loadL.setFont(new Font("Times New Roman", Font.BOLD, 13));
		loadL.setHorizontalAlignment(SwingConstants.LEFT);

		JButton loadB = new JButton("Submit");
		loadB.addActionListener(new LoadListener());
		loadB.addKeyListener(new LoadEnterListener());
		loadB.setBounds(132, 20, 89, 23);

		JPanel lessonView = new JPanel();
		lessonView.setBackground(new Color(255, 255, 153));
		lessonView.setBounds(10, 59, 430, 192);
		lessonView.setLayout(null);

		wordL = new JLabel("Word");
		wordL.setEnabled(false);
		wordL.setSize(100, 20);
		wordL.setLocation(20, 20);
		wordL.setFont(new Font("Times New Roman", Font.BOLD, 14));
		wordL.setHorizontalAlignment(SwingConstants.LEFT);

		textF = new JTextField();
		textF.setEnabled(false);
		textF.setHorizontalAlignment(SwingConstants.LEFT);
		textF.setColumns(10);
		textF.setBounds(130, 21, 146, 20);
		textF.addKeyListener(new EnterKeyListener());

		posL = new JLabel("(part of speech)");
		posL.setEnabled(false);
		posL.setBounds(286, 21, 80, 20);
		posL.setFont(new Font("Times New Roman", Font.BOLD, 11));
		posL.setHorizontalAlignment(SwingConstants.LEFT);

		/*
		 * Indicator, showing if the answer was correct or not
		 */
		stateL = new JLabel("");
		stateL.setFont(new Font("Times New Roman", Font.BOLD, 11));
		stateL.setBounds(20, 51, 70, 14);

		correctAnswersL = new JLabel("");
		correctAnswersL.setFont(new Font("Tahoma", Font.BOLD, 11));
		correctAnswersL.setForeground(new Color(0, 128, 0));
		correctAnswersL.setBounds(106, 52, 260, 14);

		/*
		 * Example sentence
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
		 * Shows how many words are still left to answer
		 */
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setBounds(39, 150, 146, 23);

		nextB = new JButton("Next word");
		nextB.addActionListener(new NextButtonListener());
		nextB.setEnabled(false);
		nextB.setBounds(253, 150, 113, 23);

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

	public class LoadListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			JFileChooser fileOpen = new JFileChooser("C:\\Users\\Mantas\\Desktop\\SkyDrive\\IT\\Java\\�od�iai\\lessons");
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
	}

	private void loadFile(File file) {
		wordList = new ArrayList<Word>();

		// Words are read from file and saved to wordList
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
			System.out.println("Error while trying to read words from selected lesson!");
			ex.printStackTrace();
		}

		wordL.setEnabled(true);
		textF.setEnabled(true);
		posL.setEnabled(true);
		nextB.setEnabled(true);
		progressBar.setMaximum(max+1);

		showNextWord();								// Shows the first word
	}

	/*
	 * Parse a word from a formatted line
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

		nextB.setText("Check");
		isShowAnswer = true;
	}

	public class EnterKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				new NextButtonListener().actionPerformed(null);
			}
		}
	}

	public class NextButtonListener implements ActionListener {
		private String lith;
		private String temp;
		private boolean found;
		private String answer;
		public void actionPerformed(ActionEvent ev) {
			if (isShowAnswer) {																				// Checks, if the answer is correct
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
						stateL.setText("Correct!");
						found = true;
						break;
					}
				}
				// If correct translation not found
				if (!found) {
					err++;
					stateL.setForeground(new Color(128, 0, 0));
					stateL.setText("Incorrect!");
				}
				correctAnswersL.setText(currentWord.getLithuanian());		// Shows all possible correct answers
				if (showSentences)																			// Shows the example sentence of the word (optional)
					sentence.setText(currentWord.getSentence());
				nextB.setText("Next word");
				isShowAnswer = false;
			}
			// Last word reached
			else {
				if (currentWordIndex < wordList.size()) {
					showNextWord();
				} else {
					//System.out.println("That was the last word!");
					ShowStatistics();
					nextB.setEnabled(false);
				}
			}
		}
	}
	public void ShowStatistics() {
		remove(lessonP);
		statisticsPanel = new StatisticsPanel(max, err, nickName);
		if (saveStats) {
			statisticsPanel.saveStatistics();
		}
		add(statisticsPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
