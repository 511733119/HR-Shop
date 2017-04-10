package com.hr.shop.dto;

import java.io.File;

/**
 * @author hjc
 * 文件上传类
 */
public class FileImage {

	private File file;
	
	private String contentType;//文件类型
	
	private String filename;//文件名

	public File getFile() {
		return file;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setUpload(File file) {
		this.file = file;
	}

	public void setUploadContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setUploadFileName(String filename) {
		this.filename = filename;
	}
	
	
}
