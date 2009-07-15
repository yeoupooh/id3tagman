package net.eclipseforum.id3tagman.test;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;


public class TestReadID3TagFromMp3File {

	public static String convertStr(String srcStr)
	{
		byte[] srcBytes;
		String destStr = null;
		try {
			srcBytes = srcStr.getBytes("iso-8859-1");
			destStr = new String(srcBytes, "euc-kr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return destStr;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    File sourceFile = new File("/Users/yeoupooh/Music/Favorites/2008-07/02. 에너지 (Feat. 선예).mp3");
	    try {
			MP3File mp3file = new MP3File(sourceFile);
			System.out.println(convertStr(mp3file.getID3v1Tag().getArtist()));
			System.out.println(convertStr(mp3file.getID3v1Tag().getAlbum()));
			System.out.println(convertStr(mp3file.getID3v1Tag().getAlbumTitle()));
			//System.out.println(convertStr(mp3file.getID3v1Tag().getAuthorComposer()));
			System.out.println(mp3file.getID3v1Tag().getGenre());
			System.out.println(mp3file.getID3v1Tag().getSongGenre());
			System.out.println(convertStr(mp3file.getID3v1Tag().getSongTitle()));
			//System.out.println(mp3file.getID3v1Tag().getSongLyric());
			System.out.println(mp3file.getID3v1Tag().getSongComment());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
