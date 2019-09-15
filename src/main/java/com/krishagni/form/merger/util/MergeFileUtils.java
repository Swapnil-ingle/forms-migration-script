package com.krishagni.form.merger.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class MergeFileUtils {
	public static void ensureDirExists(String dir) {
		File parentDir = new File(dir);
		
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
	}

	public static String generateFileName(String strategy, String prefix, Integer idx, String ext) {
		if (strategy == null) {
			return seqGeneratedName(prefix, idx, ext);
		}

		switch(strategy) {
			case("sequential"):
				return seqGeneratedName(prefix, idx, ext);
			default:
				// Use sequential generation as default
				return seqGeneratedName(prefix, idx, ext);
		}
	}

	public static void copyBlobToFile(File file, Object data) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream((byte[]) data);
		OutputStream out = new FileOutputStream(file);
		IOUtils.copy(bis, out);
		
		bis.close();
		out.close();
	}

	public static void zipDir(String dir, String zipName) throws IOException {
		zipDir(dir, zipName, false);
	}

	public static void zipDir(String dir, String zipName, boolean deleteStaleFiles) throws IOException {
		System.out.println("Zipping the CSV and files");
		File dirToZip = new File(dir);
		File zip = new File(dirToZip.getParent() + File.separatorChar + zipName);

		FileOutputStream fileOut = new FileOutputStream(zip);
		ZipOutputStream zipOut = new ZipOutputStream(fileOut);

		for (File file: dirToZip.listFiles()) {
			zipFiles(file, file.getName(), zipOut);
		}

		zipOut.close();
		fileOut.close();

		if (deleteStaleFiles) {
			deleteFiles(dirToZip);
		}

		FileUtils.moveFileToDirectory(zip, dirToZip, false);
	}

	private static String seqGeneratedName(String filePrefix, Integer fileIdx, String extension) {
		return String.format("%s_%d%s", filePrefix, fileIdx, extension);
	}

	private static void zipFiles(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
	    if (fileToZip.isHidden()) {
	        return;
	    }

	    if (fileToZip.isDirectory()) {
	        if (fileName.endsWith(String.valueOf(File.separatorChar))) {
	            zipOut.putNextEntry(new ZipEntry(fileName));
	            zipOut.closeEntry();
	        } else {
	            zipOut.putNextEntry(new ZipEntry(fileName + File.separatorChar));
	            zipOut.closeEntry();
	        }
	        File[] children = fileToZip.listFiles();
	        for (File childFile : children) {
	        	zipFiles(childFile, fileName + File.separatorChar + childFile.getName(), zipOut);
	        }
	        return;
	    }

	    FileInputStream fis = new FileInputStream(fileToZip);
	    ZipEntry zipEntry = new ZipEntry(fileName);
	    zipOut.putNextEntry(zipEntry);

	    byte[] bytes = new byte[1024];
	    int length;

	    while ((length = fis.read(bytes)) >= 0) {
	        zipOut.write(bytes, 0, length);
	    }

	    fis.close();
	}

	private static void deleteFiles(File dirToZip) {
		System.out.println("Deleting stale files");
		for (File file: dirToZip.listFiles()) {
			if (file.isDirectory()) {
				deleteFiles(file);
			}
			file.delete();
		}
	}
}
