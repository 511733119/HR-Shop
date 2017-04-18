package com.hr.shop.action;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.Product;
import com.hr.shop.validatorInterface.ValidInterface;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author hjc
 * 商品Action，实现商品模块的相关操作
 */
@RestController
@RequestMapping("/api/products")
public class ProductAction extends BaseAction<Product>{

	/**
	 * 首页主体展示商品由销量高到低排列，顶部轮播图展示新上架商品数据
	 */
	@RequestMapping(value = "/flag/{flag}", method = RequestMethod.GET,produces="application/json;charset=UTF-8" )
	@JsonView(View.son2.class)
	public Map<String,Object> findNewest_Or_HighestSalesProduct( @PathVariable("flag") int flag , int pageNum){
		if(pageNum <= 0){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		if(flag == 1){
			//如果flag=1，则查找最新上架商品
			jsonList = productService.findNewest_Or_HighestSalesProduct(1 , 3 , "update_date");

		}else {
			//如果flag!=1，则查找销量最高商品
			jsonList = productService.findNewest_Or_HighestSalesProduct(pageNum, 12, "sales");
		}
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , jsonList);
	}

	/**
	 * 分类页展示分类数据
	 */
	@RequestMapping(value = "/category/{cid}", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@JsonView(View.son2.class)
	public Map<String,Object> listProduct( @PathVariable("cid") int cid , int pageNum){
		if(cid <= 0 || pageNum <= 0){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		jsonList = productService.findProduct(pageNum, 12, cid);
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , jsonList);
	}

	/**
	 * 进入商品详情页传递商品种类数据
	 */
	@JsonView(View.son2.class)
	@RequestMapping(value = "/details/{id}", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public Map<String, Object> getProduct(@PathVariable int id , @Validated({ValidInterface.class}) Product product ,BindingResult errors){
		if(errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		jsonList = productService.getProduct(id);
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , jsonList);
	}

	/**
	 * 列出搜索页的推荐商品
	 * @return
	 */
	@JsonView(View.son2.class)
	@RequestMapping(value = "/searchList/", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public Map<String, Object> getSearchList(){
		jsonList = productService.getSearchList();
		return productService.successRespMap(respMap , Map_Msg.SUCCESS , jsonList);
	}

	/**
	 * 根据关键字获得商品名称
	 * @return
	 */
	@JsonView(View.son2.class)
	@RequestMapping(value = "/keyword/", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public Map<String,Object> getSimilarProduct( String name, int pageNum , @Validated({ValidInterface.class}) Product product , BindingResult errors){

		try {
		    name = new String(name.getBytes("iso-8859-1"),"UTF-8");//对中文进行解码，先转为iso-8859-1字节流，再转为utf8字符流
		} catch (UnsupportedEncodingException e) {
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		logger.debug("关键词:{}",name);
		if (errors.hasErrors()){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		if( pageNum <= 0){
			return productService.errorRespMap(respMap ,Map_Msg.PARAM_IS_INVALID );
		}
		jsonList = productService.getSimilarProduct(name , pageNum , 10);

		return productService.successRespMap(respMap , Map_Msg.SUCCESS , jsonList);
	}
}
