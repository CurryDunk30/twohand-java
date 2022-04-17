package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.ShopInformation;

import java.util.List;
import java.util.Map;

/**
 * Created by wsk1103 on 2017/5/12.
 */
public interface ShopInformationService {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopInformation record);

    int insertSelective(ShopInformation record);

    ShopInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopInformation record);

    int updateByPrimaryKey(ShopInformation record);

    List<ShopInformation> selectTen(Map map);

    List<ShopInformation> selectOffShelf(int uid, int start);

    int getCountsOffShelf(int uid);

    int getCounts();
    List<Integer> getAllIds(Integer searchState, String searchName, Integer searchLevel, Integer categoryType);

    int getUserSellCounts(Integer uid, Integer state, String name);
    int getSearchCounts(Integer searchState, String searchName, Integer searchLevel, Integer categoryType);

    int selectIdByImage(String image);

    List<ShopInformation> selectUsersBySize(Integer uid, Integer page, Integer size, Integer display);

    List<ShopInformation> selectUsersBySize(Integer uid, Integer page, Integer size, Integer display, String name);

    List<ShopInformation> SearchByPageSize(Integer page, Integer size, Integer searchState, String searchName, Integer searchLevel, Integer categoryType);

    List<ShopInformation> selectByName(String name);

    List<ShopInformation> selectByKind(Integer kind);

    List<ShopInformation> selectUserReleaseByUid(int uid);

    List<ShopInformation> selectUserUpByUid(int uid, int state);

}
