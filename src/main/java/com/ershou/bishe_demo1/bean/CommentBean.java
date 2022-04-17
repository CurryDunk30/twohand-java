package com.ershou.bishe_demo1.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by wsk1103 on 2017/5/21.
 */
public class CommentBean implements Serializable {
    private int id;
    private Date time;
    private String content;
    private int uid;
    private int sid;
    private int type;
    private Map<String, Object> reply;


}
