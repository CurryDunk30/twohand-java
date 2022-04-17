package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.ShopCar;

import java.util.List;

/**
 * Created by wsk1103 on 2017/5/13.
 */
public interface ShopCarService {
    int deleteByPrimaryKey(Integer id);
    int deleteByUidSid(Integer uid, Integer sid);
    int deleteBySid(Integer sid);

    int getUserBuyCounts(Integer uid);

    int insert(ShopCar record);

    int insertSelective(ShopCar record);

    ShopCar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopCar record);

    int updateByPrimaryKey(ShopCar record);

    List<ShopCar> selectByUid(int uid);
    List<ShopCar> selectByUid(int uid, int page, int size);

    ShopCar selectByUidSid(Integer uid, Integer sid);
}
