package com.ershou.bishe_demo1.Pojo;

import java.io.Serializable;
import java.util.Date;

public class CommentReply implements Serializable {
    private Integer id;

    private Date time;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReplyuid() {
        return replyuid;
    }

    public void setReplyuid(Integer replyuid) {
        this.replyuid = replyuid;
    }

    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    private String content;

    private Integer replyuid;

    private Integer commentid;
}
