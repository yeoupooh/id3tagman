package net.eclipseforum.id3tagman;
public class TagProperty {
	public enum IDs {
		FileName(0), Title(1), TrackNumber(2), Artist(3), Album(4), AlbumTitle(
				5), Genre(6), Authors(7), Comment(8);

		private int index;

		IDs(int index) {
			this.index = index;
		}

		public int index() {
			return this.index;
		}
	};

	private IDs id;
	private String text;
	private int width;

	public TagProperty(IDs id, String text, int width) {
		this.id = id;
		this.text = text;
		this.width = width;
	}

	public IDs getId() {
		return id;
	}

	public void setId(IDs id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
