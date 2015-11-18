package zodziai;

public class Word {
	
	private String eng;			// Angli�kas �odis
	private String lt;			// Vertimas (galimi keli �od�iai)
	private String sentence;	// Pavyzdinis sakinys, kuriame panaudotas �odis
	private String pos;			// �od�io kalbos dalis
	
	// Pagrindinis konstruktorius
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
