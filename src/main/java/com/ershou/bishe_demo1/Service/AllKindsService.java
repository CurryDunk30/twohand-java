package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.AllKinds;

import java.util.List;

/**
 * 获取商品类型CRUD
 */
public interface AllKindsService {
    int deleteByPrimaryKey(Integer id);

    int insert(AllKinds record);

    int insertSelective(AllKinds record);

    AllKinds selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AllKinds record);

    int updateByPrimaryKey(AllKinds record);

    List<AllKinds> selectAll();
}
