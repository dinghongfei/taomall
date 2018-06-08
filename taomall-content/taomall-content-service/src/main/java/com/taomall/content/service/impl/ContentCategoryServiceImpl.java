package com.taomall.content.service.impl;

import com.taomall.common.pojo.EasyUITreeNode;
import com.taomall.common.pojo.TaomallResult;
import com.taomall.content.service.ContentCategoryService;
import com.taomall.dao.ContentCategoryMapper;
import com.taomall.pojo.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("contentCategoryService")
public class ContentCategoryServiceImpl implements ContentCategoryService{

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
        List<ContentCategory> contentCategoryList = contentCategoryMapper.selectListByParentId(parentId);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (ContentCategory contentCategory : contentCategoryList) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent()?"closed":"open");
            resultList.add(node);
        }

        return resultList;
    }

    @Override
    public TaomallResult addContentCategory(Long parentId, String name) {
        //创建一个pojo对象
        ContentCategory contentCategory = new ContentCategory();
        //补全对象属性
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        //状态。可选值:1(正常),2(删除)'
        contentCategory.setStatus(1);
        //排序，默认为1
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入到数据库
        contentCategoryMapper.insert(contentCategory);

        //判断父节点的状态
        ContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            //修改原本为叶子节点的父节点为父节点
            parent.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKeySelective(parent);
        }


        //返回结果
        return TaomallResult.ok(contentCategory);
    }

    @Override
    public TaomallResult updateContentCategory(Long id, String name) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);

        return TaomallResult.ok();
    }
}
