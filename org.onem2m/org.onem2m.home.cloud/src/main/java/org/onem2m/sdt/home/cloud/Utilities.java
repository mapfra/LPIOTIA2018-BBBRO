package org.onem2m.sdt.home.cloud;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utilities {

	/**
	 * Instantiates a new string utilities.
	 */
	private Utilities() {
	}

	/**
	 * Checks the case-dependent equality of 2 objects (either both null or same
	 * strings).
	 * 
	 * @param s1
	 *            the first object
	 * @param s2
	 *            the second object
	 * @return true, if successful
	 */
	public static boolean equals(final Object s1, final Object s2) {
		return (s1 == null) ? (s2 == null) : s1.equals(s2);
	}

	/**
	 * Checks if a string is null or empty.
	 * 
	 * @param str
	 *            the string
	 * @return true, if is null or empty
	 */
	public static boolean isEmpty(final String str) {
		return (str == null) || str.trim().equals("");
	}

	public static boolean isEmpty(final Object obj) {
		return (obj == null) || obj.toString().trim().equals("");
	}

	/**
	 */
	public static boolean isEmpty(final Collection coll) {
		return (coll == null) || coll.isEmpty();
	}

	/**
	 */
	public static boolean isEmpty(final Object[] coll) {
		return (coll == null) || (coll.length == 0);
	}

	/**
	 * Checks if a string is a non empty string.
	 * 
	 * @param str
	 *            the string
	 * @return true, if is not empty
	 */
	public static boolean isNotEmpty(final String str) {
		return (str != null) && !str.trim().equals("");
	}

	/**
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String readLine(final InputStream in) throws IOException {
		return readLine(new InputStreamReader(in, "UTF-8"));
	}
	
	public static String readLine(final InputStream in, final boolean skipComments) throws IOException {
		return readLine(new InputStreamReader(in, "UTF-8"), skipComments);
	}

	public static String readLine(final Reader in) throws IOException {
		return readLine(in, false);
	}
	
	public static String readLine(final Reader in, final boolean skipComments) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(in);
			StringBuffer result = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				if (! (skipComments && (line.startsWith("#") || line.trim().startsWith("//"))))
					result.append(line);
			}
			return result.toString();
		} finally {
			try {
				br.close();
			} catch (Exception ignored) {
			}
		}
	}

	public static void copyToFile(final InputStream in, final File out)
		throws IOException {
		try {
			OutputStream fos = new FileOutputStream(out);
			try {
				copyInOut(in, fos);
			} finally {
				try { fos.close(); }
				catch(Exception igored) {}
			}
		} finally {
			try { in.close(); }
			catch(Exception igored) {}
		}
	}

	/**
	 * Copies an input stream into an output stream.
	 * 
	 * @param in
	 *            the input stream
	 * @param out
	 *            the output stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void copyInOut(final InputStream in, final OutputStream out)
			throws IOException {
		BufferedInputStream bis = new BufferedInputStream(in);
		BufferedOutputStream bos = new BufferedOutputStream(out);
		byte[] c = new byte[512];
		int number = 0;
		while ((number = bis.read(c)) != -1) {
			bos.write(c, 0, number);
		}
		bos.flush();
	}

	public static List readLines(final Reader in, final boolean skipComments) throws IOException {
		BufferedReader br = null;
		List ret = new ArrayList();
		try {
			br = new BufferedReader(in);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (! (skipComments && (line.equals("") || line.startsWith("#") || line.startsWith("//"))))
					ret.add(line);
			}
			return ret;
		} finally {
			try {
				br.close();
			} catch (Exception ignored) {
			}
		}
	}

}
