package com.ershou.bishe_demo1.Service.Impl;

import com.ershou.bishe_demo1.Mapper.ShopContextMapper;
import com.ershou.bishe_demo1.Pojo.ShopContext;
import com.ershou.bishe_demo1.Service.ShopContextService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wsk1103 on 2017/5/13.
 */
@Service
public class ShopContextServiceImpl implements ShopContextService {
    @Resource
    private ShopContextMapper shopContextMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        try {
            return shopContextMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteBySidAndType(Integer sid, Integer commentType) {
        try {
            return shopContextMapper.deleteBySidAndType(sid, commentType);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insert(ShopContext record) {
        return shopContextMapper.insert(record);
    }

    @Override
    public int insertSelective(ShopContext record) {
        return shopContextMapper.insertSelective(record);
    }

    @Override
    public ShopContext selectByPrimaryKey(Integer id) {
        return shopContextMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopContext record) {
        return shopContextMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShopContext record) {
        return shopContextMapper.updateByPrimaryKey(record);
    }

    @Override
    public int getCounts(int sid) {
        return shopContextMapper.getCounts(sid);
    }

    @Override
    public int getCountsByType(int commentType) {
        try {
            return shopContextMapper.getCountsByType(commentType);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<ShopContext> findById(int sid, int start) {
        return shopContextMapper.findById(sid,start);
    }

    @Override
    public List<ShopContext> selectBySid(int sid, int type) {
        return shopContextMapper.selectBySid(sid, type);
    }

    @Override
    public List<ShopContext> selectByTypePage(int commentType, int page, int size) {
        Integer start = (page-1) * size;
        try {
            return shopContextMapper.selectByTypePage(commentType, start, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
