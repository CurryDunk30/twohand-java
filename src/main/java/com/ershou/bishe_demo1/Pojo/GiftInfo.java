package com.ershou.bishe_demo1.Pojo;

import java.io.Serializable;
import java.util.Date;

public class GiftInfo implements Serializable {
    private Integer id;

    private Date createtime;

    private Integer kind;

    private String name;
    private String receivename;
    private String remark;
    private String address;
    private String tel;

    private Integer level;
    private Integer quantity;

    private Integer receiveuid;
    private Integer giveuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReceivename() {
        return receivename;
    }

    public void setReceivename(String receivename) {
        this.receivename = receivename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReceiveuid() {
        return receiveuid;
    }

    public void setReceiveuid(Integer receiveuid) {
        this.receiveuid = receiveuid;
    }

    public Integer getGiveuid() {
        return giveuid;
    }

    public void setGiveuid(Integer giveuid) {
        this.giveuid = giveuid;
    }

}
