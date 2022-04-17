package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.UserPassword;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPasswordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPassword record);

    int insertSelective(UserPassword record);

    UserPassword selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPassword record);

    int updateByPrimaryKey(UserPassword record);

    UserPassword selectByUid(Integer uid);
}