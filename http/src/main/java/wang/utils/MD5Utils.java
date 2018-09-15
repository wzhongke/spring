package wang.utils;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class MD5Utils {
	private static MessageDigest messagedigest = null;
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.err.println(MD5Utils.class.getName()
				+ "初始化失败，MessageDigest不支持MD5Util。");
			e.printStackTrace();
		}
	}

	public static String getNumString(String s, int num) {

		StringBuilder sb = new StringBuilder();
		for(int i = s.length(); i < num; ++i) {
			sb.append('0');
		}
		sb.append(s);
		return sb.toString();
	}


	/**
	 * 压缩或解压zip：
	 * 由于直接使用java.util.zip工具包下的类，会出现中文乱码问题，所以使用ant.jar中的org.apache.tools.zip下的工具类
	 * @param filePath zip文件所在的位置
	 * @param destPath 解压之后的位置
	 */
	public static void unzip(String filePath, String destPath) throws IOException {
		ZipFile zipFile = new ZipFile(filePath, "GBK");
		try {
			File rootDir = new File(destPath);
			if (rootDir.exists()) {
				rootDir.delete();
			}
			for (Enumeration entries = zipFile.getEntries(); entries.hasMoreElements(); ) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				File file = new File(destPath + File.separator + entry.getName());
				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					File parent = file.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					InputStream in = zipFile.getInputStream(entry);
					OutputStream out = new FileOutputStream(file);
					out.close();
				}
			}
		} finally {
			try {zipFile.close();} catch (Exception e){}
		}
	}

	public static void zip(String sourceDir, String zipfile) throws IOException {
		File dir = new File(sourceDir);
		if (!dir.isDirectory()) return;
		File [] files = dir.listFiles();
		if (files == null || files.length == 0) return ;
		List<File> srcfile = Arrays.asList(files);
		byte[] buf = new byte[1024];
		try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile))){
			// Create the ZIP file
			// Compress the files
			for (int i = 0; i < srcfile.size(); i++) {
				File file = srcfile.get(i);
				FileInputStream in = new FileInputStream(file);
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(file.getName()));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getFileMd5(String file) {
		try (FileInputStream in = new FileInputStream(file)) {
			return getFileMd5(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getFileMd5(File file) {
		try (FileInputStream in = new FileInputStream(file)) {
			return getFileMd5(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String getFileMd5(FileInputStream in) throws IOException {
		byte [] buffer = new byte[1024];
		int numRead = 0;
		while ((numRead = in.read(buffer)) > 0) {
			messagedigest.update(buffer, 0, numRead);
		}
		return bufferToHex(messagedigest.digest());
	}

	public static String getByteMd5 (byte [] bytes) {
		messagedigest.update(bytes, 0, bytes.length);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
		char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	/**
	 * 递归删除文件夹及其下面的文件
	 */
	public static boolean deleteDir(File dir) {
		//循环遍历待删除目录下的文件
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				//采用递归方式，即使待删除文件夹下还有多层目录，也会递归先都删除掉
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	public static boolean deleteDirChild(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files == null || files.length == 0) return true;
			for (File f: files) {
				deleteDir(f);
			}
		}
		return true;
	}

	public static  String runShell(String cmd) throws IOException {
		Process process = Runtime.getRuntime().exec("dos2unix ");
		InputStream fis = process.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line ;
		StringBuilder buffer = new StringBuilder();
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}
}
