package zodziai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Test extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lessonL;
	private JPanel lessonP;
	private ArrayList<JButton> buttonGroup;
	private JButton choice1B;
	private JButton choice2B;
	private JButton choice3B;
	private JButton choice4B;
	private StatisticsPanel statisticsPanel;

	private int count;
	private int iterator = 0;
	private int max;
	private int err;
	private int rightChoice;

	private JLabel wordL;

	private ArrayList<Word> wordList;
	private Word currentWord;

	public Test() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		lessonL = new JLabel("Test");
		lessonL.setHorizontalAlignment(SwingConstants.CENTER);
		lessonL.setFont(new Font("Times New Roman", Font.BOLD, 18));

		lessonP = new JPanel();
		lessonP.setBackground(new Color(255, 255, 153));
		lessonP.setLayout(null);

		wordL = new JLabel("");
		wordL.setForeground(new Color(0, 0, 102));
		wordL.setHorizontalAlignment(SwingConstants.CENTER);
		wordL.setFont(new Font("Tahoma", Font.BOLD, 17));
		wordL.setBounds(143, 30, 163, 33);

		choice1B = new JButton("");
		choice1B.setForeground(new Color(255, 255, 255));
		choice1B.setBackground(new Color(51, 102, 255));
		choice1B.addActionListener(new ButtonPressedListener());
		choice1B.setBounds(40, 103, 163, 59);

		choice2B = new JButton("");
		choice2B.setForeground(new Color(255, 255, 255));
		choice2B.setBackground(new Color(51, 102, 255));
		choice2B.addActionListener(new ButtonPressedListener());
		choice2B.setBounds(247, 103, 163, 59);

		choice3B = new JButton("");
		choice3B.setForeground(new Color(255, 255, 255));
		choice3B.setBackground(new Color(51, 102, 255));
		choice3B.addActionListener(new ButtonPressedListener());
		choice3B.setBounds(40, 186, 163, 59);

		choice4B = new JButton("");
		choice4B.setForeground(new Color(255, 255, 255));
		choice4B.setBackground(new Color(51, 102, 255));
		choice4B.addActionListener(new ButtonPressedListener());
		choice4B.setBounds(247, 186, 163, 59);

		buttonGroup = new ArrayList<JButton>();
		buttonGroup.add(choice1B);
		buttonGroup.add(choice2B);
		buttonGroup.add(choice3B);
		buttonGroup.add(choice4B);

		add(lessonL, BorderLayout.NORTH);
		add(lessonP, BorderLayout.CENTER);
			lessonP.add(wordL);
			lessonP.add(choice1B);
			lessonP.add(choice2B);
			lessonP.add(choice3B);
			lessonP.add(choice4B);

		loadFile();
	}

	private void makeWord(String lineToParse) {
		StringTokenizer parser = new StringTokenizer(lineToParse, ".");
		if (parser.hasMoreTokens()) {
		   Word nWord = new Word(parser.nextToken(), parser.nextToken(), parser.nextToken(), parser.nextToken());
		   wordList.add(nWord);
		}
	}

	private void loadFile() {
		wordList = new ArrayList<Word>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("all-lessons.ss"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				count++;
				makeWord(line);
				System.out.println(line);
			}
			reader.close();
		} catch(Exception ex) {
			System.out.println("Error while trying to load all lessons file!");
			ex.printStackTrace();
		}

		showNextWord();
	}

	private void showNextWord() {

		iterator++;
		int randChoice1;
		int randChoice2;
		int randChoice3;
		int randChoice4;

		int randButton1;
		int randButton2;
		int randButton3;
		int randButton4;

		String lith;
		/*
		 * Choose 4 random distinct numbers
		 */
		randChoice1 = (int) (Math.random() * count-1);
		randChoice2 = (int) (Math.random() * count-1);
		while (randChoice2 == randChoice1)
			randChoice2 = (int) (Math.random() * count-1);
		randChoice3 = (int) (Math.random() * count-1);
		while ((randChoice3 == randChoice1) || (randChoice3 == randChoice2))
			randChoice3 = (int) (Math.random() * count-1);
		randChoice4 = (int) (Math.random() * count-1);
		while ((randChoice4 == randChoice1) || (randChoice4 == randChoice2) || (randChoice4 == randChoice3))
			randChoice4 = (int) (Math.random() * count-1);

		currentWord = wordList.get(randChoice1);
		wordL.setText(currentWord.getEnglish());

		randButton1 = (int) (Math.random() * 4);
		rightChoice = randButton1;
		randButton2 = (int) (Math.random() * 4);
		while (randButton1 == randButton2)
			randButton2 = (int) (Math.random() * 4);
		randButton3 = (int) (Math.random() * 4);
		while ((randButton1 == randButton3) || (randButton2 == randButton3))
			randButton3 = (int) (Math.random() * 4);
		randButton4 = (int) (Math.random() * 4);
		while ((randButton1 == randButton4) || (randButton2 == randButton4) || (randButton3 == randButton4))
			randButton4 = (int) (Math.random() * 4);

		lith = currentWord.getLithuanian();
		StringTokenizer parser = new StringTokenizer(lith, ",");
		buttonGroup.get(randButton1).setText(parser.nextToken());

		lith = wordList.get(randChoice2).getLithuanian();
		parser = new StringTokenizer(lith, ",");
		buttonGroup.get(randButton2).setText(parser.nextToken());

		lith = wordList.get(randChoice3).getLithuanian();
		parser = new StringTokenizer(lith, ",");
		buttonGroup.get(randButton3).setText(parser.nextToken());

		lith = wordList.get(randChoice4).getLithuanian();
		parser = new StringTokenizer(lith, ",");
		buttonGroup.get(randButton4).setText(parser.nextToken());

		count--;
		wordList.remove(randChoice1);
	}

	public class ButtonPressedListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == choice1B) {
				if (buttonGroup.get(rightChoice) != choice1B) {
					err++;
				}
			}
			else if (ev.getSource() == choice2B) {
				if (buttonGroup.get(rightChoice) != choice2B) {
					err++;
				}
			}
			else if (ev.getSource() == choice3B) {
				if (buttonGroup.get(rightChoice) != choice3B) {
					err++;
				}
			}
			else if (ev.getSource() == choice4B) {
				if (buttonGroup.get(rightChoice) != choice4B) {
					err++;
				}
			}

			max++;
			if ((count > 4) && (iterator < 100))
				showNextWord();
			else {
				ShowStatistics();
			}
		}
	}

	public void ShowStatistics() {
		remove(lessonP);
		statisticsPanel = new StatisticsPanel(max, err);
		add(statisticsPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
