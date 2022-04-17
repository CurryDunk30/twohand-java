package com.ershou.bishe_demo1.Pojo;

import java.io.Serializable;
import java.util.Date;

// 商品留言和求购留言
public class ShopContext implements Serializable {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    private Integer id;

    private Date time;

    private Integer sid;

    private String content;

//    0-商品 1-求购
    private Integer type;

    private Integer uid;


}