package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.CommentReply;
import com.ershou.bishe_demo1.Pojo.ShopContext;

import java.util.List;

public interface CommentReplyService {
    int deleteByPrimaryKey(Integer id);
    int deleteByCommentId(Integer commentId);

    int insert(CommentReply record);

    int insertSelective(CommentReply record);

    CommentReply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommentReply record);

    int updateByPrimaryKey(CommentReply record);

    int getCountsByCommentId(int commentid);

    List<CommentReply> selectByCommentId(int commentid);
}
