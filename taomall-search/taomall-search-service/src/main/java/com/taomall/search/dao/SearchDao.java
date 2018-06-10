package com.taomall.search.dao;

import com.taomall.common.pojo.SearchItem;
import com.taomall.common.pojo.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询索引库商品dao
 * @author dhf
 */
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;


    public SearchResult search(SolrQuery solrQuery) throws Exception{
        //根据query对象进行查询
        QueryResponse response = solrServer.query(solrQuery);
        //取查询结果
        SolrDocumentList solrDocumentList = response.getResults();
        //取查询结果总记录数
        long numFound = solrDocumentList.getNumFound();
        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(numFound);
        //取高亮结果
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //商品列表
        List<SearchItem> itemList = new ArrayList<>();
        //把查询结果封装到SearchItem对象中
        for (SolrDocument solrDocument : solrDocumentList) {
            SearchItem searchItem = new SearchItem();
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            searchItem.setId((String) solrDocument.get("id"));
            //解析图片地址，取第一张
            String image = (String) solrDocument.get("item_image");
            if (StringUtils.isNotBlank(image)){
                image = image.split(",")[0];
            }
            searchItem.setImage(image);
            searchItem.setPrice((Long) solrDocument.get("item_price"));
            searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
            //取高亮显示
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String title;
            if (list != null && list.size()>0) {
                title = list.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            searchItem.setTitle(title);
            itemList.add(searchItem);
        }
        //把结果添加到SearchResult中
        searchResult.setItemList(itemList);
        //返回
        return searchResult;
    }

}
