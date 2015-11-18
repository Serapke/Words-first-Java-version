package zodziai;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class LessonCreator extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField word;
	private JTextArea translation;
	private JTextArea sentence;
	private JTextField partOfSpeech;
	public ArrayList<Word> wordList;
	
	public LessonCreator() {
		wordList = new ArrayList<Word>();
		
		/*
		 * Panelës nustatymai
		 */
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		
		/*
		 * Menu srities pavadinimas
		 */
		JLabel creatorLabel = new JLabel("SUKURKITE PAMOKÀ");
		creatorLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		creatorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*
		 * Veiksni panelë
		 */
		JPanel creator = new JPanel();
		creator.setBackground(new Color(255, 255, 153));
		creator.setLayout(null);
		
		/*
		 * Þodis
		 */
		JLabel wordLabel = new JLabel("Þodis");
		wordLabel.setBounds(10, 11, 36, 20);
		wordLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		wordLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		word = new JTextField();
		word.setBounds(102, 11, 105, 20);
		word.setHorizontalAlignment(SwingConstants.LEFT);
		word.setColumns(10);
		
		/*
		 * Kalbos dalis		
		 */
		JLabel posLabel = new JLabel("Kalbos dalis");
		posLabel.setBounds(10, 42, 82, 20);
		posLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		posLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		partOfSpeech = new JTextField();
		partOfSpeech.setBounds(102, 42, 105, 20);
		partOfSpeech.setHorizontalAlignment(SwingConstants.LEFT);
		partOfSpeech.setColumns(10);
		
		/*
		 * Vertimas
		 */
		JLabel translationLabel = new JLabel("Vertimas");
		translationLabel.setBounds(10, 73, 82, 20);
		translationLabel.setToolTipText("Vertimus skirkite kableliais.");
		translationLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		translationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		translation = new JTextArea(3, 40);
		translation.setWrapStyleWord(true);
		translation.setLineWrap(true);
		JScrollPane tScroller = new JScrollPane(translation);
		tScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tScroller.setBounds(102, 76, 168, 51);
		
		/*
		 * Sakinys
		 */
		JLabel sentenceLabel = new JLabel("Sakinys");
		sentenceLabel.setHorizontalAlignment(SwingConstants.LEFT);
		sentenceLabel.setFont(new Font("Times New Roman", Font.BOLD, 13));
		sentenceLabel.setBounds(10, 149, 82, 20);
		
		sentence = new JTextArea(3, 50);
		sentence.setText("");
		sentence.setWrapStyleWord(true);
		sentence.setLineWrap(true);
		JScrollPane sScroller = new JScrollPane(sentence);
		sScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sScroller.setBounds(102, 151, 286, 51);
		
		/*
		 * 'Kitas þodis' mygtukas
		 */
		JButton nextButton = new JButton("Kitas \u017Eodis");
		nextButton.setForeground(new Color(255, 255, 255));
		nextButton.setBackground(new Color(51, 102, 255));
		nextButton.setFont(new Font("Verdana", Font.PLAIN, 13));
		nextButton.setBounds(168, 218, 112, 23);
		nextButton.addActionListener(new NextWordListener());
		creator.add(nextButton);
		
		/*
		 * Programos iðdëstymas
		 */
		add(creatorLabel, BorderLayout.NORTH);
		add(creator, BorderLayout.CENTER);
			creator.add(wordLabel);
			creator.add(word);
			creator.add(posLabel);
			creator.add(partOfSpeech);
			creator.add(translationLabel);
			creator.add(tScroller);
			creator.add(sentenceLabel);
			creator.add(sScroller);
	}
	
	/*
	 * Sekanèio þodþio mygtukas 
	 */
	public class NextWordListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Word nWord;
			if (!(sentence.getText()).isEmpty())
				nWord = new Word(word.getText(), translation.getText(), sentence.getText(), partOfSpeech.getText());
			else {
				nWord = new Word(word.getText(), translation.getText(), " ", partOfSpeech.getText());
			}
			wordList.add(nWord);
			clearWord();
		}
	}

	/*
	 * Þodþio apraðymo iðtrynimas, parengimas naujam þodþiui
	 */
	public void clearWord() {
		word.setText("");
		translation.setText("");
		sentence.setText("");
		partOfSpeech.setText("");
		word.requestFocus();
	}
	
	/*
	 * Þodþio iðsaugojimas masyve
	 */
	public void saveFile(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			for(Word w:wordList) {
				writer.write(w.getEnglish() + ".");
				writer.write(w.getLithuanian() + ".");
				writer.write(w.getSentence() + ".");
				writer.write(w.getPartOfSpeech());
				writer.newLine();
			}
			writer.close();
		} catch (IOException ex) {
			System.out.println("Iðspausdinti nepavyko");
			ex.printStackTrace();
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("all-lessons.ss", true));
			
			for(Word w:wordList) {
				writer.write(w.getEnglish() + ".");
				writer.write(w.getLithuanian() + ".");
				writer.write(w.getSentence() + ".");
				writer.write(w.getPartOfSpeech());
				writer.newLine();
			}
			writer.close();
		} catch (IOException ex) {
			System.out.println("Iðspausdinti nepavyko");
			ex.printStackTrace();
		}
	}
}
