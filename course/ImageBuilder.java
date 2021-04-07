package it.esedra.corso.journal;

public class ImageBuilder {

	private int id;
	private String src;
	private String name;
	private int idParagraph;

	public ImageBuilder() {

	}

	public ImageBuilder(int id, String src, String name, int idParagraph) {
		super();
		this.id = id;
		this.src = src;
		this.name = name;
		this.idParagraph = idParagraph;

	}

	public Image build() {
		return new Image(id, src, name, idParagraph);
	}

	public int getId() {
		return id;
	}

	public ImageBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public String getSrc() {
		return src;
	}

	public ImageBuilder setSrc(String src) {
		this.src = src;
		return this;
	}

	public String getName() {
		return name;
	}

	public ImageBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public int getIdParagraph() {
		return idParagraph;
	}

	public ImageBuilder setIdParagraph(int idParagraph) {
		this.idParagraph = idParagraph;
		return this;
	}

}
