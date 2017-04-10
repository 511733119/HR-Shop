package com.hr.shop.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 文件保存路径类
 * @author hjc
 *
 */
@Component("config")
public class Config {

	//评论的图片保存路径
	@Value("#{prop.commentFilePath}")
	private String commentFilePath;
	
	//用户头像保存路径
	@Value("#{prop.filePath}")
	private String filePath ;
	
	//商品图片保存路径
	@Value("#{prop.basePath}")
	private String basePath;
	
	public void setCommentFilePath(String commentFilePath) {
		this.commentFilePath = commentFilePath;
	}
	
	public String getCommentFilePath() {
		return commentFilePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFilePath() {
		return filePath;
	}
	
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	public String getBasePath() {
		return basePath;
	}
}
