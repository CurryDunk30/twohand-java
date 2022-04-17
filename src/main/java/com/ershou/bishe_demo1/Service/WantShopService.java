package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.WantShop;

import java.util.List;

/**
 * Created by wsk1103 on 2017/5/13.
 */
public interface WantShopService {
    int deleteByPrimaryKey(Integer id);

    int insert(WantShop record);

    int insertSelective(WantShop record);

    WantShop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WantShop record);

    int updateByPrimaryKey(WantShop record);

    int getCounts(int uid);

    int getCountsByState(int state);

    int getCountsByUidAndState(int uid, int state);

    List<WantShop> selectAll();
    List<WantShop> selectByKind(Integer kind);

    List<WantShop> searchStateBySize(Integer display, Integer kind, Integer level, String name, Integer page, Integer size);
    List<WantShop> searchStateByUserAndSize(Integer uid, Integer display, Integer kind, Integer level, String name, Integer page, Integer size);

}
