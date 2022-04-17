package com.ershou.bishe_demo1.Service.Impl;

import com.ershou.bishe_demo1.Mapper.GiftInfoMapper;
import com.ershou.bishe_demo1.Pojo.GiftInfo;
import com.ershou.bishe_demo1.Service.GiftInfoSevice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GiftInfoServiceImpl implements GiftInfoSevice {
    @Resource
    GiftInfoMapper giftInfoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        try {
            return giftInfoMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insert(GiftInfo record) {
        try {
            return giftInfoMapper.insert(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insertSelective(GiftInfo record) {
        try {
            return giftInfoMapper.insertSelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public GiftInfo selectByPrimaryKey(Integer id) {
        try {
            return giftInfoMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateByPrimaryKeySelective(GiftInfo record) {
        try {
            return giftInfoMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateByPrimaryKey(GiftInfo record) {
        try {
            return giftInfoMapper.updateByPrimaryKey(record);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getCountsByReceive(int receiveUid) {
        try {
            return giftInfoMapper.getCountsByReceive(receiveUid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getCountsByGive(int giveUid) {
        try {
            return giftInfoMapper.getCountsByGive(giveUid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<GiftInfo> selectPageByReceive(Integer receiveUid, Integer page, Integer size) {
        Integer start = (page-1) * size;
        try {
            return giftInfoMapper.selectPageByReceive(receiveUid, start, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GiftInfo> selectPageByGive(Integer giveUid, Integer page, Integer size) {
        Integer start = (page-1) * size;
        try {
            return giftInfoMapper.selectPageByGive(giveUid, start, size);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GiftInfo> selectByKind(Integer kind) {
        try {
            return giftInfoMapper.selectByKind(kind);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
