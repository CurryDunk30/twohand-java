package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.AllKinds;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AllKindsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AllKinds record);

    int insertSelective(AllKinds record);

    AllKinds selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AllKinds record);

    int updateByPrimaryKey(AllKinds record);

    List<AllKinds> selectAll();
}