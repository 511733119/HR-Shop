package com.hr.shop.util;

import com.hr.shop.model.User;
import com.hr.shop.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author hjc
 * 文件上传的工具类
 */
@Component
public class FileUploadUtil{
	
	Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
	
	@Resource
	UserService userService;

	@Resource(name="config")
	private Config config;
	
	/**
	 *  JDK自带Base64解密
	 * */
	@SuppressWarnings("restriction")
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

	/**
	 * 保存文件,返回文件名
	 * @param file
	 * @param filePath
	 * @return
	 */
	public String saveFiles(MultipartFile file ,String filePath) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				//获取上传文件的名字
				String filename = file.getOriginalFilename();
				//获取上传文件的类型
				String type = filename.substring(filename.lastIndexOf("."), filename.length());
				filename = UUID.randomUUID().toString()+ type;
				File f = new File(filePath+ "/" +filename);
				logger.debug("{}",f.getAbsolutePath());
				logger.debug("{}",f.getPath());
				if(!f.exists()){
					f.mkdirs();
				}
				// 转存文件
				file.transferTo(f);
				return filename;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 保存评论时图片
	 * @param file
	 * @return
	 */
	public String saveCommFiles(MultipartFile file) {
//		return saveFiles(file ,config.getCommentFilePath());
		return saveFiles(file ,"/var/tomcat/tomcat-7/webapps/comment-img");
	}
	
	/**
	 * 保存用户头像
	 * @param file
	 * @param id 用户id
	 * @return
	 */
	public boolean saveHeadFile(MultipartFile file , int id) {
//		String filename =saveFiles(file ,config.getFilePath());
		String filename =saveFiles(file ,"/var/tomcat/tomcat-7/webapps/head-img");
		saveToDatabase(filename,id);
		return true;
	}

	/**
	 * 保存头像地址到数据库
	 * @param fileName
	 * @param id
	 */
	private void saveToDatabase(String fileName , int id){
		User user = userService.get(id);
		user.setAvatar(fileName);
		userService.updatAvatar(id, fileName);
	}
}
