package com.hr.shop.action;

import com.fasterxml.jackson.annotation.JsonView;
import com.hr.shop.Constant.Map_Msg;
import com.hr.shop.jsonView.View;
import com.hr.shop.model.Product;
import com.hr.shop.response.RestResultGenerator;
import com.hr.shop.validatorInterface.ValidInterface;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
	public String findNewest_Or_HighestSalesProduct(@PathVariable("flag") int flag , @RequestParam("pageNum") int pageNum){
		logger.debug("Entering findNewest_Or_HighestSalesProduct() :");
		if(pageNum <= 0){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		if(flag == 1){
			//如果flag=1，则查找最新上架商品
			jsonList = productService.findNewest_Or_HighestSalesProduct(1 , 3 , "update_date");
		}else {
			//如果flag!=1，则查找销量最高商品
			jsonList = productService.findNewest_Or_HighestSalesProduct(pageNum, 12, "sales");
		}
//		HttpServletRequest req = ServletActionContext.getRequest();
//		logger.debug("路径:{}",this.getClass().getClassLoader().getResource("").getPath());
		logger.debug("Ending findNewest_Or_HighestSalesProduct()");
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK,jsonList).toString();
	}

	/**
	 * 分类页展示分类数据
	 */
	@RequestMapping(value = "/category/{cid}", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	@JsonView(View.son2.class)
	public String listProduct(@PathVariable("cid") int cid , @RequestParam("pageNum") int pageNum){
		logger.debug("Entering listProduct() :");
		if(cid <= 0 || pageNum <= 0){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		jsonList = productService.findProduct(pageNum, 12, cid);
		logger.debug("Ending listProduct()");
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK, jsonList).toString();
	}

	/**
	 * 进入商品详情页传递商品种类数据
	 */
	@JsonView(View.son2.class)
	@RequestMapping(value = "/details/{id}", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String getProduct(@PathVariable int id , @Validated({ValidInterface.class}) Product product ,BindingResult errors){
		logger.debug("Entering getProduct() :");
		if(errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		jsonList = productService.getProduct(id);
		logger.debug("Ending getProduct()");
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK , jsonList).toString();
	}

	/**
	 * 列出搜索页的推荐商品
	 * @return
	 */
	@JsonView(View.son2.class)
	@RequestMapping(value = "/searchList/", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String getSearchList(){
		logger.debug("Entering getSearchList() :");
		jsonList = productService.getSearchList();
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK,jsonList).toString();
	}

	/**
	 * 根据关键字获得商品名称
	 * @return
	 */
	@JsonView(View.son2.class)
	@RequestMapping(value = "/keyword/{name}", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public String getSimilarProduct(@PathVariable("name") String name, @RequestParam("pageNum") int pageNum , @Validated({ValidInterface.class}) Product product , BindingResult errors){

		if (errors.hasErrors()){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		if( pageNum <= 0){
			throw new RuntimeException(Map_Msg.PARAM_IS_INVALID);
		}
		jsonList = productService.getSimilarProduct(name , pageNum , 10);
		return RestResultGenerator.genResult(Map_Msg.HTTP_OK , jsonList).toString();
	}
}
