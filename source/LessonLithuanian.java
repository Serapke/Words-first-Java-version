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

	int max = -1;
	int err;

	public LessonLithuanian(boolean s, boolean st, String n) {
		showSentences = s;
		saveStats = st;
		nickName = n;

		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JLabel lessonL = new JLabel("Lessons [LT-ENG]");
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

		textF = new JTextField();
		textF.setEnabled(false);
		textF.setHorizontalAlignment(SwingConstants.LEFT);
		textF.setColumns(10);
		textF.setBounds(208, 20, 146, 20);
		textF.addKeyListener(new EnterKeyListener());

		stateL = new JLabel("");
		stateL.setFont(new Font("Times New Roman", Font.BOLD, 11));
		stateL.setBounds(172, 52, 70, 14);

		correctAnswersL = new JLabel("");
		correctAnswersL.setFont(new Font("Tahoma", Font.BOLD, 11));
		correctAnswersL.setForeground(new Color(0, 128, 0));
		correctAnswersL.setBounds(252, 52, 146, 14);

		sentence = new JTextArea(3, 50);
		sentence.setVisible(false);
		sentence.setEditable(false);
		sentence.setWrapStyleWord(true);
		sentence.setLineWrap(true);
		JScrollPane sScroller = new JScrollPane(sentence);
		sScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sScroller.setBounds(20, 81, 378, 50);

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
				lessonView.add(wScroller);
				lessonView.add(textF);
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

		words.setVisible(true);
		textF.setEnabled(true);
		nextB.setEnabled(true);
		progressBar.setMaximum(max+1);

		showNextWord();
	}

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
	}

	public class NextButtonListener implements ActionListener {
		private String eng;
		private String answer;
		public void actionPerformed(ActionEvent ev) {
			if (isShowAnswer) {
				eng = currentWord.getEnglish();
				answer = textF.getText();
				answer = answer.substring(1, eng.length()+1);
				//System.out.println("|" + eng + "| |" + answer + "|");
			    if (eng.equals(answer)) {
					stateL.setForeground(new Color(0, 128, 0));
					stateL.setText("Correct");
				}
			    else {
					err++;
					stateL.setForeground(new Color(128, 0, 0));
					stateL.setText("Incorrect!");
				}
				correctAnswersL.setText(currentWord.getEnglish());
				if (showSentences)
					sentence.setText(currentWord.getSentence());
				nextB.setText("Next word");
				isShowAnswer = false;
			} else {
				if (currentWordIndex < wordList.size()) {
					showNextWord();
				} else {
					//System.out.println("That was last word!");
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
