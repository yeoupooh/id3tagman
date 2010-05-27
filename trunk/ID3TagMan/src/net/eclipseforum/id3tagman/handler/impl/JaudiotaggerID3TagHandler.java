package net.eclipseforum.id3tagman.handler.impl;

import java.io.File;
import java.io.IOException;

import net.eclipseforum.id3tagman.TagProperty.IDs;
import net.eclipseforum.id3tagman.handler.ID3TagHandlerException;
import net.eclipseforum.id3tagman.handler.ID3TagHandlerIdNotFoundException;
import net.eclipseforum.id3tagman.handler.IID3TagHandler;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagFieldKey;

public class JaudiotaggerID3TagHandler implements IID3TagHandler {

	private MP3File mp3File = null;
	private Tag tag = null;

	public String getProperty(IDs id) throws ID3TagHandlerException {
		try {
			switch (id) {
			case FileName:
				return mp3File.getFile().getName();
			case Title:
				return tag.getFirst(TagFieldKey.TITLE);
			case Album:
				return tag.getFirst(TagFieldKey.ALBUM);
			case Artist:
				return tag.getFirst(TagFieldKey.ARTIST);
			case Genre:
				return tag.getFirst(TagFieldKey.GENRE);
			case TrackNumber:
				return tag.getFirst(TagFieldKey.TRACK);
			case AlbumArtist:
				return tag.getFirst(TagFieldKey.ALBUM_ARTIST);
			case Composer:
				return tag.getFirst(TagFieldKey.COMPOSER);
			case Comment:
				return tag.getFirst(TagFieldKey.COMMENT);
			case Year:
				return tag.getFirst(TagFieldKey.YEAR);
			}
		} catch (UnsupportedOperationException e) {
			throw new ID3TagHandlerException(e);
		}

		throw new ID3TagHandlerIdNotFoundException(id);
	}

	public void load(String fileName) throws ID3TagHandlerException {
		try {
			mp3File = (MP3File) AudioFileIO.read(new File(fileName));
			tag = mp3File.getTag();
		} catch (CannotReadException e) {
			e.printStackTrace();
			throw new ID3TagHandlerException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ID3TagHandlerException(e);
		} catch (TagException e) {
			e.printStackTrace();
			throw new ID3TagHandlerException(e);
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
			throw new ID3TagHandlerException(e);
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
			throw new ID3TagHandlerException(e);
		}
	}

	public void save() throws ID3TagHandlerException {
		try {
			mp3File.save();
		} catch (IOException e) {
			throw new ID3TagHandlerException(e);
		} catch (TagException e) {
			throw new ID3TagHandlerException(e);
		}
	}

	public void setProperty(IDs id, String value) throws ID3TagHandlerException {
		try {
			switch (id) {
			case FileName:
				// do nothing
				break;
			case Title:
				tag.setTitle(value);
				break;
			case Artist:
				tag.setArtist(value);
				break;
			case Album:
				tag.setAlbum(value);
				break;
			case Genre:
				tag.setGenre(value);
				break;
			case TrackNumber:
				tag.setTrack(value);
				break;
			case AlbumArtist:
				tag.setArtist(value);
				break;
			case Composer:
				// do nothing
				break;
			case Comment:
				tag.setComment(value);
				break;
			case Year:
				tag.setYear(value);
				break;
			}
		} catch (FieldDataInvalidException e) {
			throw new ID3TagHandlerException(e);
		}
	}

}
