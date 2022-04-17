package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.ShopContext;

import java.util.List;

public interface ShopContextService {
    int deleteByPrimaryKey(Integer id);
    int deleteBySidAndType(Integer sid, Integer commentType);

    int insert(ShopContext record);

    int insertSelective(ShopContext record);

    ShopContext selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopContext record);

    int updateByPrimaryKey(ShopContext record);

    int getCounts(int sid);
    int getCountsByType(int commentType);

    List<ShopContext> findById(int sid, int start);

    List<ShopContext> selectBySid(int sid, int type);
    List<ShopContext> selectByTypePage(int commentType, int page, int size);
}
