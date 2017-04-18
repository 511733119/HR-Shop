package com.hr.shop.action;

import java.text.SimpleDateFormat;
import java.util.List;
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
	@RequestMapping(value = "/user" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String,Object> saveOrder(int id, String order_json, @Validated({ValidInterface.class}) User u , BindingResult errors){
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		if(order_json == null || "".equals(order_json)){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		User user = new User(id);
		//设置订单状态为已下单
		Status status = new Status(1);
		Forder forder = new Forder();
		//保存订单,如果库存都足够，则返回非0，返回0则表示库存不足
		String result = forderService.saveOrder(order_json, user, status, forder);

		//如果库存不足,则失败
		if (result == "0") {
			return productService.errorRespMap(respMap, Map_Msg.PLACE_ORDER_ERROR);
		}
		return productService.successRespMap(respMap , Map_Msg.SAVE_ORDER_SUCCESS ,result);
	}

	/**
	 * 查询用户的订单记录
	 * @return
	 */
	@JsonView(View.son.class)
	@RequestMapping(value = "/user" ,method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public Map<String,Object> findAllOrder( int id, int pageNum , @Validated({ValidInterface.class})User u, BindingResult errors){
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		if(pageNum <= 0){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		jsonList = forderService.findAllOrder(id , pageNum , 10 );
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , jsonList);
	}

	/**
	 * 删除订单
	 * @return
	 */
	@RequestMapping(value = "/{fid}" ,method = RequestMethod.DELETE,produces="application/json;charset=UTF-8")
	public Map<String,Object> deleteOrder(@PathVariable("fid") String fid ){
		Forder forder = forderService.getOrder(fid);
		//查找是否存在该订单
		if(forder == null){
			//提醒无此订单
			return productService.errorRespMap(respMap ,Map_Msg.WITHOUT_THIS_ORDER );
		}
		int result = forderService.deleteOrder(fid); //删除订单（标记该订单标记为1）
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
		forderService.cancelOrder(forder, fid);//取消订单，修改订单状态及恢复库存
		return productService.successRespMap(respMap , Map_Msg.DELETE_SUCCESS , "");
	}
	
	/**
	 * 支付完成，查询订单状态查看是否支付成功，是则更新订单状态，并增加销量。否则返回支付失败信息
	 */
	@RequestMapping(value = "/pay/{id}" ,method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public Map<String, Object> getPay_Result(@PathVariable("id") String id , String fid){
		
        Pay_Result pay_Result =forderService.getPay_Result(id);//获取bmob订单状态
        
        //支付失败则返回
        if(!"SUCCESS".equals(pay_Result.getTrade_state())){
        	return productService.errorRespMap(respMap, Map_Msg.ERROR);
        }
        forderService.updateAboutForder(pay_Result, fid);//支付完成，查询订单状态查看是否支付成功，是则更新订单状态，并增加销量。
        												 //否则返回支付失败信息
        return productService.successRespMap(respMap, Map_Msg.SUCCESS,"");
	}
	
}
