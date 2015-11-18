package zodziai;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;

public class StatisticsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int count;
	private int err;
	private int corr;
	private double mark;
	private String nickName;

	/**
	 * Create the panel.
	 */
	public StatisticsPanel(int max, int mis, String nick) {
		DecimalFormat newFormat = new DecimalFormat("#.#");
		
		nickName = nick;
		count = max + 1;
		err = mis;
		corr = count - err;
		System.out.println(count);
		System.out.println(corr);
		mark = corr * 100 / count;
		
		double result =  Double.valueOf(newFormat.format(mark));
		
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		JLabel lblStatistika = new JLabel("INFO");
		lblStatistika.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatistika.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblStatistika.setBounds(179, 11, 68, 29);
		add(lblStatistika);
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(Color.DARK_GRAY);
		textArea.setFont(new Font("Arial", Font.BOLD, 12));
		textArea.setRows(10);
		textArea.setBounds(113, 51, 204, 51);
		textArea.setText("Words Count:\t\t" + count + "\n" +
						 "Correct Answers:\t" + corr + "\n" +
						 "Mistakes:\t\t" + err);
		add(textArea);
		
		JLabel markLabel = new JLabel("J\u016Bs\u0173 pa\u017Eymys:");
		markLabel.setFont(new Font("Arial", Font.BOLD, 13));
		markLabel.setForeground(Color.DARK_GRAY);
		markLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		markLabel.setBounds(113, 113, 134, 29);
		add(markLabel);
		
		JLabel markL = new JLabel("");
		markL.setFont(new Font("Lucida Console", Font.BOLD, 16));
		markL.setForeground(Color.RED);
		markL.setHorizontalAlignment(SwingConstants.LEFT);
		markL.setBounds(257, 113, 60, 29);
		markL.setText("" + result);
		add(markL);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public StatisticsPanel(int max, int mis) {
DecimalFormat newFormat = new DecimalFormat("#.#");
	
		count = max;
		err = mis;
		corr = count - err;
		System.out.println(count);
		System.out.println(corr);
		mark = corr * 100 / count;
		
		double result =  Double.valueOf(newFormat.format(mark));
		
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		JLabel lblStatistika = new JLabel("INFO");
		lblStatistika.setForeground(new Color(0, 0, 102));
		lblStatistika.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatistika.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblStatistika.setBounds(143, 30, 163, 33);
		add(lblStatistika);
		
		JTextArea textArea = new JTextArea();
		textArea.setForeground(Color.DARK_GRAY);
		textArea.setFont(new Font("Arial", Font.BOLD, 12));
		textArea.setRows(10);
		textArea.setBounds(113, 65, 204, 51);
		textArea.setText("Words Count:\t\t" + count + "\n" +
						 "Correct Answers:\t" + corr + "\n" +
						 "Mistakes:\t\t" + err);
		add(textArea);
		
		JLabel markLabel = new JLabel("J\u016Bs\u0173 pa\u017Eymys:");
		markLabel.setFont(new Font("Arial", Font.BOLD, 13));
		markLabel.setForeground(Color.DARK_GRAY);
		markLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		markLabel.setBounds(113, 123, 134, 29);
		add(markLabel);
		
		JLabel markL = new JLabel("");
		markL.setFont(new Font("Lucida Console", Font.BOLD, 16));
		markL.setForeground(Color.RED);
		markL.setHorizontalAlignment(SwingConstants.LEFT);
		markL.setBounds(257, 123, 60, 29);
		markL.setText("" + result);
		add(markL);
	}
	
	public void saveStatistics() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("statistics.ss", true));
				writer.write(nickName + ".");
				writer.write(String.valueOf((int) mark));
				writer.newLine();
			writer.close();
		} catch (IOException ex) {
			System.out.println("Iðspausdinti nepavyko");
			ex.printStackTrace();
		}
	}
}
