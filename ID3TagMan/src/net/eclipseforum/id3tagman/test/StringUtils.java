package net.eclipseforum.id3tagman.test;
import java.io.UnsupportedEncodingException;

public class StringUtils {
	public static String convertStr(String srcStr) {
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

}
