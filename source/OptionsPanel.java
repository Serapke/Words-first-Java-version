package zodziai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

public class OptionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField changeNameTF;
	private ButtonGroup rButtons;
	private JRadioButton lithuanianEnglishRB;
	private JRadioButton englishLithuanianRB;

	private JCheckBox sentenceCB;
	private JCheckBox saveStatsCB;
	private JCheckBox alwaysOnTopCB;

	private boolean showSentence;
	private boolean saveStats;
	private boolean alwaysOnTop;
	private boolean translateLithuanian;
	private boolean translateEnglish;
	private String nickName;


	public OptionsPanel(boolean sh, boolean sav, boolean a, boolean trLt, boolean trEn, String n) {

		showSentence = sh;
		saveStats = sav;
		alwaysOnTop = a;
		translateLithuanian = trLt;
		translateEnglish = trEn;
		nickName = n;

		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JLabel optionsLabel = new JLabel("Options");
		optionsLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel options = new JPanel();
		options.setBackground(new Color(255, 255, 153));
		options.setLayout(null);

		/*
		 * Translation option
		 * Lithuanian - English
		 * English - Lithuanian
		 */
		lithuanianEnglishRB = new JRadioButton("Lietuvi\u0173 - Angl\u0173");
		lithuanianEnglishRB.addItemListener(new ChooseEnglishListener());
		lithuanianEnglishRB.setBackground(new Color(255, 255, 153));
		lithuanianEnglishRB.setBounds(15, 33, 115, 23);

		englishLithuanianRB = new JRadioButton("Angl\u0173 - Lietuvi\u0173");
		englishLithuanianRB.addItemListener(new ChooseLithuanianListener());
		englishLithuanianRB.setBackground(new Color(255, 255, 153));
		englishLithuanianRB.setBounds(15, 7, 115, 23);

		rButtons = new ButtonGroup();
		rButtons.add(lithuanianEnglishRB);
		rButtons.add(englishLithuanianRB);
		lithuanianEnglishRB.setSelected(translateEnglish);
		englishLithuanianRB.setSelected(translateLithuanian);

		/*
		 * Option - to show example sentences or not
		 */
		sentenceCB = new JCheckBox("Show example sentences");
		sentenceCB.setSelected(showSentence);
		sentenceCB.addItemListener(new ShowSentenceListener());
		sentenceCB.setBackground(new Color(255, 255, 153));
		sentenceCB.setBounds(15, 71, 184, 23);

		/*
		 * Option - save statistics of lessons or not
		 */
		saveStatsCB = new JCheckBox("Save statistics");
		saveStatsCB.setSelected(saveStats);
		saveStatsCB.addItemListener(new SaveStatsListener());
		saveStatsCB.setBackground(new Color(255, 255, 153));
		saveStatsCB.setBounds(15, 97, 219, 23);

		/*
		 * Option - Words application always on top
		 */
		alwaysOnTopCB = new JCheckBox("Always on top");
		alwaysOnTopCB.setSelected(alwaysOnTop);
		alwaysOnTopCB.setBackground(new Color(255, 255, 153));
		alwaysOnTopCB.addItemListener(new AlwaysOnTopListener());
		alwaysOnTopCB.setBounds(15, 123, 115, 23);

		/*
		 * Change user name, which will be saved with statistics
		 */
		JLabel changeNameLabel = new JLabel("Change username");
		changeNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		changeNameLabel.setBounds(15, 161, 92, 20);

		changeNameTF = new JTextField();
		changeNameTF.setBounds(117, 161, 144, 20);
		changeNameTF.setColumns(10);

		JButton saveB = new JButton("Update");
		saveB.setForeground(new Color(255, 255, 255));
		saveB.setBackground(new Color(51, 102, 255));
		saveB.addActionListener(new saveOptionsListener());
		saveB.setBounds(175, 227, 102, 23);

		add(optionsLabel, BorderLayout.NORTH);
		add(options, BorderLayout.CENTER);
			options.add(lithuanianEnglishRB);
			options.add(englishLithuanianRB);
			options.add(sentenceCB);
			options.add(saveStatsCB);
			options.add(alwaysOnTopCB);
			options.add(changeNameLabel);
			options.add(changeNameTF);
			options.add(saveB);
	}

	public class ChooseEnglishListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				translateEnglish = true;
			else
				translateEnglish = false;
		}
	}

	public class ChooseLithuanianListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				translateLithuanian = true;
			else
				translateLithuanian = false;
		}
	}

	public class ShowSentenceListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				showSentence = true;
			else
				showSentence = false;
		}
	}

	public class SaveStatsListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				saveStats = true;
			else
				saveStats = false;
		}
	}

	public class AlwaysOnTopListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				alwaysOnTop = true;
			else
				alwaysOnTop = false;
		}
	}

	public class saveOptionsListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("options.txt"));
					writer.write(String.valueOf(translateLithuanian));
					writer.newLine();
					writer.write(String.valueOf(translateEnglish));
					writer.newLine();
					writer.write(String.valueOf(showSentence));
					writer.newLine();
					writer.write(String.valueOf(saveStats));
					writer.newLine();
					writer.write(String.valueOf(alwaysOnTop));
					writer.newLine();
					if (!changeNameTF.equals(null))
						writer.write(changeNameTF.getText());
					writer.newLine();
				writer.close();
			} catch (IOException ex) {
				System.out.println("Error while trying to update options!");
				ex.printStackTrace();
			}
		}
	}

	public boolean getTranslateLithuanian() {
		return translateLithuanian;
	}

	public boolean getTranslateEnglish() {
		return translateEnglish;
	}

	public boolean getShowSentence() {
		return showSentence;
	}

	public boolean getSaveStats() {
		return saveStats;
	}

	public boolean getAlwaysOnTop() {
		return alwaysOnTop;
	}

	public String getNickName() {
		return nickName;
	}
}
