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
		/*
		 * Buvusiø reikðmiø atkûrimas
		 * Skaitomos true ir false reikðmës
		 */
		showSentence = sh;
		saveStats = sav;
		alwaysOnTop = a;
		translateLithuanian = trLt;
		translateEnglish = trEn;
		nickName = n;
		
		/*
		 * Panelës nustatymai
		 */
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));
		
		/*
		 * Menu srities pavadinimas
		 */
		JLabel optionsLabel = new JLabel("NUSTATYMAI");
		optionsLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*
		 * Veiksni panelë
		 */
		JPanel options = new JPanel();
		options.setBackground(new Color(255, 255, 153));
		options.setLayout(null);
		
		/*
		 * Vertimo pasirinkimai
		 * Lietuviø - anglø
		 * Anglø - lietuviø
		 * Galimybë pasirinkt tik vienà
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
		 * Pasirinkimas rodyti pavyzdinius sakinius ar ne
		 */
		sentenceCB = new JCheckBox("Rodyti sakinius");
		sentenceCB.setSelected(showSentence);
		sentenceCB.addItemListener(new ShowSentenceListener());
		sentenceCB.setBackground(new Color(255, 255, 153));
		sentenceCB.setBounds(15, 71, 184, 23);
		
		/*
		 * Pasirinkimas saugoti pamokø rezultatus faile (vëliau duomenø bazëje) ar ne
		 */
		saveStatsCB = new JCheckBox("I\u0161saugoti rezultatus");
		saveStatsCB.setSelected(saveStats);
		saveStatsCB.addItemListener(new SaveStatsListener());
		saveStatsCB.setBackground(new Color(255, 255, 153));
		saveStatsCB.setBounds(15, 97, 219, 23);
		
		/*
		 * Pasirinkimas visada rodyti programà virð kitø langø
		 */
		alwaysOnTopCB = new JCheckBox("Visada virðuje");
		alwaysOnTopCB.setSelected(alwaysOnTop);
		alwaysOnTopCB.setBackground(new Color(255, 255, 153));
		alwaysOnTopCB.addItemListener(new AlwaysOnTopListener());
		alwaysOnTopCB.setBounds(15, 123, 115, 23);
		
		/*
		 * Keisti vartotojo vardà, kuris bus naudojamas statistikø vaizdinëse pateiktyse
		 */
		JLabel changeNameLabel = new JLabel("Pakeisti vard\u0105");
		changeNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		changeNameLabel.setBounds(15, 161, 92, 20);
		
		changeNameTF = new JTextField();
		changeNameTF.setBounds(117, 161, 144, 20);
		changeNameTF.setColumns(10);
		
		/*
		 * Uþsaugojimo mygtukas
		 * Saugo duomenis faile 'options.ss'
		 */
		JButton saveB = new JButton("U\u017Esaugoti");
		saveB.setForeground(new Color(255, 255, 255));
		saveB.setBackground(new Color(51, 102, 255));
		saveB.addActionListener(new saveOptionsListener());
		saveB.setBounds(175, 227, 102, 23);
		
		/*
		 * Programos iðdëstymas
		 */
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
	
	/*
	 * Paþymëtas 'Rodyti sakinius' parametras
	 */
	public class ShowSentenceListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				showSentence = true;
			else 
				showSentence = false;	
		}
	}
	/*
	 * Paþymëtas 'Saugoti pamokø rezultatus' parametras
	 */
	public class SaveStatsListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				saveStats = true;
			else 
				saveStats = false;	
		}
	}
	
	/*
	 * Paþymëtas 'Visada virðuje' parametras
	 */
	public class AlwaysOnTopListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				alwaysOnTop = true;
			else 
				alwaysOnTop = false;
		}
	}
	
	/*
	 * Paspaustas saugojimo mygtukas
	 * Parametrai uþsaugomi faile 'options.ss'
	 */
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
				System.out.println("Iðspausdinti nepavyko");
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
