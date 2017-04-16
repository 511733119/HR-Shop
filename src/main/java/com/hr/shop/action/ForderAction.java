package com.hr.shop.action;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.dto.Pay_Result;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.Forder;
import com.hr.shop.model.Sorder;
import com.hr.shop.model.Status;
import com.hr.shop.model.User;
import com.hr.shop.validatorInterface.ValidInterface;

/**
 * @author hjc
 * 订单Action，实现订单模块的相关操作
 */
@RestController
@RequestMapping("/api/forders")
public class ForderAction extends BaseAction<Forder> {

	private static final long serialVersionUID = 1L;
	/**
	 * 订单与订单项级联入库
	 *  期望格式	String json = "{'name':'海俊','address':'清风阁','remark':'加两双筷子','phone':'123124325235','sorderSet':[{'id':1,'number':43,'protype':{'id':4,'name':'ddd','pic':'4.jpg','inventory':100,'product':{'id':2,'name':'bbb','price':23}}},{'id':6,'number':3,'protype':{'id':4,'name':'ddd','pic':'4.jpg','inventory':50,'product':{'id':2,'name':'bbb','price':23}}}]}";
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/user" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String,Object> saveOrder(int id, @RequestBody String order_json, @Validated({ValidInterface.class}) User u , BindingResult errors){
		logger.debug("Entering saveOrder() :");
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		if(order_json == null || "".equals(order_json)){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		logger.debug("json:{}, userid :{}",order_json, id);

		User user = new User(id);
		//如果此用户为空
		if(user == null){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		//设置订单状态为已下单
		Status status = new Status(1);
		Forder forder = new Forder();
		//下单
		String result = forderService.saveOrder(order_json, user, status, forder);

		//如果库存不足,则失败
		if (result == "0") {
			return productService.errorRespMap(respMap, Map_Msg.PLACE_ORDER_ERROR);
		}
		logger.debug("Ending saveOrder() :");
		return productService.successRespMap(respMap , Map_Msg.SAVE_ORDER_SUCCESS ,result);
	}

	/**
	 * 查询用户的订单记录
	 * @return
	 */
	@JsonView(View.son.class)
	@RequestMapping(value = "/user" ,method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public Map<String,Object> findAllOrder( int id, int pageNum , @Validated({ValidInterface.class})User u, BindingResult errors){
		logger.debug("Entering findAllOrder() :");
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		if(pageNum <= 0){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		logger.debug("uid:{}, pageNum :{}",id, pageNum);
		jsonList = forderService.findAllOrder(id , pageNum , 10 );
		logger.debug("Order:{}",jsonList);
		logger.debug("Ending findAllOrder() :");
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , jsonList);
	}

	/**
	 * 删除订单
	 * @return
	 */
	@RequestMapping(value = "/{fid}" ,method = RequestMethod.DELETE,produces="application/json;charset=UTF-8")
	public Map<String,Object> deleteOrder(@PathVariable("fid") String fid ){
		logger.debug("Entering deleteOrder()");
		Forder forder = forderService.getOrder(fid);
		//查找是否存在该订单
		if(forder == null){
			//提醒无此订单
			return productService.errorRespMap(respMap ,Map_Msg.WITHOUT_THIS_ORDER );
		}
		int result = forderService.deleteOrder(fid);
		logger.debug("Ending deleteOrder()");
		if(result == 1){
			//删除订单成功
			productService.successRespMap(respMap , Map_Msg.DELETE_SUCCESS , "");
		}
		return respMap;
	}

	/**
	 * 取消订单，修改订单状态及恢复库存
	 * @return
	 */
	@RequestMapping(value = "/{fid}" ,method = RequestMethod.PUT,produces="application/json;charset=UTF-8")
	public Map<String,Object> cancelOrder(@PathVariable("fid")String fid){

		if( fid == null || "".equals(fid)){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		Forder forder = forderService.getOrder(fid);//获得该订单

		if(forder == null){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		int inventory = 0;//库存
		int number = 0;//订单中单件商品数量
		int protype_id ;
		//遍历订单中的订单项,并恢复对应商品的库存，设置订单状态为交易关闭
		for (Sorder sorder : forder.getSorderSet()){
			inventory = sorder.getProtype().getInventory();//获得现在的库存
			number = sorder.getNumber();//订单项中的下单数量
			protype_id = sorder.getProtype().getId();//获得该商品id
			protypeService.updateInventory(protype_id,inventory + number);//更新 库存
			forderService.updateStatusById(fid, 4);//设置订单状态为交易关闭
		}
		return productService.successRespMap(respMap , Map_Msg.DELETE_SUCCESS , "");
	}
	
	/**
	 * 支付完成，查询订单状态查看是否支付成功，是则更新订单状态，否则返回支付失败信息
	 *
	 */
	@RequestMapping(value = "/pay/{id}" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String, Object> getPay_Result(@PathVariable("id") String id , String fid){
		
		logger.debug("Entering getPay_Result()");
		//目标页面  
        String url = "https://api.bmob.cn/1/pay/"+id;  
        String responseBody = forderService.getPay_json(url); //HttpClient抓取bmob订单信息
        Pay_Result pay_Result = forderService.parsePay_json(responseBody); //解析json数据为Pay_Result类
        
        //支付失败则返回
        if(!"SUCCESS".equals(pay_Result.getTrade_state())){
        	return productService.errorRespMap(respMap, Map_Msg.ERROR);
        }
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String ctime = formatter.format(pay_Result.getCreate_time());//格式化日期
        
        forderService.updateTimeAndStatus(fid, ctime, 2);//更新订单支付时间和订单状态修改为已支付
        logger.debug("Ending getPay_Result()");
        return productService.successRespMap(respMap, Map_Msg.SUCCESS,"");
	}
	
}
