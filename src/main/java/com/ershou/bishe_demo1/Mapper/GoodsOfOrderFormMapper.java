package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.GoodsOfOrderForm;

import java.util.List;

public interface GoodsOfOrderFormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsOfOrderForm record);

    int insertSelective(GoodsOfOrderForm record);

    GoodsOfOrderForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsOfOrderForm record);

    int updateByPrimaryKey(GoodsOfOrderForm record);

    List<GoodsOfOrderForm> selectByOFid(int ofid);

    List<GoodsOfOrderForm> selectBySid(int sid);
}