package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);

    int getCounts(int uid);

    List<UserAddress> selectByUid(int uid);
    List<UserAddress> selectDefaultByUid(int uid);
}
