package com.taomall.search.service.test;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SolrJTest {

    @Test
    public void testImportIndex(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
        SearchItemService bean = applicationContext.getBean(SearchItemService.class);
        TaomallResult taomallResult = bean.importItemsToIndex();
        System.out.println(taomallResult);
    }

    @Test
    public void test1() throws IOException, SolrServerException {
        CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder().withZkHost("192.168.1.173:2181,192.168.1.173:2281,192.168.1.173:2381").build();
        //CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder().withSolrUrl("http://192.168.1.173:8180/solr").build();
        //需要设置默认的Collection
        cloudSolrClient.setDefaultCollection("collection");
        //创建一个文档对
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域
        document.addField("id", "test001");
        document.addField("item_title", "测试商品名称");
        document.addField("item_price", 100);
        //把文档写入索引库
        cloudSolrClient.add(document);
        //提交
        cloudSolrClient.commit();

    }


   /* @Test
    public void testSolrCloudAdDocument() throws Exception {
        //创建一个CloudSolrServer对象，构造方法中需要制定zookeeper的地址列表
        CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.1.173:2181,192.168.1.173:2281,192.168.1.173:2381");
        //需要设置默认的Collection
        cloudSolrServer.setDefaultCollection("collection");
        //创建一个文档对
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域
        document.addField("id", "test001");
        document.addField("item_title", "测试商品名称");
        document.addField("item_price", 100);
        //把文档写入索引库
        cloudSolrServer.add(document);
        //提交
        cloudSolrServer.commit();

    }




    @Test
    public void testAddDocument() throws Exception{
        //创建一个solrServer对象，创建一个HttpSolrServer对象
        //需要指定solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solrCoreTest");
        //创建一个文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        //想文档中添加域，必须有id域，域的名称必须在schema.xml中定义
        document.addField("id","test03");
        document.addField("item_title","test_item03333");
        document.addField("price",1000);
        //把文档对象写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();

    }
    */

    @Test
    public void deleteDocumentById() throws Exception{
        //创建一个solrServer对象，创建一个HttpSolrServer对象
        //需要指定solr服务的url
        //SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solrCoreTest");
        //solrServer.deleteById("test01");
        //提交
        //solrServer.commit();


        CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder().withZkHost("192.168.1.173:2181,192.168.1.173:2281,192.168.1.173:2381").build();
        cloudSolrClient.setDefaultCollection("collection");
        cloudSolrClient.deleteById("test001");
        cloudSolrClient.commit();
    }
/*
    @Test
    public void deleteDocumentByQuery() throws Exception{
        //创建一个solrServer对象，创建一个HttpSolrServer对象
        //需要指定solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solrCoreTest");
        solrServer.deleteByQuery("id:test02");
        //提交
        solrServer.commit();
    }

    @Test
    public void searchDocument() throws Exception{
        //创建一个solrServer对象，创建一个HttpSolrServer对象
        //需要指定solr服务的url
        SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solrCoreTest");
        //创建一个solrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件，过滤条件，分页条件，排序条件，高亮
        //query.set("q", "*:*");//设置查询所有
        query.setQuery("手机");
        //分页条件
        query.setStart(30);//从第几条开始
        query.setRows(20);
        //设置默认搜索于
        query.set("df","item_keywords");
        //设置高亮
        query.setHighlight(true);
        //高亮显示的域
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        //执行查询
        QueryResponse response = solrServer.query(query);
        //取查询结果,对应于在网页操作页面返回结果中的response
        SolrDocumentList solrDocumentList = response.getResults();
        //取查询结果总记录数
        System.out.println("总记录数："+solrDocumentList.getNumFound());
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTile;
            if (list != null && list.size()>0) {
                itemTile = list.get(0);
            } else {
                itemTile = (String) solrDocument.get("item_title");
            }
            System.out.println(itemTile);
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
        }
        //提交
        solrServer.commit();
    }*/


}
