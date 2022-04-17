package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.ShopCar;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopCarMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByUidSid(Integer uid, Integer sid);
    int deleteBySid(Integer sid);

    int insert(ShopCar record);

    int getUserBuyCounts(Integer uid);

    int insertSelective(ShopCar record);

    ShopCar selectByPrimaryKey(Integer id);

    List<ShopCar> selectUsersBySize(Integer uid, Integer start, Integer size);

    int updateByPrimaryKeySelective(ShopCar record);

    int updateByPrimaryKey(ShopCar record);

    List<ShopCar> selectByUid(int uid);
    ShopCar selectByUidSid(Integer uid, Integer sid);
}