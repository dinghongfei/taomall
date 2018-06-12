package com.taomall.item.pojo;

import com.taomall.pojo.Item;

public class ItemResult extends Item {

    public ItemResult(Item item) {
        //初始化属性
        this.setId(item.getId());
        this.setTitle(item.getTitle());
        this.setSellPoint(item.getSellPoint());
        this.setPrice(item.getPrice());
        this.setNum(item.getNum());
        this.setBarcode(item.getBarcode());
        this.setImage(item.getImage());
        this.setCid(item.getCid());
        this.setStatus(item.getStatus());
        this.setCreated(item.getCreated());
        this.setUpdated(item.getUpdated());
    }

    public String[] getImages() {
        if (this.getImage()!=null && !"".equals(this.getImage())) {
            String image2 = this.getImage();
            String[] strings = image2.split(",");
            return strings;
        }
        return null;
    }


}
