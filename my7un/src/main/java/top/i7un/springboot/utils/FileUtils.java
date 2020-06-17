package top.i7un.springboot.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentSkipListSet;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @ClassName: FileUtils
 * @Description: 文件操作
 * @author gaoyuxin
 * @date 2018年4月26日 下午4:42:15
 */
public final class FileUtils {

	private final static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	private static Set<String> readingFilenames = new ConcurrentSkipListSet<String>();
	private static final String DOWNLOAD_FILE_FUFFIX = ".download";

	/**
	 * 校验文件是否存在，如不存在则创建 FileUtils.checkFile()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @param filePath
	 *            文件绝对路径
	 * @return File.createNewFile()
	 */
	public static boolean checkFile(String filePath) {
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 获得classPath根路径 FileUtils.getClassPath()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @return
	 */
	public static String getClassPath() {
		String classPath = FileUtils.class.getResource("/").getPath();
		// windows系统
		if (isWindowsSystem()) {
			return classPath.substring(1);
		}
		// 非windows系统
		else {
			return classPath;
		}
	}

	/**
	 * 校验是否是windows操作系统 FileUtils.isWindowsSystem()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @return
	 */
	public static boolean isWindowsSystem() {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		return os.toLowerCase().startsWith("win");
	}

	/**
	 * 关闭流 FileUtils.closeStream()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @param in
	 * @param out
	 */
	public static void closeStream(InputStream in, OutputStream out) {
		closeStream(in);
		closeStream(out);
	}
	/**
	 * 
	* @methodName: strChangeXML  
	* @Description: xml格式的字符串转换成xml格式的文档   //代码很low 如果后面 有大佬看见 可以帮我改改 
	* @param @param str
	* @param @param pathAndName
	* @param @throws IOException    参数  
	* @return void    返回类型  
	* @author zhaojun 
	* @throws
	 */
	public static void strChangeXML(String str,String pathAndName) throws IOException {
        SAXReader saxReader = new SAXReader();
        Document document;
        try {
         document = saxReader.read(new ByteArrayInputStream(str.getBytes("UTF-8")));
         OutputFormat format = OutputFormat.createPrettyPrint();
         /** 将document中的内容写入文件中 */
         XMLWriter writer = new XMLWriter(new FileWriter(new File(pathAndName)),format);
         writer.write(document);
         writer.close();
        } catch (DocumentException e) {
         e.printStackTrace();
        }
	 }
	
	
	/**
	 * 关闭流 FileUtils.closeStream()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @param in
	 */
	public static void closeStream(InputStream in) {
		if (in == null) {
			return;
		}
		try {
			in.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			in = null;
		} finally {
			in = null;
		}
	}

	/**
	 * 关闭流 FileUtils.closeReader()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @param readers
	 */
	public static void closeReader(Reader... readers) {
		if (readers == null) {
			return;
		}
		for (Reader obj : readers) {
			if (obj == null)
				continue;
			try {
				obj.close();
			} catch (Exception e) {
				obj = null;
				logger.error(e.getMessage(), e);
			} finally {
				obj = null;
			}
		}
	}

	/**
	 * 关闭流 FileUtils.closeStream()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @param out
	 */
	public static void closeStream(OutputStream out) {
		if (out == null) {
			return;
		}
		try {
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			out = null;
		} finally {
			out = null;
		}
	}

	/**
	 * 关闭流 FileUtils.closeWriter()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @param writers
	 */
	public static void closeWriter(Writer... writers) {
		if (writers == null) {
			return;
		}
		for (Writer obj : writers) {
			if (obj == null)
				continue;
			try {
				obj.close();
			} catch (Exception e) {
				obj = null;
				logger.error(e.getMessage(), e);
			} finally {
				obj = null;
			}
		}
	}

	/**
	 * 保存文件 FileUtils.saveFile()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @param content
	 * @param absPath
	 */
	public static void saveFile(String content, String absPath) throws Exception {
		FileWriter writer = null;
		BufferedWriter buffer = null;
		try {
			checkFile(absPath);
			writer = new FileWriter(absPath);
			buffer = new BufferedWriter(writer);
			buffer.write(content);
			writer.flush();
			buffer.flush();
		} catch (Exception e) {
			throw new Exception("保存文件" + absPath + "失败", e);
		} finally {
			closeWriter(writer, buffer);
		}
	}

	/**
	 * 删除文件 FileUtils.saveFile()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @param absPath
	 */
	public static boolean removeFile(String absPath) {
		if (absPath == null)
			return true;
		File file = new File(absPath);
		if (!file.exists()) {
			return true;
		}
		if (!file.isDirectory()) {
			file.delete();
			return true;
		}
		File[] childrens = file.listFiles();
		if (childrens != null) {
			for (File children : childrens) {
				removeFile(children.getAbsolutePath());
			}
		}
		return file.delete();
	}

	/**
	 * 读取文件 FileUtils.readFile()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-16
	 * </P>
	 * 
	 * @param absPath
	 * @param topLine
	 *            默认0，读取所有行
	 * @return 文件不存在时返回NULL
	 * @throws Exception
	 */
	public static String readFile(String absPath, int topLine) throws Exception {
		if (absPath == null)
			return null;
		File file = new File(absPath);
		if (!file.exists()) {
			return null;
		}
		if (!file.isFile()) {
			return null;
		}
		if ((System.currentTimeMillis() - file.lastModified()) <= 100) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		BufferedReader buffer = null;
		FileReader reader = null;
		StringBuffer stringBuffer = new StringBuffer("");
		String tempString = "";
		try {
			reader = new FileReader(file);
			buffer = new BufferedReader(reader);
			int line = 1;
			while ((tempString = buffer.readLine()) != null) {
				line++;
				stringBuffer.append(tempString);
				if (topLine != 0 && line >= topLine) {
					break;
				}
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + absPath + "失败", e);
		} finally {
			closeReader(reader, buffer);
		}
		return stringBuffer.toString();
	}

	/**
	 * 读取文件或目录下的随机文件 FileUtils.readRandomFile()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-18
	 * </P>
	 * 
	 * @param absPath
	 * @param excludeFileName
	 * @param topLine
	 * @return
	 * @throws Exception
	 */
	public static String readRandomFile(String absPath, final String excludeFileName, int topLine,
			boolean deleteAfterRead) throws Exception {
		String[] contents = readRandomFileFullInfo(absPath, excludeFileName, topLine, deleteAfterRead);
		if (contents == null || contents.length < 2) {
			return null;
		}
		return contents[1];
	}

	/**
	 * 读取文件或目录下的随机文件 FileUtils.readRandomFile()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-18
	 * </P>
	 * 
	 * @param absPath
	 * @param excludeFileName
	 * @param topLine
	 * @return
	 * @throws Exception
	 */
	public static String[] readRandomFileArr(String absPath, final String excludeFileName, int topLine,
			boolean deleteAfterRead) throws Exception {
		String[] contents = readRandomFileFullInfo(absPath, excludeFileName, topLine, deleteAfterRead);
		if (contents == null || contents.length < 2) {
			return null;
		}
		return contents;
	}

	/**
	 * 读取文件或目录下的随机文件 FileUtils.readRandomFile()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-18
	 * </P>
	 * 
	 * @param absPath
	 * @param excludeFileName
	 * @param topLine
	 * @return
	 * @throws Exception
	 */
	public static String[] readRandomFileRe(String absPath, final String excludeFileName, int topLine,
			boolean deleteAfterRead) throws Exception {
		String[] contents = readRandomFileFullInfo(absPath, excludeFileName, topLine, deleteAfterRead);
		if (contents == null || contents.length < 2) {
			return null;
		}
		return contents;
	}

	/**
	 * 读取文件或目录下的随机文件 FileUtils.readRandomFileFullInfo()<BR>
	 * <P>
	 * Author : yubin
	 * </P>
	 * <P>
	 * Date : 2015-6-22
	 * </P>
	 * 
	 * @param absPath
	 * @param excludeFileName
	 * @param topLine
	 * @param deleteAfterRead
	 * @return 数组共2列，第1列为文件名，第二列为文件正文
	 * @throws Exception
	 */
	public static String[] readRandomFileFullInfo(String absPath, final String excludeFileName, int topLine,
			boolean deleteAfterRead) throws Exception {
		if (absPath == null)
			return null;
		File file = new File(absPath);
		if (!file.exists()) {
			return null;
		}
		String[] contents = new String[2];
		if (file.isFile()) {
			String content = readFile(absPath, topLine);
			if (deleteAfterRead) {
				removeFile(absPath);
			}
			contents[0] = file.getName();
			contents[1] = content;
			return contents;
		}
		File[] filterFiles = file.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !readingFilenames.contains(name) && !name.equalsIgnoreCase(excludeFileName)
						&& !(name.lastIndexOf(DOWNLOAD_FILE_FUFFIX) == DOWNLOAD_FILE_FUFFIX.length() + 1);
			}
		});
		if (filterFiles == null || filterFiles.length == 0) {
			return null;
		}
		File filterFile = filterFiles[new Random().nextInt(filterFiles.length)];
		if (!readingFilenames.add(filterFile.getName())) {
			return readRandomFileFullInfo(absPath, excludeFileName, topLine, deleteAfterRead);
		}
		if (!filterFile.exists()) {
			readingFilenames.remove(filterFile.getName());
			return null;
		}
		String filterFilePath = filterFile.getAbsolutePath();
		String content = null;
		try {
			content = readFile(filterFilePath, topLine);
		} catch (Exception e) {
			throw e;
		} finally {
			if (deleteAfterRead) {
				if (content != null) {
					removeFile(filterFilePath);
				}
			}
			readingFilenames.remove(filterFile.getName());
		}
		contents[0] = filterFile.getName();
		contents[1] = content;
		return contents;
	}

	/**
	 * 检查目录，创建目录 FileUtils.checkDir()<BR>
	 * <P>
	 * Author : walker_v5
	 * </P>
	 * <P>
	 * Date : 2015年6月27日
	 * </P>
	 * 
	 * @param dir
	 */
	public static void checkDir(String dir) {
		File file = new File(dir);
		Stack<File> queue = new Stack<File>();
		checkDir(file, queue);
		while (!queue.isEmpty()) {
			File file1 = queue.pop();
			file1.mkdir();
		}
	}

	private static void checkDir(File file, Stack<File> queue) {
		if (!file.exists()) {
			queue.add(file);
			File file1 = file.getParentFile();
			if (file1 != null)
				checkDir(file1, queue);
		}
	}

	/**
	 * 读取行的文件
	 * 
	 * @param absPath
	 * @param excludeFileName
	 * @param topLine
	 * @param deleteAfterRead
	 * @return
	 * @throws Exception
	 */
	public static String readLineRandomFile(String absPath, final String excludeFileName, int topLine,
			boolean deleteAfterRead) throws Exception {
		String[] contents = readLineRandomFileFullInfo(absPath, excludeFileName, topLine, deleteAfterRead);
		if (contents == null || contents.length < 2) {
			return null;
		}
		return contents[1];
	}

	/**
	 * 读取文件或目录下的随机文件 (加入行的)判断
	 * 
	 * @param absPath
	 * @param excludeFileName
	 * @param topLine
	 * @param deleteAfterRead
	 * @return
	 * @throws Exception
	 */
	public static String[] readLineRandomFileFullInfo(String absPath, final String excludeFileName, int topLine,
			boolean deleteAfterRead) throws Exception {
		if (absPath == null)
			return null;
		File file = new File(absPath);
		if (!file.exists()) {
			return null;
		}
		String[] contents = new String[2];
		if (file.isFile()) {
			String content = readLineFile(absPath, topLine);
			if (deleteAfterRead) {
				removeFile(absPath);
			}
			contents[0] = file.getName();
			contents[1] = content;
			return contents;
		}
		File[] filterFiles = file.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !readingFilenames.contains(name) && !name.equalsIgnoreCase(excludeFileName)
						&& !(name.lastIndexOf(DOWNLOAD_FILE_FUFFIX) == DOWNLOAD_FILE_FUFFIX.length() + 1);
			}
		});
		if (filterFiles == null || filterFiles.length == 0) {
			return null;
		}
		File filterFile = filterFiles[new Random().nextInt(filterFiles.length)];
		if (!readingFilenames.add(filterFile.getName())) {
			return readLineRandomFileFullInfo(absPath, excludeFileName, topLine, deleteAfterRead);
		}
		if (!filterFile.exists()) {
			readingFilenames.remove(filterFile.getName());
			return null;
		}
		String filterFilePath = filterFile.getAbsolutePath();
		String content = null;
		try {
			content = readLineFile(filterFilePath, topLine);
		} catch (Exception e) {
			throw e;
		} finally {
			if (content != null) {
				removeFile(filterFilePath);
			}
			readingFilenames.remove(filterFile.getName());
		}
		contents[0] = filterFile.getName();
		contents[1] = content;
		return contents;
	}

	/**
	 * 读取文件读取每一行
	 * 
	 * @param absPath
	 * @return
	 * @throws Exception
	 */
	public static String readEveryLineFile(String absPath) throws Exception {
		if (absPath == null)
			return null;
		File file = new File(absPath);
		if (!file.exists()) {
			return null;
		}
		if (!file.isFile()) {
			return null;
		}
		if ((System.currentTimeMillis() - file.lastModified()) <= 100) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		BufferedReader buffer = null;
		FileReader reader = null;
		StringBuffer stringBuffer = new StringBuffer("");
		String tempString = "";
		try {
			reader = new FileReader(file);
			buffer = new BufferedReader(reader);
			int line = 1;
			while ((tempString = buffer.readLine()) != null) {
				line++;
				stringBuffer.append(tempString).append("\r\n");//根据
			}

		} catch (Exception e) {
			throw new Exception("读取文件" + absPath + "失败", e);
		} finally {
			closeReader(reader, buffer);
		}
		return stringBuffer.toString();
	}

	/**
	 * 读取文件(加入行的)判断
	 * 
	 * @param absPath
	 * @param topLine
	 * @return
	 * @throws Exception
	 */
	public static String readLineFile(String absPath, int topLine) throws Exception {
		if (absPath == null)
			return null;
		File file = new File(absPath);
		if (!file.exists()) {
			return null;
		}
		if (!file.isFile()) {
			return null;
		}
		if ((System.currentTimeMillis() - file.lastModified()) <= 100) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		BufferedReader buffer = null;
		FileReader reader = null;
		StringBuffer stringBuffer = new StringBuffer("");
		String tempString = "";
		try {
			reader = new FileReader(file);
			buffer = new BufferedReader(reader);
			int line = 1;
			while ((tempString = buffer.readLine()) != null) {
				line++;
				stringBuffer.append(tempString).append(",");
				if (topLine != 0 && line >= topLine) {
					break;
				}
			}
		} catch (Exception e) {
			throw new Exception("读取文件" + absPath + "失败", e);
		} finally {
			closeReader(reader, buffer);
		}
		return stringBuffer.toString();
	}

	/**
	 * @Title: checkFileAndCreate
	 * @Description: 创建文件
	 * @param filePath
	 * @return
	 * @return String
	 * @author gaoyuxin
	 * @date 2018年4月26日 下午4:39:35
	 */
	public static String checkFileAndCreate(String filePath) {
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
				return filePath;
			} catch (IOException e) {
				logger.warn("checkFileAndCreate exception", e);
				return null;
			}
		}
		return null;
	}

	/**
	 * @Title: copyFile
	 * @Description: 复制文件
	 * @param fromFile
	 * @param toFile
	 * @return
	 * @author haohuiming
	 * @date 2018年4月26日 下午4:39:35
	 */
	public static void copyFile(File fromFile, File toFile) throws Exception {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			if (!toFile.exists()) {
				toFile.getParentFile().mkdirs();
			}
			fi = new FileInputStream(fromFile);
			fo = new FileOutputStream(toFile);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道

			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			throw new Exception("拷贝图片文件出错", e);
		} finally {
			try {
				if (fi != null) {
					fi.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			try {
				if (fo != null) {
					fo.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * copy一个目录下所有内容到另外一个目录
	 * 
	 * @param oldPath
	 *            旧目录
	 * @param newPath
	 *            新目录
	 * @throws IOException
	 */
	public static void copyDir(String oldPath, String newPath) throws IOException {
		File file = new File(oldPath);
		String[] filePath = file.list();

		if (!(new File(newPath)).exists()) {
			(new File(newPath)).mkdir();
		}

		for (int i = 0; i < filePath.length; i++) {
			if ((new File(oldPath + file.separator + filePath[i])).isDirectory()) {
				copyDir(oldPath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
				continue;
			}
			if (new File(oldPath + file.separator + filePath[i]).isFile()) {
				copyFile(oldPath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
			}
		}
	}

	/**
	 * 通过文件路口copy文件
	 * 
	 * @param oldPath
	 *            旧文件路径
	 * @param newPath
	 *            新文件路径
	 * @author haohuiming
	 */
	public static void copyFile(String oldPath, String newPath) {
		File oldFile = new File(oldPath);
		File file = new File(newPath);
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(oldFile);
			out = new FileOutputStream(file);
			byte[] buffer = new byte[1048576];
			int len = -1;
			// 使用实际读出来的字节数，否则可能会出现无用大量空字符
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
