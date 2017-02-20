package cn.mrdear.util.jniUtil;

import java.io.*;

/**
 * Contains helper methods for loading native libraries, particularly JNI.
 * 
 * @author gkubisa
 */
public class NativeLibraryLoader {
	/**
	 * Utility classes should not have a public constructor.
	 */
	private NativeLibraryLoader() { }
	
	/**
	 * Loads a native shared library. It tries the standard System.loadLibrary
	 * method first and if it fails, it looks for the library in the current
	 * class path. It will handle libraries packed within jar files, too.
	 *
	 * @param libraryName - name of the library to load
	 * @throws IOException if the library cannot be extracted from a jar file
	 * into a temporary file
	 */
	public static void loadLibrary(String libraryName) throws IOException {
		try {
			System.loadLibrary(libraryName);
		} catch (UnsatisfiedLinkError e) {
			String fileName = System.mapLibraryName(libraryName);

			int dotPosition = fileName.lastIndexOf('.');
			File file = File.createTempFile(fileName.substring(0, dotPosition), fileName.substring(dotPosition));
			file.deleteOnExit();
			
			byte[] buffer = new byte[4096];
			InputStream inputStream = NativeLibraryLoader.class.getClassLoader().getResourceAsStream(fileName);
			OutputStream outputStream = new FileOutputStream(file);
			
			try {
				while ( inputStream.available() > 0 ) {
					int StreamLength = inputStream.read(buffer);
					if ( StreamLength >= 0 ) {
						outputStream.write(buffer, 0, StreamLength);
					}
				}
			} finally {
				outputStream.close();
				inputStream.close();
			}
			
			System.load(file.getAbsolutePath());
		}
	}
}
