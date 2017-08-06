package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.pojo.TbUser;
import com.taotao.portal.bean.CatItemBean;
import com.taotao.portal.bean.OrderInfoBean;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

/**
 * 提交订单
 * @author fenguriqi
 * 2017年5月27日 上午9:17:54
 * OrderController
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	/**
	 * 订单service
	 */
	@Autowired
	private OrderService    orderService;
	/**
	 * 购物车列表展示
	 * @auther fengruiqi
	 * 2017年5月27日  下午2:15:48
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/order-cart")
	public String showOrderCart(Model model, HttpServletRequest request) {
		//取购物车商品列表
		List<CatItemBean> list = cartService.getCartItems(request);
		model.addAttribute("cartList", list);
		return "order-cart";
	}

	/**
	 * 创建订单
	 * @auther fengruiqi
	 * 2017年5月27日  下午2:38:11
	 * @param orderInfo
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createOrder(OrderInfoBean orderInfo, Model model, HttpServletRequest request) {
		//取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		//补全orderIn的属性
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//调用服务
		String orderId = orderService.createOrder(orderInfo);
		//把订单号传递个页面
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		//返回逻辑视图
		return "success";
	}

	
}
