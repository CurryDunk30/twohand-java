package com.ershou.bishe_demo1.Pojo;

import java.io.Serializable;
import java.util.Date;

public class GoodsOfOrderForm implements Serializable {
    private Integer id;

    private Integer orderid;

    private Integer sid;

    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer ofid) {
        this.orderid = ofid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}