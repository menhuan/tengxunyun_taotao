package com.taotao.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.AnswerResult;
import com.taotao.common.pojo.EasyUIDateResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;
import com.taotao.utils.ConstantUtils;

/**
 * 接口的实现
 * @author fenguriqi
 * 2017年1月22日 下午9:54:48
 * ItemServiceImpl
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper  itemParamItemMapper ;
	
	@Override
	public TbItem getItemById(Long itemId) {
		//TbItem item = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		//创建查询条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		//判断list中是否为空
		TbItem item = null;
		if (list != null && list.size() > 0) {
			item = list.get(0);
		}
		return item;
	}
	/**
	 *单表查询揭露结果封装
	 */
	@Override
	public EasyUIDateResult getTotalMessage(Integer page, Integer rows) {
		/**
		 * 设置分页
		 */
		PageHelper.startPage(page, rows);
		TbItemExample example=new TbItemExample();
		
		List<TbItem> list=itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(list);
		
		long total=pageInfo.getTotal();
		//创建 返回值对象
		EasyUIDateResult result=new EasyUIDateResult(total,list);
		return result;
	}
	/**
	 * 给商品描述表插入商品
	 * 生成商品id
	 * 补全item的属性
	 */
	@Override
	public AnswerResult createItem(TbItem item, String desc ,String itemParam) {
		
		/**组装信息*/
		long itemId=IDUtils.genItemId();
		
		item.setId(itemId);
		item.setStatus(ConstantUtils.PRODUCT_STATUS);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		
		/**插入新的商品*/
		itemMapper.insert(item);
		
		TbItemDesc itemDesc=new TbItemDesc();
		
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDesc.setCreated(new Date());
		
		/**插入新的商品描述*/
		tbItemDescMapper.insert(itemDesc);
		
		/**增加商品规格参数处理*/
		TbItemParamItem  itemParamBean=new TbItemParamItem();
		itemParamBean.setItemId(itemId);
		itemParamBean.setParamData(itemParam);
		itemParamBean.setCreated(new Date());
		itemParamBean.setUpdated(new Date());
		
		itemParamItemMapper.insert(itemParamBean);
		
		return AnswerResult.ok();
	}
	/**
	 * 查询规格参数
	 */
	@Override
	public String getItemParam(Long itemid) {
		
		TbItemParamItemExample example=new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria  
		 criteria =example.createCriteria();
		criteria.andItemIdEqualTo(itemid);
		
		List<TbItemParamItem> list=itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list==null || list.isEmpty()){
			return "";
		}
		
		TbItemParamItem bean=list.get(0);
		String paramData=bean.getParamData();
		List<Map> mapListMessage=JsonUtils.jsonToList(paramData,Map.class );
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("	<tbody>\n");
		for (Map map : mapListMessage) {
			sb.append("<tr>\n");
			sb.append("<th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
			sb.append("</tr>\n");
			//取规格项
			List<Map> mapListParams = (List<Map>) map.get("params");
			for (Map map2 : mapListParams) {
				sb.append("<tr>\n");
				sb.append("<td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
				sb.append("<td>"+map2.get("v")+"</td>\n");
				sb.append("</tr>\n");
			}
		}
		sb.append("	</tbody>\n");
		sb.append("</table>");

		return sb.toString();
	}

}
