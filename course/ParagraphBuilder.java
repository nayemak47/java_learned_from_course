package it.esedra.corso.journal;

public class ParagraphBuilder {
	private int id;
	private String text;
	private int idChapter;

	public ParagraphBuilder() {

	}

	public ParagraphBuilder(int id, String text,int idChapter) {
		super();
		this.id = id;
		this.text = text;
		this.idChapter= idChapter;

	}

	public Paragraph build() {
		return new Paragraph(id, text, idChapter);

	}

	public Integer getId() {
		return id;
	}

	public ParagraphBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public String getText() {
		return text;
	}

	public ParagraphBuilder setText(String text) {
		this.text = text;
		return this;
	}
	
	public int getIdChapter() {
		return idChapter;
	}
	
	public ParagraphBuilder setIdJournal(int idChapter) {
		this.idChapter = idChapter;
		return this;
	}

}
