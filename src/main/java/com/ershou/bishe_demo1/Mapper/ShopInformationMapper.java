package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.ShopInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopInformation record);

    int insertSelective(ShopInformation record);

    ShopInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopInformation record);

    int updateByPrimaryKey(ShopInformation record);

    List<ShopInformation> selectTenSell(Map map);

    List<ShopInformation> selectOffShell(Integer uid, Integer start);

    List<ShopInformation> selectUsersBySize(Integer uid, Integer start, Integer size, String name);
    List<ShopInformation> selectUsersStateBySize(Integer uid, Integer start, Integer size, Integer state, String name);
    List<ShopInformation> searchStateBySize(Integer start, Integer size, Integer display, String name, Integer level, Integer kind);
    List<ShopInformation> searchBySize(Integer start, Integer size, String name, Integer level, Integer kind);

    List<ShopInformation> selectUserAndStateByUid(Integer uid, Integer state);

    int getCountsOffShelf(Integer uid);

    int getCounts();
    List<Integer> getAllIds(Integer display, String name, Integer level, Integer kind);

    int getUserCounts(Integer uid, String name);

    int getUserStateCounts(Integer uid, Integer state, String name);

    int selectIdByImage(String image);

    List<ShopInformation> selectByName(String name);

    //通过分类选择
    @Select("select * from shopinformation where sort=#{sort} and display =1 limit 12")
    List<ShopInformation> selectBySort(int sort);

    //选择用户的发布
    @Select("select * from shopinformation where uid=#{uid} and display=1 order by id desc limit 12")
    List<ShopInformation> selectUserReleaseByUid(int uid);

    int getSearchStateCounts(Integer display, String name, Integer level, Integer kind);

    List<ShopInformation> selectByKind(Integer kind);
}