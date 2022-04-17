package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.UserInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInformation record);

    int insertSelective(UserInformation record);

    UserInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInformation record);

    int updateByPrimaryKey(UserInformation record);

    int selectIdByPhone(String phone);
    int selectIdByUsername(String username);

//    @Select("select * from user_info")
    List<UserInformation> selectAll();

    List<UserInformation> getAllForeach(List<Integer> list);

    int getCountsBySearchUsername(String username, int role);
    int getCountsBySearchNickname(String nickname, int role);
    int getCountsBySearchRealname(String realname, int role);
    int getCountsByRole(int role);

    List<UserInformation> SearchByUsername(int start, int size, String username, int role);
    List<UserInformation> SearchByRealname(int start, int size, String realname, int role);
    List<UserInformation> SearchByNickname(int start, int size, String nickname, int role);
    List<UserInformation> SearchByRole(int start, int size, int role);
}