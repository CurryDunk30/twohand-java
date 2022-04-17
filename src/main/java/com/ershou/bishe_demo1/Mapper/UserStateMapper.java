package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.UserState;

public interface UserStateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserState record);

    int insertSelective(UserState record);

    UserState selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserState record);

    int updateByPrimaryKey(UserState record);

    UserState selectByUid(int uid);

}