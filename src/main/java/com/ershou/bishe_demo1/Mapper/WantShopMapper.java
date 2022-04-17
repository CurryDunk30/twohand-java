package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.WantShop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WantShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WantShop record);

    int insertSelective(WantShop record);

    WantShop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WantShop record);

    int updateByPrimaryKey(WantShop record);

    int getCounts(int uid);

    int getCountsByState(int state);

    int getCountsByUidAndState(int uid, int state);

    int searchStateBySize(Integer display, Integer kind, Integer level, String name);

    List<WantShop> selectAll();
    List<WantShop> selectByKind(Integer kind);

    List<WantShop> searchStateBySize(Integer display, Integer kind, Integer level, String name, Integer start, Integer size);
    List<WantShop> searchStateByUserAndSize(Integer uid, Integer display, Integer kind, Integer level, String name, Integer start, Integer size);
}