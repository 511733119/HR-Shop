package com.hr.shop.action;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.Cart;
import com.hr.shop.model.User;
import com.hr.shop.response.RestResultGenerator;
import com.hr.shop.validatorInterface.ValidInterface;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hjc
 * 购物车Action，实现购物车模块的相关操作
 */
@RestController
@RequestMapping("/api/carts")
public class CartAction extends BaseAction<Cart> {

	private static final long serialVersionUID = 1L;
	/**
	 * 修改购物车商品数量时触发
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH ,produces="application/json;charset=UTF-8")
	public String updateCartNumber(@PathVariable("id") int id, @RequestParam("number") int number  , @Validated({ValidInterface.class})Cart c , BindingResult errors){

		if(errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		//库存
		int inventory = 0;

		Cart cart = cartService.get(id);
		if(cart != null){
			inventory = cart.getProtype().getInventory();	//获得该商品库存
		}
		//如果数量大于库存,则输出错误
		if ( number > inventory){
			throw new RuntimeException(Map_Msg.UPDATE_NUMBER_ERROR);
		}
		cart.setNumber(number);

		cartService.update(cart);//执行更新操作

		logger.debug("Ending updateCartNumber()");
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK,
				Map_Msg.UPDATE_SUCCESS).toString();

	}

	/**
	 * 删除购物车项
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE,produces="application/json;charset=UTF-8" )
	public String deleteCart(@PathVariable("id") int id , @Validated({ValidInterface.class})Cart cart , BindingResult errors) {
		logger.debug("Entering deleteCart() : id:{}",id);
		if(errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		cartService.delete(id);//删除该购物车项
		logger.debug("Ending deleteCart()");
		return  RestResultGenerator.genResult(Map_Msg.HTTP_OK, Map_Msg.DELETE_SUCCESS).toString();
	}

	/**
	 * 显示购物车所有商品
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET,produces="application/json;charset=UTF-8" )
	@JsonView(View.son.class)
	public String getCart(@RequestParam("id")int id , @Validated({ValidInterface.class}) User user , BindingResult errors) {
		logger.debug("Entering getCart() :");
		if (errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		//获取购物车数据
		List<Cart> list = cartService.getCart(id);
		logger.debug("Ending getCart()");
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK , list).toString();
	}

//	/**
//	 * 删除所选购物车项
//	 */
//
//	public String deleteByCheck() {
//		logger.debug("Entering deleteByCheck() :");
//		String ids = String.valueOf(request.get("ids"));
//		logger.debug("ids :{}", ids);
//		cartService.deleteByCheck(ids);
//		dataMap = cartService.getDataMap(dataMap ,
//				Map_Msg.SUCCESS,
//				Map_Msg.DELETE_SUCCESS);
//		logger.debug("Ending deleteByCheck()");
//		return SUCCESS;
//	}

	/**
	 * 用户点击立即下单，检测库存是否足够，是则跳转到错误显示页面，否则跳转到填写个人信息下单页
	 * @return
	 */
	@RequestMapping(value = "/buy", method = RequestMethod.GET,produces="application/json;charset=UTF-8" )
	public String placeOrderImmediately(@RequestParam("ptids")String ptids , @RequestParam("numbers") String numbers) {

		if( (ptids == null || "".equals(ptids))
				|| (numbers == null || "".equals(numbers))) {
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}

		String[] split_ids = ptids.split(",");//分割字符串，取出所要下单的商品细分种类id
		String[] split_numbers = numbers.split(",");//分割字符串，取出所要下单的商品细分种类各自对应数量

		if(split_ids == null || split_numbers == null){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}

		int count = cartService.checkInventory(split_ids, split_numbers);//库存检查
		if(count != 0){
			//如果某件商品库存不足，则不能下单
			throw new RuntimeException(Map_Msg.PLACE_ORDER_ERROR);
		}
		//库存足够，成功下单
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK,Map_Msg.SAVE_ORDER_SUCCESS).toString();
	}

}