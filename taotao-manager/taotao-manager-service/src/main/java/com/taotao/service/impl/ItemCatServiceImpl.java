package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNodePojo;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	/**
	 * 注入map
	 */
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyUITreeNodePojo> getItemCatList(long parentId) {
        //根据id查询分类列表
		TbItemCatExample  example=new TbItemCatExample();
		//设置查询条件
		Criteria criteria=example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list=itemCatMapper.selectByExample(example);
		
		List<EasyUITreeNodePojo> resultList=new ArrayList<>();
		for(TbItemCat tbItemCatBean:list){
			EasyUITreeNodePojo pojo=new EasyUITreeNodePojo(tbItemCatBean.getId(), tbItemCatBean.getName(), 
					tbItemCatBean.getIsParent()?"closed":"open");
		    resultList.add(pojo);
		}
		return resultList;
	}

}