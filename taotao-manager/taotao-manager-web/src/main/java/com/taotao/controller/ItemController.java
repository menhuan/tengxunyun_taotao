package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.AnswerResult;
import com.taotao.common.pojo.EasyUIDateResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * action
 * @author fenguriqi
 * 2017年1月22日 下午9:58:25
 * ItemController
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * 根据id去查询数据
	 * @auther fengruiqi
	 * 2017年2月3日  下午10:01:29
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/{itemId}")
	@ResponseBody
	private TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
		System.out.println(111);
		return item;
	}
	/**
	 * 
	 * 描述：查询商品列表
	 * @author fengruiqi
	 * @time  2017年1月26日 下午5:26:09
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	public  EasyUIDateResult getItemList(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="30") Integer rows ){
		EasyUIDateResult result=itemService.getTotalMessage(page, rows);
		return result;
	}
	
	/**
	 * 增加商品信息
	 * @auther fengruiqi
	 * 2017年3月19日  下午2:26:01
	 * @param desc
	 * @param item
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public AnswerResult addItem(String desc,TbItem item,String itemParam){
		AnswerResult answerResult=itemService.createItem(item,desc,itemParam);
		return answerResult;
	}
	
	
}
