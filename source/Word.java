package zodziai;

public class Word {

	private String eng;			// English translaiton of the word
	private String lt;			// Lithuanian translation of the word (many possible translations separated by commas)
	private String sentence;	// Example sentence of the word
	private String pos;			// Part of speech

	public Word(String a, String b, String c, String d) {
		eng = a;
		lt = b;
		sentence = c;
		pos = d;
	}

	public void setEnglish(String a) {
		eng = a;
	}

	public String getEnglish() {
		return eng;
	}

	public void setLithuanian(String b) {
		lt = b;
	}

	public String getLithuanian() {
		return lt;
	}

	public void setSentence(String c) {
		sentence = c;
	}

	public String getSentence() {
		return sentence;
	}

	public void setPartOfSpeech(String d) {
		pos = d;
	}

	public String getPartOfSpeech() {
		return pos;
	}
}
