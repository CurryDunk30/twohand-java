package com.ershou.bishe_demo1.Pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WantShop implements Serializable {
    private Integer id;

    private Date modified;

    private Integer display;

    private String name;

    private Integer kind;

    private Integer uid;

    private Integer quantity;
    private Integer level;

    private BigDecimal price;

    private String remark;
    private String image;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getModified() {
        return modified == null ? null : (Date) modified.clone();
    }

    public void setModified(Date modified) {
        this.modified = modified == null ? null : (Date) modified.clone();
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}