package it.esedra.corso.journal;

public class VideoBuilder {

	private int id;
	private String src;
	private String name;
	private String title;
	private int idParagraph;

	public VideoBuilder() {

	}

	public VideoBuilder(int id, String src, String name, String title, int idParagraph) {
		super();
		this.id = id;
		this.src = src;
		this.name = name;
		this.title = title;
		this.idParagraph = idParagraph;
		

	}

	public Video build() {
		return new Video(id, src, name, title, idParagraph);
	}

	public int getId() {
		return id;
	}

	public VideoBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public String getSrc() {
		return src;
	}

	public VideoBuilder setSrc(String src) {
		this.src = src;
		return this;
	}

	public String getName() {
		return name;
	}

	public VideoBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public VideoBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public int getIdParagraph() {
		return idParagraph;
	}

	public VideoBuilder setIdParagraph(int idParagraph) {
		this.idParagraph = idParagraph;
		return this;
	}
	

}
