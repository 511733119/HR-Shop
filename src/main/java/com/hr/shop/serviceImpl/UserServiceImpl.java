package com.hr.shop.serviceImpl;

import com.hr.shop.model.User;
import com.hr.shop.service.UserService;
import com.hr.shop.util.MD5Util;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.regex.Pattern;

/**
 * 模块自身的业务逻辑
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Override
	public User login(User user) {
		return userDao.login(user);
	}

	@Override
	public User getUser(String phone) {
		return userDao.getUser(phone);
	}

	@Override
	public User checkToken(int id, String token) {
		return userDao.checkToken(id, token);
	}

	/**
	 * 发送验证码
	 */
	@Override
	public String sendCode(String phone) {
		String body = null;
		try {
			TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23645484", "1db0344df9f885ac50c2f83d8450cf2d");
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			String code =generateCheckPass();
			req.setExtend( "123456" );
			req.setSmsType( "normal" );
			req.setSmsFreeSignName( "华软杂货" );
			req.setSmsParamString("{\"code\":\""+ code +"\"}");
			req.setRecNum( phone );
			req.setSmsTemplateCode( "SMS_48385018" );
			AlibabaAliqinFcSmsNumSendResponse rsp ;
			rsp = client.execute(req);
			body = rsp.getBody();
			
			//保存验证码
			Jedis jedis = new Jedis("localhost");
			jedis.set(phone,code);	//保存在redis中
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return body;
	}


	/** 
     * 随机生成6位数字字符数组
     */
	@Override
    public String generateCheckPass(){ 
        String chars = "0123456789"; 
        char[] rands = new char[6]; 
        for (int i = 0; i < 6; i++) 
        { 
            int rand = (int) (Math.random() * 10); 
            rands[i] = chars.charAt(rand); 
        } 
        return String.valueOf(rands); 
    }
    
    /**
     * 注册时随机生成用户名
     */
    @Override
    public String generateUsername(){
    	String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    	String nums = "0123456789";
    	char[] rands = new char[10]; 
        for (int i = 0; i < 3; i++) 
        { 
            int rand = (int) (Math.random() * 10); 
            rands[i] = chars.charAt(rand); 
        } 
        for (int j = 0; j < 7; j++) 
        { 
            int rand = (int) (Math.random() * 10); 
            rands[j+3] = nums.charAt(rand); 
        } 
        return String.valueOf(rands); 
    }
    
	/**
	 *  生成token
	 */
	@Override
	public String generateToken(String username, String password){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(username)
				.append(":")
				.append(password)
				.append(":")
				.append(System.currentTimeMillis());
		return MD5Util.generate(stringBuilder.toString());
	}

	@Override
	public boolean getAllUserName(String name) {
		User user =  userDao.getAllUserName(name);
		return user == null? true : false;
	}

	@Override
	public boolean isMobileNumber(String mobiles) {
		return Pattern.matches("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$", mobiles)?true:false;
	}

	@Override
	public void updateUsername(int id, String username) {
		userDao.updateUsername(id , username);
	}

	@Override
	public void updatePassword(int id, String password) {
		userDao.updatePassword(id , password);
	}

	public void updatAvatar(int id, String avatar) {
		userDao.updatAvatar(id,avatar);
	}
}
