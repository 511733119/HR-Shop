package com.hr.shop.action;

import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.model.Cart;
import com.hr.shop.model.Protype;
import com.hr.shop.model.User;
import com.hr.shop.response.RestResultGenerator;
import com.hr.shop.validatorInterface.ValidInterface;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hjc
 * 商品细分种类Action
 */
@RestController
@RequestMapping("/api/protypes")
public class ProtypeAction extends BaseAction<Protype> {

	private static final long serialVersionUID = 1L;
	/**
	 * 添加购物车操作
	 */
	@RequestMapping(value = "/cart/add" , method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	public String addCart(@RequestParam("ptid") int ptid, @RequestParam("id") int id, @RequestParam("number") int number , @Validated({ValidInterface.class})User u , BindingResult errors){
		logger.debug("Entering addCart() :");

		if(errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		if( ptid <= 0 ){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		//数量
		int inventory = 0;
		Protype protype = protypeService.get(id);
		if(protype == null){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		inventory =protype.getInventory();	//获得该商品库存
		//如果数量大于库存,则输出错误
		if ( number >= inventory){
			throw new RuntimeException(Map_Msg.UPDATE_NUMBER_ERROR);
		}

		logger.debug("number:{},userid:{}",number,id);

		User user = new User(id);

		//获得该用户所有购物车id
		List<Cart> list = cartService.getCartId(user.getId());

		if(list == null ){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		//保存购物车信息
		cartService.saveCart(list, number, user, protype);
		logger.debug("Ending addCart()");
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK, Map_Msg.ADD_CART_SUCCESS).toString();
	}
	
}