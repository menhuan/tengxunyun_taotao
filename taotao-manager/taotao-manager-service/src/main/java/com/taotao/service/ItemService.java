package com.taotao.service;

import com.taotao.common.pojo.AnswerResult;
import com.taotao.common.pojo.EasyUIDateResult;
import com.taotao.pojo.TbItem;

/**
 * 接口
 * @author ASUS
 *
 */
public interface ItemService {
    
	/**
	 * 接口方法
	 * @auther fengruiqi
	 * 2017年1月22日  下午9:53:59
	 * @param itemId
	 * @return
	 */
	public TbItem getItemById(Long itemId) ;

	/**
	 * 查询单个表信息然后返回查询结果
	 * 描述：
	 * @author fengruiqi
	 * @time  2017年1月26日 下午5:12:25
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIDateResult getTotalMessage(Integer page,Integer rows);
	
	/**
	 * 创建一个商品
	 * @auther fengruiqi
	 * 2017年3月19日  上午10:46:34
	 * @param item
	 * @param desc
	 * @return
	 */
	public AnswerResult createItem(TbItem item,String desc,String itemParam); 
	
	/**
	 * 根据商品id查询规格参数
	 * @auther fengruiqi
	 * 2017年4月15日  下午12:43:07
	 * @param itemid
	 * @return
	 */
	public String getItemParam(Long itemid);

	
}
