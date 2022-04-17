package com.ershou.bishe_demo1.Service.Impl;

import com.ershou.bishe_demo1.Mapper.CommentReplyMapper;
import com.ershou.bishe_demo1.Pojo.CommentReply;
import com.ershou.bishe_demo1.Pojo.GiftInfo;
import com.ershou.bishe_demo1.Service.CommentReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentReplySeviceImpl implements CommentReplyService {
    @Resource
    CommentReplyMapper commentReplyMapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        try {
            return commentReplyMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteByCommentId(Integer commentId) {
        try {
            return commentReplyMapper.deleteByCommentId(commentId);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insert(CommentReply record) {
        try {
            return commentReplyMapper.insert(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insertSelective(CommentReply record) {
        try {
            return commentReplyMapper.insertSelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public CommentReply selectByPrimaryKey(Integer id) {
        try {
            return commentReplyMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateByPrimaryKeySelective(CommentReply record) {
        try {
            return commentReplyMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateByPrimaryKey(CommentReply record) {
        try {
            return commentReplyMapper.updateByPrimaryKey(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getCountsByCommentId(int commentid) {
        try {
            return commentReplyMapper.getCountsByCommentId(commentid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<CommentReply> selectByCommentId(int commentid) {
        try {
            return commentReplyMapper.selectByCommentId(commentid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
