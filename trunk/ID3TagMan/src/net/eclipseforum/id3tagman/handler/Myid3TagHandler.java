package net.eclipseforum.id3tagman.handler;

import java.io.File;
import java.io.IOException;

import net.eclipseforum.id3tagman.TagProperty.IDs;

import org.cmc.music.common.ID3ReadException;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

/**
 * NOT WORKING PROPERLY
 * http://www.fightingquaker.com/myid3/
 * 
 * @author yeoupooh
 *
 */
public class Myid3TagHandler implements ITagHandler {

	private File file = null;
	private MusicMetadataSet metadata = null;

	public String getProperty(IDs id) throws TagPropertyException {
		String value = null;

		try {
			if (metadata != null) {
				switch (id) {
				case FileName:
					value = file.getName();
					break;

				case Album:
					value = metadata.getSimplified().getAlbum();
					break;

				case Artist:
					value = metadata.getSimplified().getArtist();
					break;

				case Genre:
					value = metadata.getSimplified().getGenreName();
					break;

				case Title:
					value = metadata.getSimplified().getSongTitle();
					break;

				case TrackNumber:
					value = metadata.getSimplified().getTrackNumberFormatted();
					break;

				case Authors:
					value = metadata.getSimplified().getComposer();
					break;

				case Comment:
					value = metadata.getSimplified().getComments().toString();
					break;

				}
			}
		} catch (Exception e) {
			throw new TagPropertyException(e);
		}

		return value;
	}

	public void load(File file) throws TagHandlerException {
		this.file = file;

		try {
			metadata = new MyID3().read(file);
		} catch (ID3ReadException e) {
			throw new TagHandlerException(e);
		} catch (IOException e) {
			throw new TagHandlerException(e);
		} catch (Exception e) {
			throw new TagHandlerException(e);
		}
	}

	public void save() throws TagHandlerException {
		// TODO Auto-generated method stub

	}

	public void setProperty(IDs id, String value) throws TagPropertyException {
		// TODO Auto-generated method stub
	}

}
