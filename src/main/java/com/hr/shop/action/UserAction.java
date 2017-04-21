package com.hr.shop.action;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.Business;
import com.hr.shop.model.Product;
import com.hr.shop.model.User;
import com.hr.shop.model.User_follow_Business;
import com.hr.shop.model.User_follow_Product;
import com.hr.shop.validatorInterface.ValidInterface;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.util.List;
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
		logger.info("the user is using username and password to login , the input is : {}",input);
		user = userService.login(user);//查找是否存在此用户
		//不存在此用户
		if(user == null){
			logger.error("the username or password is error");
			/*返回登录失败相关信息*/
			return productService.errorRespMap(respMap ,Map_Msg.INPUT_USER_IS_ERROR );
		}else {
			//存在，则成功登录
			logger.info("userid:{} login successfully ",user.getId());
			String token = userService.generateToken(input,user.getPassword());	/* 生成token */
			userService.updateToken(token,user.getId());//刷新token
			//返回成功登录相关信息
			// 返回新的token
			//返回用户id
			respMap = productService.successRespMap(respMap , Map_Msg.USER_LOGIN_SUCCESS , user);
		}
		return respMap;
	}

	/**
	 * 如果是电话号码登录
	 */
	@RequestMapping(value = "/phoneLogin" , method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String,Object> phoneLogin( String phone , @Validated({ValidInterface.class}) User u , BindingResult errors){
		logger.info("the phone:{} is attempting to login",phone);
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		//查找是否存在该电话号码用户
		User user = userService.getUser(phone);
		//如果存在
		if (user != null){
			logger.info("userid:{} login successfully",user.getId());
			String token = userService.generateToken(user.getPhone(),user.getPassword());	// 生成token
			userService.updateToken(token, user.getId());//刷新token
			//返回成功登录相关信息(token,id)
			respMap =  productService.successRespMap(respMap , Map_Msg.USER_LOGIN_SUCCESS , user);
		}else {
			logger.info("phone:{} is new user，now attempting to register",phone);
			//返回该用户未注册标志，客户端此时跳转到设置密码页面
			respMap = productService.successRespMap(respMap , Map_Msg.UPDATE_SUCCESS , "");
		}
		return respMap;
	}

	/**
	 * 注册时发送验证码到客户端
	 * 成功返回格式"{\"alibaba_aliqin_fc_sms_num_send_response\":{\"result\":{\"err_code\":\"0\",\"model\":\"106050020644^1108205048156\",\"success\":true},\"request_id\":\"zddu10wa4c4w\"}}"
	 * 错误返回格式"{\"error_response\":{\"code\":15,\"msg\":\"Remote service error\",\"sub_code\":\"isv.MOBILE_NUMBER_ILLEGAL\",\"sub_msg\":\"号码格式错误\",\"request_id\":\"z24ugzp8esng\"}}"
	 */
	@RequestMapping(value = "/registerCode" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String, Object> sendRegisterCode( String phone,@Validated({ValidInterface.class}) User u , BindingResult errors){
		logger.info("attempting to send code to phone {}",phone);
		//如果不合法
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		for (User user : userService.query()) {
			if (phone.equals(user.getPhone())) {
				logger.info("the phone had registered!");
				return productService.errorRespMap(respMap ,Map_Msg.USER_HAS_REGISTER );
			}
		}
		result = userService.sendCode(phone);
		logger.info("send code to user successfully");
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , result);
	}

	/**
	 * 登录时发送验证码到客户端
	 * 成功返回格式"{\"alibaba_aliqin_fc_sms_num_send_response\":{\"result\":{\"err_code\":\"0\",\"model\":\"106050020644^1108205048156\",\"success\":true},\"request_id\":\"zddu10wa4c4w\"}}"
	 * 错误返回格式"{\"error_response\":{\"code\":15,\"msg\":\"Remote service error\",\"sub_code\":\"isv.MOBILE_NUMBER_ILLEGAL\",\"sub_msg\":\"号码格式错误\",\"request_id\":\"z24ugzp8esng\"}}"
	 */
	@RequestMapping(value = "/loginCode" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String, Object> sendLoginCode( String phone , @Validated({ValidInterface.class}) User u, BindingResult errors){
		//如果输入不合法
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		result = userService.sendCode(phone);//发送短信验证码给客户端
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , result);
	}

	/**
	 * 判断验证码是否正确
	 */
	@RequestMapping(value = "/checkCode" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String, Object> checkCode(@Validated({ValidInterface.class})User u , BindingResult errors , String phone , String code){
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
		logger.info("the code in server is {},the user with phone :{} send code {}",sendCode,phone,code);
		//如果用户输入的验证码正确
		if (sendCode.equals(code)) {
			respMap = productService.successRespMap(respMap , Map_Msg.CODE_IS_RIGHT , "");
			if(userService.getUser(phone) == null){
				//返回该用户是新用户json标志
				respMap = productService.successRespMap(respMap , Map_Msg.NEW_USER , "");
			}
			logger.info("the code is right");
		}else {
			respMap = productService.errorRespMap(respMap ,Map_Msg.CODE_IS_ERROR );
			logger.info("the code is error");
		}
		return respMap;
	}

	/**
	 * 用户输入正确的验证码后，填入密码进行注册
	 */
	@RequestMapping(value = "/register" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@JsonView({View.exceptPwd.class})
	public Map<String, Object> register( String phone , String password , @Validated({ValidInterface.class}) User u, BindingResult errors){
		//如果输入不合法
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		//验证该号码是否已注册过
		User user = userService.getUser(phone);
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
		userService.save(user);//注册新用户

		//根据电话号码获得该用户id，回传给客户端保存，用于之后其他操作传递id参数
		int userid = userService.getUser(phone).getId();

		user.setId(userid);

		//返回用户电话号码,用户id,用户名,token
		logger.info("userid:{} is registering successfully",userid);
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
		//判断file数组不能为空并且长度大于0
		if(file != null && file.length > 0){
			//循环获取file数组中得文件
			for(int i = 0;i < file.length;i++){
				MultipartFile f = file[i];
				//保存文件
				fileUploadUtil.saveHeadFile(f,id);
			}
			//上传成功
			return productService.successRespMap(respMap , Map_Msg.SUCCESS ,"");
		}
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
		userService.updatePassword(id,password);//设置新的密码，更新用户
		return productService.successRespMap(respMap , Map_Msg.UPDATE_SUCCESS , "");
	}
	
	/**
	 * 用户收藏/取消收藏店铺
	 * @param bid 店铺id
	 * @param id 用户id
	 * @return
	 */
	@RequestMapping(value = "/businesses-followers" , method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public Map<String, Object> handlerBusinessFolowers( int bid ,  int id){
		if (bid < 1 || id < 1) {
			return productService.errorRespMap(respMap, Map_Msg.PARAM_IS_INVALID);
		}
		long count = user_BusinessService.checkIfFollow(bid, id);//检查用户是否已收藏该店铺
		if (count == 0) {
			//如果未收藏店铺，则插入数据
			User_follow_Business user_follow_Business = new User_follow_Business();
			user_follow_Business.setUser(new User(id));
			user_follow_Business.setBusiness(new Business(bid));
			user_BusinessService.save(user_follow_Business);
		}else{
			//如果已收藏，则删除该数据
			user_BusinessService.deleteFollow(bid,id);
		}
		return productService.successRespMap(respMap, Map_Msg.SUCCESS, "");
	}
	
	/**
	 * 获取用户收藏的店铺
	 * @param id 用户id
	 * @param pageNum 页码
	 * @return
	 */
	@JsonView({View.summary.class})
	@RequestMapping(value = "/businesses-follows" , method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Map<String, Object> getUserFollowsBusiness(int id , int pageNum){
		if (pageNum < 1 || id < 1) {
			return productService.errorRespMap(respMap, Map_Msg.PARAM_IS_INVALID);
		}
		List<User_follow_Business> jsonList = userService.getUserFollowsBusiness(id, pageNum, 10);
		return productService.successRespMap(respMap, Map_Msg.SUCCESS, jsonList);
	}
	
	/**
	 * 用户收藏/取消收藏商品
	 * @param pid 商品id
	 * @param id 用户id
	 * @return
	 */
	@RequestMapping(value = "/products-followers" , method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public Map<String, Object> handlerProductFolowers(int pid ,  int id){
		if (pid < 1 || id < 1) {
			return productService.errorRespMap(respMap, Map_Msg.PARAM_IS_INVALID);
		}
		long count = user_ProductService.checkIfFollow(pid, id);//检查用户是否已收藏该商品
		if (count == 0) {
			//如果未收藏店铺，则插入数据
			User_follow_Product user_follow_Product = new User_follow_Product();
			user_follow_Product.setUser(new User(id));
			user_follow_Product.setProduct(new Product(pid));
			user_ProductService.save(user_follow_Product);
		}else{
			//如果已收藏，则删除该数据
			user_ProductService.deleteFollow(pid,id);
		}
		return productService.successRespMap(respMap, Map_Msg.SUCCESS, "");
	}
	
	/**
	 * 获取用户收藏的商品
	 * @param id 用户id
	 * @param pageNum 页码
	 * @return
	 */
	@JsonView({View.son2.class})
	@RequestMapping(value = "/products-follows" , method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Map<String, Object> getUserFollowsProduct(int id , int pageNum){
		if (pageNum < 1 || id < 1) {
			return productService.errorRespMap(respMap, Map_Msg.PARAM_IS_INVALID);
		}
		List<User_follow_Product> jsonList = userService.getUserFollowsProduct(id, pageNum, 10);
		return productService.successRespMap(respMap, Map_Msg.SUCCESS, jsonList);
	}
}

