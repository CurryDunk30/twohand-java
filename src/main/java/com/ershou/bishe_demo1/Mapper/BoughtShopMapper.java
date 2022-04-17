package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.BoughtShop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoughtShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BoughtShop record);

    int insertSelective(BoughtShop record);

    BoughtShop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BoughtShop record);

    int updateByPrimaryKey(BoughtShop record);

    int getCounts(int uid);

    List<BoughtShop> selectByUid(int uid, int start);
}