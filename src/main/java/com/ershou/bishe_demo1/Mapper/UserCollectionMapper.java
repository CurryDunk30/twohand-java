package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.UserCollection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCollectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCollection record);

    int insertSelective(UserCollection record);

    UserCollection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCollection record);

    int updateByPrimaryKey(UserCollection record);

    int getCounts(int uid);

    List<UserCollection> selectByUid(int uid, int start);
}