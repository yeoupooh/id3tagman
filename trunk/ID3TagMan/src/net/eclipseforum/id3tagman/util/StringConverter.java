package net.eclipseforum.id3tagman.util;
import java.io.UnsupportedEncodingException;

public class StringConverter {
	private String srcEncoding = null;
	private String destEncoding = null;

	public StringConverter(String srcEncoding, String destEncoding) {
		this.srcEncoding = srcEncoding;
		this.destEncoding = destEncoding;
	}

	public String convert(String srcStr) {
		byte[] srcBytes;
		String destStr = null;
		try {
			srcBytes = srcStr.getBytes(srcEncoding);
			destStr = new String(srcBytes, destEncoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return destStr;
	}

}
