package net.yundingwei.dszw.app.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {

	/**
	 * 判断SDCard是否存在
	 * @return
	 */
	public static boolean isSDcard() {
		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SD卡根目录路径
	 */
	public static String getSDRootDir() {
		String path;
		if (isSDcard()) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath();
			return path;
		} else {
			return null;
		}
	}

	/**
	 * 获取程序内部存储路径
	 */
	public static String getCacheDir(Context context) {
		String path = context.getFilesDir().getPath();
		return path;
	}
	
	/**
	 * 创建目录
	 * 
	 * @param dir
	 */
	public static File makeDirs(String dir) {
		try {
			File file = new File(dir);
			if (!file.exists()) {
				file.mkdirs();
			}
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建新文件
	 * 
	 * @param dir
	 * @param fileName
	 */
	public static File createNewFile(String dir, String fileName) {
		try {
			// 创建目录
			makeDirs(dir);
			// 文件路径
			String path = dir + "/" + fileName;
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 写入文件
	 * 
	 * @param buffer
	 * @param path
	 */
	public synchronized static void write(byte[] buffer, String path, String fileName, boolean append) {
		FileOutputStream fos = null;
		try {
			File dir = new File(path);
			if (dir.isDirectory() && !dir.exists()) {
				dir.mkdirs();// 创建目录
			}

			File file = new File(path + "/" + fileName);
			if (!file.exists()) {// 如果文件不存在，则创建文件
				file.createNewFile();
			}

			fos = new FileOutputStream(file, append);
			fos.write(buffer);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件或目录
	 * @param path
	 */
	public synchronized static void delete(String path){
		try{
			File file = new File(path);
			if(file.exists()){
				file.delete();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
