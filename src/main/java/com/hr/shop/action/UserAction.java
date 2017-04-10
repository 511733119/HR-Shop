package com.hr.shop.action;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.User;
import com.hr.shop.validatorInterface.ValidInterface;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author hjc
 * 用户Action，实现用户模块的相关操作
 */
@RestController
@RequestMapping("/api/users")
public class UserAction extends BaseAction<User> {

	/**
	 * 账号密码登录
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST ,produces="application/json;charset=UTF-8")
	public Map<String,Object> login( String input , String password , @Validated({ValidInterface.class}) User u , BindingResult errors){
		logger.debug("Entering login() :");
		if (errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		//如果输入为空
		if((input == null || "".equals(input))){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}

		User user = new User();
		//进行登录验证
		//如果输入的是手机号
		if (userService.isMobileNumber(input)){
			user.setPhone(input);

		}else{
			//如果输入的是用户名
			user.setUsername(input);
		}
		user.setPassword(password);

		logger.info("用户正在使用账号密码登录，输入为: {}",input);

		user = userService.login(user);//查找是否存在此用户

		//不存在此用户
		if(user == null){
			logger.info("用户名或密码错误");
			/*返回登录失败相关信息*/
			return productService.errorRespMap(respMap ,Map_Msg.INPUT_USER_IS_ERROR );
		}else {
			//存在，则成功登录
			logger.info("userid:{} 成功登录 ",user.getId());
			String token = userService.generateToken(input,user.getPassword());	/* 生成token */
			user.setToken(token);
			userService.update(user);//刷新token

			//返回成功登录相关信息
			// 返回新的token
			//返回用户id
			respMap = productService.successRespMap(respMap , Map_Msg.USER_LOGIN_SUCCESS , user);
		}

		logger.debug("Ending login()");
		return respMap;
	}

	/**
	 * 如果是电话号码登录
	 */
	@RequestMapping(value = "/phoneLogin" , method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String,Object> phoneLogin( String phone , @Validated({ValidInterface.class}) User u , BindingResult errors){
		logger.debug("Entering phoneLogin() :");
		logger.info("手机号:{} 正在尝试登录",phone);

		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}

		//查找是否存在该电话号码用户
		User user = userService.getUser(phone);
		//如果存在
		if (user != null){

			logger.info("userid:{} 成功登录",user.getId());
			String token = userService.generateToken(user.getPhone(),user.getPassword());	// 生成token
			user.setToken(token);
			userService.update(user);//刷新token

			//返回成功登录相关信息(token,id)
			respMap =  productService.successRespMap(respMap , Map_Msg.USER_LOGIN_SUCCESS , user);
		}else {
			logger.info("手机号:{} 是新用户，现在尝试注册",phone);
			//返回该用户未注册标志，客户端此时跳转到设置密码页面
			respMap = productService.successRespMap(respMap , Map_Msg.UPDATE_SUCCESS , "");
		}
		logger.debug("Ending phoneLogin()");
		return respMap;
	}

	/**
	 * 注册时发送验证码到客户端
	 * 成功返回格式"{\"alibaba_aliqin_fc_sms_num_send_response\":{\"result\":{\"err_code\":\"0\",\"model\":\"106050020644^1108205048156\",\"success\":true},\"request_id\":\"zddu10wa4c4w\"}}"
	 * 错误返回格式"{\"error_response\":{\"code\":15,\"msg\":\"Remote service error\",\"sub_code\":\"isv.MOBILE_NUMBER_ILLEGAL\",\"sub_msg\":\"号码格式错误\",\"request_id\":\"z24ugzp8esng\"}}"
	 */
	@RequestMapping(value = "/registerCode" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String, Object> sendRegisterCode( String phone,@Validated({ValidInterface.class}) User u , BindingResult errors){
		logger.debug("Entering sendRegisterCode()");
		logger.info("手机号:{} 尝试接收短信",phone);
		//如果不合法
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		for (User user : userService.query()) {
			if (phone.equals(user.getPhone())) {
				logger.info("该手机号码已注册!");
				return productService.errorRespMap(respMap ,Map_Msg.USER_HAS_REGISTER );
			}
		}
		result = userService.sendCode(phone);
		logger.info("短信验证码已发送成功");
		logger.debug("Ending sendRegisterCode()");
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , result);

	}

	/**
	 * 登录时发送验证码到客户端
	 * 成功返回格式"{\"alibaba_aliqin_fc_sms_num_send_response\":{\"result\":{\"err_code\":\"0\",\"model\":\"106050020644^1108205048156\",\"success\":true},\"request_id\":\"zddu10wa4c4w\"}}"
	 * 错误返回格式"{\"error_response\":{\"code\":15,\"msg\":\"Remote service error\",\"sub_code\":\"isv.MOBILE_NUMBER_ILLEGAL\",\"sub_msg\":\"号码格式错误\",\"request_id\":\"z24ugzp8esng\"}}"
	 */
	@RequestMapping(value = "/loginCode" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String, Object> sendLoginCode( String phone , @Validated({ValidInterface.class}) User u, BindingResult errors){
		logger.debug("Entering sendLoginCode()");
		//如果输入不合法
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		result = userService.sendCode(phone);//发送短信验证码给客户端
		logger.debug("Ending sendLoginCode()");
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , result);
	}

	/**
	 * 判断验证码是否正确
	 */
	@RequestMapping(value = "/checkCode" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String, Object> checkCode(@Validated({ValidInterface.class})User u , BindingResult errors , String phone , String code){
		logger.debug("Entering checkCode()");
		//如果输入不合法
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		if(code == null || "".equals(code)){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}

		//获取手机号(手机号在用户点击获取验证码时保存,按发送键时传递过来)
		Jedis redis = new Jedis("localhost");
		String sendCode = redis.get(phone);//获取存储的验证码
		logger.info("服务器验证码是 {},手机号为:{}的用户发送的验证码为 {}",sendCode,phone,code);
		if (sendCode.equals(code)) {
			respMap = productService.successRespMap(respMap , Map_Msg.CODE_IS_RIGHT , "");
			if(userService.getUser(phone) == null){
				//返回该用户是新用户json标志
				respMap = productService.successRespMap(respMap , Map_Msg.NEW_USER , "");
			}
			logger.info("验证码正确");
		}else {
			respMap = productService.errorRespMap(respMap ,Map_Msg.CODE_IS_ERROR );
			logger.info("验证码错误");
		}
		logger.debug("Ending checkCode()");
		return respMap;
	}

	/**
	 * 用户输入正确的验证码后，填入密码进行注册
	 */
	@RequestMapping(value = "/register" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@JsonView({View.exceptPwd.class})
	public Map<String, Object> register( String phone , String password , @Validated({ValidInterface.class}) User u, BindingResult errors){
		logger.debug("Entering register()");
		//如果输入不合法
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		//验证该号码是否已注册过
		User user = new User();
		user = userService.getUser(phone);
		if (user != null){
			return productService.errorRespMap(respMap ,Map_Msg.USER_HAS_REGISTER );
		}
		String username = userService.generateUsername(); //随机生成用户名
		String token = userService.generateToken(username,password);	// 生成token

		user = new User();
		user.setPhone(phone);
		user.setUsername(username);
		user.setPassword(password);
		user.setToken(token);
		userService.save(user);

		//根据电话号码获得该用户id，回传给客户端保存，用于之后其他操作传递id参数
		int userid = userService.getUser(phone).getId();

		user.setId(userid);

		//返回用户电话号码,用户id,用户名,token
		logger.info("userid:{} 注册成功",userid);

		logger.debug("Ending register()");
		//返回token，id，phone，username
		return productService.successRespMap(respMap , Map_Msg.USER_REGISTER_SUCCESS , user);
	}

	/**
	 * 用户上传头像
	 * @param id 用户id
	 * @return
	 */
	@RequestMapping(value = "/upload/{id}" , method = RequestMethod.POST , produces = "application/json;charset=UTF-8")
	public Map<String, Object> updateHeadImg( @RequestParam("file") MultipartFile[] file , @PathVariable("id") int id) {
		logger.debug("Entering updateHeadImg()");
		//判断file数组不能为空并且长度大于0
		if(file != null && file.length > 0){
			//循环获取file数组中得文件
			for(int i = 0;i < file.length;i++){
				MultipartFile f = file[i];
				//保存文件
				fileUploadUtil.saveHeadFile(f,id);
			}
			logger.debug("Ending updateHeadImg()");
			//上传成功
			return productService.successRespMap(respMap , Map_Msg.SUCCESS ,"");
		}
		logger.debug("Ending updateHeadImg()");
		//参数为空则返回错误
		return productService.errorRespMap(respMap, Map_Msg.THE_PIC_IS_NULL);

	}

	/**
	 * 用户改变用户名
	 */
	@RequestMapping(value = "/updateUsername" ,method = RequestMethod.PUT,produces="application/json;charset=UTF-8")
	public Map<String, Object> updateUsername( int id,  String username ,@Validated({ValidInterface.class}) User u , BindingResult errors){
		// 如果不匹配
		if (errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		//如果输入的用户名已存在
		if(!userService.getAllUserName(username)){
			return productService.errorRespMap(respMap ,Map_Msg.THE_NAME_IS_EXIST );
		}

		userService.updateUsername(id,username);//设置新的用户名,更新用户
//		User user = userService.get(id);//根据用户id获得该用户对象
//		user.setUsername(username);
//		userService.update(user);//
		return productService.successRespMap(respMap , Map_Msg.UPDATE_SUCCESS , "");
	}

	/**
	 * 用户修改密码
	 */
	@RequestMapping(value = "/updatePassword" ,method = RequestMethod.PUT,produces="application/json;charset=UTF-8")
	public Map<String, Object> updatePassword( int id, String password ,@Validated({ValidInterface.class}) User u , BindingResult errors){

		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
//		User user = userService.get(id);//根据用户id获得该用户对象
//		if(user == null){
//			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
//		}
//		user.setPassword(password);
//		userService.update(user);//更新用户
		userService.updatePassword(id,password);//设置新的密码，更新用户
		return productService.successRespMap(respMap , Map_Msg.UPDATE_SUCCESS , "");
	}

}

