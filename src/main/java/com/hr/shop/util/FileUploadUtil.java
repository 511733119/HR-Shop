package com.hr.shop.util;

import org.springframework.beans.factory.annotation.Value;
import sun.misc.BASE64Decoder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * @author hjc
 * 文件上传的工具类
 */
public class FileUploadUtil{

	@Value("#{prop.filePath}")
	private static String filePath ;

//	//通过文件名获取扩展名
//	private static String getFileExt(String fileName){
//		return FilenameUtils.getExtension(fileName);
//	}
//
//	//生成UUID随机数，作为新的文件名
//	private static String newFileName(String fileName){
//		String ext = getFileExt(fileName);
//		return UUID.randomUUID().toString()+"."+ext;
//	}
//
//	//实现文件上传的功能，返回上传后新的文件名
//	public static String uploadFile(FileImage fileImage){
//		//获取新唯一文件名
//		String pic = newFileName(fileImage.getFilename());
//		try {
//			FileUtil.copyFile(fileImage.getFile(), new File(filePath,pic));
//			return pic;
//		} catch (Exception e) {
//			throw new RuntimeException("上传图片失败");
//		}finally{
//			fileImage.getFile().delete();
//		}
//	}

	/**
	 * 将byte数组转化为file文件
	 * @param bfile
	 * @param fileName
	 */
	public static String getFile(byte[] bfile, String fileName) {
		BufferedOutputStream bos = null;
		File file = null;
		System.out.println(bfile.length);
		try {
			File dir = new File("C:\\img");
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File("C:" + "\\img\\" + fileName);
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(bfile);
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return fileName;
	}

	/**
	 *  JDK自带Base64解密
	 * */
	public static String decoderBase64(String string) {
		String str = null;
		BASE64Decoder decoder = new BASE64Decoder();// decode解密
		try {
			str = new String(decoder.decodeBuffer(string));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return str;
	}
}
