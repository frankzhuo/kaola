package com.kaola.edata.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 文件压缩工具类
 * @author sunjunqing
 *
 */
public class ZipCompressor {
	static final int BUFFER = 8192;
	Logger logger = Logger.getLogger(this.getClass());
	private File zipFile;

	public ZipCompressor(String pathName) {
		zipFile = new File(pathName);
	}
	public ZipCompressor() {

	}

	public void compress(String srcPathName) {
		File file = new File(srcPathName);
		if (!file.exists())
			throw new RuntimeException(srcPathName + "不存在！");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
					new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			String basedir = "";
			compress(file, out, basedir);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void compress(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			logger.info("压缩：" + basedir + file.getName());
			this.compressDirectory(file, out, basedir);
		} else {
			logger.info("压缩：" + basedir + file.getName());
			this.compressFile(file, out, basedir);
		}
	}

	
	/**
	 * 压缩一个目录
	 * @param dir 要压缩的目录路径
	 * @param out 输出流
	 * @param basedir 压缩后生成ZIP文件的跟路径
	 */
	public void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists())
			return;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compress(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/** 压缩一个文件 */
	private void compressFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String args[]){
		//示例
		ZipCompressor zip = new ZipCompressor();
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream("D://zip/dsdsdsd.zip"));
			zip.compressDirectory(new File("D://aa"), out, "");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
