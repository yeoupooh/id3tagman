package net.eclipseforum.id3tagman.handler;

import java.io.File;
import java.io.IOException;

import net.eclipseforum.id3tagman.TagProperty.IDs;
import de.vdheide.mp3.FrameDamagedException;
import de.vdheide.mp3.ID3Exception;
import de.vdheide.mp3.ID3v2DecompressionException;
import de.vdheide.mp3.ID3v2Exception;
import de.vdheide.mp3.ID3v2IllegalVersionException;
import de.vdheide.mp3.ID3v2WrongCRCException;
import de.vdheide.mp3.MP3File;
import de.vdheide.mp3.NoMP3FrameException;
import de.vdheide.mp3.TagContent;
import de.vdheide.mp3.TagFormatException;

/**
 * http://developer.berlios.de/projects/javamp3/
 * 
 * @author yeoupooh
 *
 */
public class Javamp3TagHandler implements ITagHandler {

	private MP3File mp3file = null;
	private File file = null;

	public String getProperty(IDs id) throws TagPropertyException {
		String value = null;

		if (mp3file != null) {
			try {
				switch (id) {
				case FileName:
					value = file.getName();
					break;

				case Album:
					value = mp3file.getAlbum().getTextContent();
					break;

				case Artist:
					value = mp3file.getArtist().getTextContent();
					break;

				case Genre:
					value = mp3file.getGenre().getTextContent();
					break;

				case Title:
					value = mp3file.getTitle().getTextContent();
					break;

				case TrackNumber:
					value = mp3file.getTrack().getTextContent();
					break;

				case Authors:
					value = mp3file.getComposer().getTextContent();
					break;

				case Comment:
					value = mp3file.getComments().getTextContent();
					break;

				}
			} catch (FrameDamagedException e) {
				throw new TagPropertyException(e);
			}
		}

		return value;
	}

	public void load(File file) throws TagHandlerException {
		this.file = file;

		try {
			mp3file = new MP3File(file.getAbsolutePath());
		} catch (ID3v2WrongCRCException e) {
			throw new TagHandlerException(e);
		} catch (ID3v2DecompressionException e) {
			throw new TagHandlerException(e);
		} catch (ID3v2IllegalVersionException e) {
			throw new TagHandlerException(e);
		} catch (IOException e) {
			throw new TagHandlerException(e);
		} catch (NoMP3FrameException e) {
			throw new TagHandlerException(e);
		} catch (Exception e) {
			throw new TagHandlerException(e);
		}
	}

	public void save() throws TagHandlerException {
		try {
			TagContent content = new TagContent();
			content.setContent("UTF-8");
			mp3file.setEncodedBy(content);
			mp3file.update();
		} catch (ID3Exception e) {
			throw new TagHandlerException(e);
		} catch (ID3v2Exception e) {
			throw new TagHandlerException(e);
		} catch (TagFormatException e) {
			throw new TagHandlerException(e);
		}
	}

	public void setProperty(IDs id, String value) throws TagPropertyException {
		if (mp3file != null && mp3file.canWrite() == true && value != null
				&& value.length() > 0) {
			TagContent content = new TagContent();
			content.setContent(value);
			try {
				switch (id) {
				case FileName:
					// value = file.getName();
					break;

				case Album:
					mp3file.setAlbum(content);
					break;

				case Artist:
					mp3file.setArtist(content);
					break;

				case Genre:
					mp3file.setGenre(content);
					break;

				case Title:
					mp3file.setTitle(content);
					break;

				case TrackNumber:
					mp3file.setTrack(content);
					break;

				case Authors:
					mp3file.setComposer(content);
					break;

				case Comment:
					System.out.println("commenttype="
							+ mp3file.getComments().getType());
					mp3file.setComments(content);
					break;
				}

			} catch (TagFormatException e) {
				System.err.println("id=" + id);
				throw new TagPropertyException(e);
			} catch (Exception e) {
				throw new TagPropertyException(e);
			}

		}
	}

}
