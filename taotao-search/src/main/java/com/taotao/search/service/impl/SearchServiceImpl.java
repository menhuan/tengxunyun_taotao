package com.taotao.search.service.impl;

import com.taotao.common.pojo.AnswerResult;
import com.taotao.common.pojo.SearchResultBean;
import com.taotao.search.dao.ItemDao;
import com.taotao.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * searchService
 * @author fenguriqi
 * 2017年5月15日 下午4:21:42
 * SearchServiceImpl
 */
@Service
public class SearchServiceImpl implements SearchService {
	
	/**
	 * 注入dao
	 */
	@Autowired
	private ItemDao itemDao;
	
	@Override
	public SearchResultBean searchResult(String queryString, Long page, Long pageSize) throws Exception {
		//创建查询对象
		SolrQuery solrQuery = new SolrQuery();
		//设置查询条件
		//solrQuery.set("q","");
		solrQuery.setQuery(queryString);
		//设置分页
		solrQuery.setStart((int) ((page - 1) * pageSize));
		solrQuery.setRows(pageSize.intValue());
		//高亮设置
		solrQuery.setHighlight(true);
		//设置高亮显示的域
		solrQuery.addHighlightField("item_title");
		//高亮显示的前缀
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		//高亮显示的后缀
		solrQuery.setHighlightSimplePost("</span>");
		//设置默认搜素域
		solrQuery.set("df", "item_keywords");
		//调用dao查询商品列表
		SearchResultBean searchResult = itemDao.searchItem(solrQuery);
		//计算总页数
		long total = searchResult.getTotal();
		long pageCount = total / pageSize;
		if (total % pageSize > 0) {
			pageCount++;
		}
		searchResult.setPageCount(pageCount);
		searchResult.setPageNum(page);
		//返回结果
		return searchResult;
	}

}
