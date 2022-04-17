package com.ershou.bishe_demo1.Pojo;

import java.util.Date;

public class ExchangeInfo {
    private Integer id;
    private Date createTime;
    private Integer state;
    private Integer level;
    private String name;
    private String remark;
    private Integer quantity;
    private Integer kind;
    private Integer sellerUid;
    private Integer exchangeUid;
    private Integer sellerSid;
    private Integer shopQuantity;

    public Integer getShopQuantity() {
        return shopQuantity;
    }

    public void setShopQuantity(Integer shopQuantity) {
        this.shopQuantity = shopQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(Integer sellerUid) {
        this.sellerUid = sellerUid;
    }

    public Integer getExchangeUid() {
        return exchangeUid;
    }

    public void setExchangeUid(Integer exchangeUid) {
        this.exchangeUid = exchangeUid;
    }

    public Integer getSellerSid() {
        return sellerSid;
    }

    public void setSellerSid(Integer sellerSid) {
        this.sellerSid = sellerSid;
    }

}
