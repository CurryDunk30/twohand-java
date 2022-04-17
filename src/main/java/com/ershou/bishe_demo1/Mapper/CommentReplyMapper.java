package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.CommentReply;
import com.ershou.bishe_demo1.Pojo.ShopContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentReplyMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByCommentId(Integer commentId);

    int insert(CommentReply record);

    int insertSelective(CommentReply record);

    CommentReply selectByPrimaryKey(Integer id);

    int getCountsByCommentId(Integer commentId);

    int updateByPrimaryKeySelective(CommentReply record);

    int updateByPrimaryKey(CommentReply record);

    List<CommentReply> selectByCommentId(Integer commentId);
}
