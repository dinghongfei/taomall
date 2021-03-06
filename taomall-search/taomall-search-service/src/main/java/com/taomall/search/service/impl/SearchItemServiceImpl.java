package com.taomall.search.service.impl;

import com.taomall.common.pojo.SearchItem;
import com.taomall.common.pojo.TaomallResult;
import com.taomall.search.dao.SearchItemMapper;
import com.taomall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("searchItemService")
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private SolrClient solrServer;

    @Override
    public TaomallResult importItemsToIndex() {
        try {
            //从数据库查询所有商品数据
            List<SearchItem> itemList = searchItemMapper.getItemList();
            //遍历商品数据添加到索引库
            for (SearchItem searchItem : itemList) {
                //1.创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //2.向文档中添加域
                document.addField("id",searchItem.getId());
                document.addField("item_title",searchItem.getTitle());
                document.addField("item_sell_point",searchItem.getSell_point());
                document.addField("item_price",searchItem.getPrice());
                document.addField("item_image",searchItem.getImage());
                document.addField("item_category_name",searchItem.getCategory_name());
                document.addField("item_desc",searchItem.getItem_desc());
                //3.把文档写入索引库
                solrServer.add(document);

            }
            //提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return TaomallResult.build(500,"数据导入失败");
        }
        //返回添加成功
        return TaomallResult.ok();
    }
}
