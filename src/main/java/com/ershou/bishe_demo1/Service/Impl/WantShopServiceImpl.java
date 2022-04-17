package com.ershou.bishe_demo1.Service.Impl;

import com.ershou.bishe_demo1.Mapper.WantShopMapper;
import com.ershou.bishe_demo1.Pojo.WantShop;
import com.ershou.bishe_demo1.Service.WantShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wsk1103 on 2017/5/13.
 */
@Service
public class WantShopServiceImpl implements WantShopService {
    @Resource
    private WantShopMapper userWantMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        try {
            return userWantMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insert(WantShop record) {
        return userWantMapper.insert(record);
    }

    @Override
    public int insertSelective(WantShop record) {
        return userWantMapper.insertSelective(record);
    }

    @Override
    public WantShop selectByPrimaryKey(Integer id) {
        return userWantMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WantShop record) {
        return userWantMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(WantShop record) {
        return userWantMapper.updateByPrimaryKey(record);
    }

    @Override
    public int getCounts(int uid) {
        return userWantMapper.getCounts(uid);
    }

    @Override
    public int getCountsByState(int state) {
        try {
            return userWantMapper.getCountsByState(state);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getCountsByUidAndState(int uid, int state) {
        try {
            return userWantMapper.getCountsByUidAndState(uid, state);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<WantShop> selectAll() {
        return userWantMapper.selectAll();
    }

    @Override
    public List<WantShop> selectByKind(Integer kind) {
        try {
            return userWantMapper.selectByKind(kind);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<WantShop> searchStateBySize(Integer display, Integer kind, Integer level, String name, Integer page, Integer size) {
        Integer start = (page-1) * size;
        try {
            return userWantMapper.searchStateBySize(display, kind, level, name, start, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<WantShop> searchStateByUserAndSize(Integer uid, Integer display, Integer kind, Integer level, String name, Integer page, Integer size) {
        Integer start = (page-1) * size;
        try {
            return userWantMapper.searchStateByUserAndSize(uid,display, kind, level, name, start, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
