package com.ershou.bishe_demo1.Pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderForm implements Serializable {
    private Integer id;

    private Date modified;

//    订单已支付 0， 订单已完结 1， 订单取消 2,
    private Integer state;

    public Integer getPaystate() {
        return paystate;
    }

    public void setPaystate(Integer paystate) {
        this.paystate = paystate;
    }

    //    订单支付状态
    private Integer paystate;
//  购买的用户id
    private Integer uid;

    public Integer getSellerid() {
        return sellerid;
    }

    public void setSellerid(Integer sellerid) {
        this.sellerid = sellerid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer sellerid;

    private String address;
    private String message;
    private String tel;
//    收件人
    private String name;


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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

}