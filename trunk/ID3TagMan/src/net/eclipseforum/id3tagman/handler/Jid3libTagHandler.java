package net.eclipseforum.id3tagman.handler;

import java.io.File;
import java.io.IOException;

import net.eclipseforum.id3tagman.TagProperty.IDs;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

/**
 * http://javamusictag.sourceforge.net/
 * 
 * @author yeoupooh
 * 
 */
public class Jid3libTagHandler implements ITagHandler {

	private File file = null;
	private MP3File mp3file = null;

	public String getProperty(IDs id) throws TagPropertyException {
		String value = null;

		if (mp3file != null) {
			try {
				if (mp3file.hasID3v1Tag()) {
					switch (id) {
					case FileName:
						value = file.getName();
						break;

					case Album:
						value = mp3file.getID3v1Tag().getAlbum();
						break;

					case AlbumTitle:
						value = mp3file.getID3v1Tag().getAlbumTitle();
						break;

					case TrackNumber:
						value = mp3file.getID3v1Tag().getTrackNumberOnAlbum();
						break;

					case Authors:
						value = mp3file.getID3v1Tag().getAuthorComposer();
						break;

					case Comment:
						value = mp3file.getID3v1Tag().getComment();
						break;

					case Artist:
						value = mp3file.getID3v1Tag().getArtist();
						break;

					case Title:
						value = mp3file.getID3v1Tag().getSongTitle();
						break;

					case Genre:
						value = mp3file.getID3v1Tag().getSongGenre();
						break;
					}

				} else if (mp3file.hasID3v2Tag()) {
					switch (id) {
					case FileName:
						value = file.getName();
						break;

					case AlbumTitle:
						value = mp3file.getID3v2Tag().getAlbumTitle();
						break;

					case TrackNumber:
						value = mp3file.getID3v2Tag().getTrackNumberOnAlbum();
						break;

					case Authors:
						value = mp3file.getID3v2Tag().getAuthorComposer();
						break;

					case Comment:
						value = mp3file.getID3v2Tag().getSongComment();
						break;

					case Artist:
						value = mp3file.getID3v2Tag().getLeadArtist();
						break;

					case Title:
						value = mp3file.getID3v2Tag().getSongTitle();
						break;

					case Genre:
						value = mp3file.getID3v2Tag().getSongGenre();
						break;
					}
				} else {
					switch (id) {
					case FileName:
						value = file.getName();
					}
				}
			} catch (UnsupportedOperationException e) {
				throw new TagPropertyException(e);
			}
		}

		return value;
	}

	public void load(File file) throws TagHandlerException {
		this.file = file;

		try {
			mp3file = new MP3File(file);
		} catch (IOException e) {
			throw new TagHandlerException(e);
		} catch (TagException e) {
			throw new TagHandlerException(e);
		}
	}

	public void save() throws TagHandlerException {
		// TODO Auto-generated method stub

	}

	public void setProperty(IDs id, String value) throws TagPropertyException {
		// TODO Auto-generated method stub
	}

	public String getName() {
		return "Java ID3 Tag Library";
	}

}
