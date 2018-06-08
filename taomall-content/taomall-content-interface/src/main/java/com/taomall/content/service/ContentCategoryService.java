package com.taomall.content.service;

import com.taomall.common.pojo.EasyUITreeNode;
import com.taomall.common.pojo.TaomallResult;

import java.util.List;

/**
 * 内容分类service
 * @author dhf
 */
public interface ContentCategoryService {

    /**
     * 获取内容分类列表
     * 根据父节点id获取其子节点
     * @param parentId  父节点id
     * @return          List<EasyUITreeNode>
     */
    List<EasyUITreeNode> getContentCategoryList(Long parentId);

    /**
     * 添加内容分类
     * @param parentId  父节点id
     * @param name      新增子节点名字
     * @return          TaomallResult
     */
    TaomallResult addContentCategory(Long parentId, String name);

    /**
     * 修改内容分类
     * @param id        分类id
     * @param name      节点新名字
     * @return          TaomallResult
     */
    TaomallResult updateContentCategory(Long id, String name);


}
