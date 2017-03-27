package com.hr.shop.action;

import com.hr.shop.model.Sorder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hjc
 * 订单项Action
 */
@RestController
@RequestMapping("/api/sorders")
public class SorderAction extends BaseAction<Sorder> {

	private static final long serialVersionUID = 1L;

}
