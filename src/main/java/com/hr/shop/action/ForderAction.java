package com.hr.shop.action;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.*;
import com.hr.shop.validatorInterface.ValidInterface;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
		if(user == null){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		Status status = new Status(1);
		Forder forder = new Forder();

		//下单
		forderService.saveOrder(order_json, user, status, forder);

		logger.debug("Ending saveOrder() :");
		return productService.successRespMap(respMap , Map_Msg.SAVE_ORDER_SUCCESS , "");
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
		Protype protype = null;
		//遍历订单中的订单项,并恢复对应商品的库存，设置订单状态为交易关闭
		for (Sorder sorder : forder.getSorderSet()){
			inventory = sorder.getProtype().getInventory();//获得现在的库存
			number = sorder.getNumber();//订单项中的下单数量
			protype = sorder.getProtype();
			protype.setInventory(inventory + number);//恢复库存
			protypeService.update(protype);//更新
			forderService.cancelOrder(fid);//设置订单状态为交易关闭
		}
		return productService.successRespMap(respMap , Map_Msg.DELETE_SUCCESS , "");
	}
}
