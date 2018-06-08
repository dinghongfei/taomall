package com.taomall.service.impl;

import com.taomall.common.pojo.EasyUITreeNode;
import com.taomall.dao.ItemCatMapper;
import com.taomall.pojo.ItemCat;
import com.taomall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类管理
 * @author dhf
 */
@Service("itemCatService")
public class ItemCatServiceImpl implements ItemCatService{

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 根据父节点id查询子节点列表
     * @param parentId
     * @return
     */
    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        List<ItemCat> itemCatList = itemCatMapper.selectListByParentId(parentId);
        //转换成EasyUITreeNode列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (ItemCat itemCat : itemCatList) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(itemCat.getId());
            node.setText(itemCat.getName());
            //如果节点下有子节点“closed”，没有子节点“open”
            node.setState(itemCat.getIsParent()?"closed":"open");
            resultList.add(node);
        }
        return resultList;
    }
}
