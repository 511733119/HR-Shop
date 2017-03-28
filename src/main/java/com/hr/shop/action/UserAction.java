package com.hr.shop.action;

import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.model.User;
import com.hr.shop.response.RestResultGenerator;
import com.hr.shop.validatorInterface.ValidInterface;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

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
	public String login(@RequestParam("input") String input , @RequestParam("password") String password , @Validated({ValidInterface.class}) User u , BindingResult errors){
		logger.debug("Entering login() :");
		if (errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		//如果输入为空
		if((input == null || "".equals(input))){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
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
			respRes = RestResultGenerator.genResult(Map_Msg.HTTP_BAD_REQUEST,Map_Msg.INPUT_USER_IS_ERROR);
		}else {
			//存在，则成功登录
			logger.info("userid:{} 成功登录 ",user.getId());
			String token = userService.generateToken(input,user.getPassword());	/* 生成token */
			user.setToken(token);
			userService.update(user);//刷新token

			//返回成功登录相关信息
			// 返回新的token
			//返回用户id
			urespRes = RestResultGenerator.genUserResult( Map_Msg.HTTP_OK , Map_Msg.USER_LOGIN_SUCCESS , token , user.getId() , null);
		}

		logger.debug("Ending login()");
		return urespRes.toString();
	}

	/**
	 * 如果是电话号码登录
	 */
	@RequestMapping(value = "/phoneLogin" , method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String phoneLogin(@RequestParam("phone") String phone , @Validated({ValidInterface.class}) User u , BindingResult errors){
		logger.debug("Entering phoneLogin() :");
		logger.info("手机号:{} 正在尝试登录",phone);

		if(errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}

		//查找是否存在该电话号码用户
		User user = userService.getUser(phone);
		//如果存在
		if (user != null){

			logger.info("userid:{} 成功登录",user.getId());
			String token = userService.generateToken(user.getPhone(),user.getPassword());	// 生成token
			user.setToken(token);
			userService.update(user);//刷新token

			//返回成功登录相关信息
			urespRes = RestResultGenerator.genUserResult(Map_Msg.HTTP_OK,Map_Msg.USER_LOGIN_SUCCESS ,token , user.getId(), null);
		}else {
			logger.info("手机号:{} 是新用户，现在尝试注册",phone);
			//返回该用户未注册标志，客户端此时跳转到设置密码页面
			urespRes = RestResultGenerator.genUserResult(Map_Msg.HTTP_OK,Map_Msg.NEW_USER,null,0,null);
		}
		logger.debug("Ending phoneLogin()");
		return urespRes.toString();
	}

	/**
	 * 注册时发送验证码到客户端
	 * 成功返回格式"{\"alibaba_aliqin_fc_sms_num_send_response\":{\"result\":{\"err_code\":\"0\",\"model\":\"106050020644^1108205048156\",\"success\":true},\"request_id\":\"zddu10wa4c4w\"}}"
	 * 错误返回格式"{\"error_response\":{\"code\":15,\"msg\":\"Remote service error\",\"sub_code\":\"isv.MOBILE_NUMBER_ILLEGAL\",\"sub_msg\":\"号码格式错误\",\"request_id\":\"z24ugzp8esng\"}}"
	 */
	@RequestMapping(value = "/registerCode" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String sendRegisterCode(@RequestParam("phone") String phone,@Validated({ValidInterface.class}) User u , BindingResult errors){
		logger.debug("Entering sendRegisterCode()");
		logger.info("手机号:{} 尝试接收短信",phone);
		//如果不合法
		if(errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		for (User user : userService.query()) {
			if (phone.equals(user.getPhone())) {
				logger.info("该手机号码已注册!");
				return RestResultGenerator.genResult(Map_Msg.HTTP_BAD_REQUEST,Map_Msg.USER_HAS_REGISTER).toString();
			}
		}
		String result = userService.sendCode(phone);
		respRes = RestResultGenerator.genResult(Map_Msg.HTTP_OK,result);
		logger.info("短信验证码已发送成功");
		logger.debug("Ending sendRegisterCode()");
		return respRes.toString();

	}

	/**
	 * 登录时发送验证码到客户端
	 * 成功返回格式"{\"alibaba_aliqin_fc_sms_num_send_response\":{\"result\":{\"err_code\":\"0\",\"model\":\"106050020644^1108205048156\",\"success\":true},\"request_id\":\"zddu10wa4c4w\"}}"
	 * 错误返回格式"{\"error_response\":{\"code\":15,\"msg\":\"Remote service error\",\"sub_code\":\"isv.MOBILE_NUMBER_ILLEGAL\",\"sub_msg\":\"号码格式错误\",\"request_id\":\"z24ugzp8esng\"}}"
	 */
	@RequestMapping(value = "/loginCode" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String sendLoginCode(@RequestParam("phone") String phone , @Validated({ValidInterface.class}) User u, BindingResult errors){
		logger.debug("Entering sendLoginCode()");
		//如果输入不合法
		if(errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		String result = userService.sendCode(phone);//发送短信验证码给客户端
		respRes = RestResultGenerator.genResult(Map_Msg.HTTP_OK,result);
		logger.debug("Ending sendLoginCode()");
		return respRes.toString();
	}

	/**
	 * 判断验证码是否正确
	 */
	@RequestMapping(value = "/checkCode" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String checkCode(@Validated({ValidInterface.class})User u , BindingResult errors ,@RequestParam("phone") String phone , @RequestParam("code") String code){
		logger.debug("Entering checkCode()");
		//如果输入不合法
		if(errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		if(code == null || "".equals(code)){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}

		//获取手机号(手机号在用户点击获取验证码时保存,按发送键时传递过来)
		Jedis redis = new Jedis("localhost");
		String sendCode = redis.get(phone);//获取存储的验证码
		logger.info("服务器验证码是 {},手机号为:{}的用户发送的验证码为 {}",sendCode,phone,code);
		if (sendCode.equals(code)) {
			respRes = RestResultGenerator.genResult(Map_Msg.HTTP_OK,Map_Msg.CODE_IS_RIGHT);
			if(userService.getUser(phone) == null){
				//返回该用户是新用户json标志
				respRes = RestResultGenerator.genEx_UserResult(Map_Msg.HTTP_OK,Map_Msg.CODE_IS_RIGHT, null,0,null,null,"true");
			}
			logger.info("验证码正确");
		}else {
			respRes = RestResultGenerator.genResult(Map_Msg.HTTP_BAD_REQUEST, Map_Msg.CODE_IS_ERROR);
			logger.info("验证码错误");
		}
		logger.debug("Ending checkCode()");
		return respRes.toString();
	}

	/**
	 * 用户输入正确的验证码后，填入密码进行注册
	 */
	@RequestMapping(value = "/register" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String register(@RequestParam("phone") String phone , @RequestParam("password") String password , @Validated({ValidInterface.class}) User u, BindingResult errors){
		logger.debug("Entering register()");
		//如果输入不合法
		if(errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		//验证该号码是否已注册过
		User user = new User();
		user = userService.getUser(phone);
		if (user != null){
			throw new RuntimeException(Map_Msg.USER_HAS_REGISTER);
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

		//返回用户电话号码,用户id,用户名,token
		logger.info("userid:{} 注册成功",userid);

		logger.debug("Ending register()");
		return RestResultGenerator.genEx_UserResult(Map_Msg.HTTP_OK,Map_Msg.USER_REGISTER_SUCCESS,token,userid,phone,username,null).toString();
	}
//
//	/**
//	 * 用户上传头像
//	 * @return
//	 */
//	public String updateHeadImg(){
//
//		int id = model.getId();	//用户id
//		if(request.get("base64_img") == null || id == 0) {
//			return ERROR;
//		}
//		String base64_img = String.valueOf(request.get("base64_img"));//获得base64加密后字符串
//		String decoder_img = FileUploadUtil.decoderBase64(base64_img);//base64解码
//		byte[] bytes = decoder_img.getBytes();
//		String filePosition = FileUploadUtil.getFile(bytes,id + "_" + System.currentTimeMillis()+".jpg");//获得文件名
//
//		User user = userService.get(id);
//		user.setAvatar(filePosition);
//		userService.update(user);//更新头像
//
//		dataMap = userService.getDataMap(dataMap,Map_Msg.SUCCESS,Map_Msg.UPDATE_SUCCESS);
//		return SUCCESS;
//	}

	/**
	 * 用户改变用户名
	 */
	@RequestMapping(value = "/updateUsername" ,method = RequestMethod.PATCH,produces="application/json;charset=UTF-8")
	public String updateUsername(@RequestParam("id") int id, @RequestParam("username") String username ,@Validated({ValidInterface.class}) User u , BindingResult errors){
		// 如果不匹配
		if (errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		//如果输入的用户名已存在
		if(!userService.getAllUserName(username)){
			return RestResultGenerator.genResult(Map_Msg.HTTP_BAD_REQUEST,Map_Msg.THE_NAME_IS_EXIST).toString();
		}

		userService.updateUsername(id,username);//设置新的用户名,更新用户
//		User user = userService.get(id);//根据用户id获得该用户对象
//		user.setUsername(username);
//		userService.update(user);//
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK,Map_Msg.UPDATE_SUCCESS).toString();
	}

	/**
	 * 用户修改密码
	 */
	@RequestMapping(value = "/updatePassword" ,method = RequestMethod.PATCH,produces="application/json;charset=UTF-8")
	public String updatePassword(@RequestParam("id") int id, @RequestParam("password") String password ,@Validated({ValidInterface.class}) User u , BindingResult errors){

		if(errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
//		User user = userService.get(id);//根据用户id获得该用户对象
//		if(user == null){
//			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
//		}
//		user.setPassword(password);//设置新的密码
//		userService.update(user);//更新用户
		userService.updatePassword(id,password);//设置新的密码，更新用户
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK,Map_Msg.UPDATE_SUCCESS).toString();
	}

}

