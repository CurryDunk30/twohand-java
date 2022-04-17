package com.ershou.bishe_demo1.Service.Impl;

import com.ershou.bishe_demo1.Mapper.ShopInformationMapper;
import com.ershou.bishe_demo1.Pojo.ShopInformation;
import com.ershou.bishe_demo1.Service.ShopInformationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by wsk1103 on 2017/5/12.
 */
@Service
public class ShopInformationServiceImpl implements ShopInformationService{

    @Resource
    private ShopInformationMapper shopInformationMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return shopInformationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ShopInformation record) {
        return shopInformationMapper.insert(record);
    }

    @Override
    public int insertSelective(ShopInformation record) {
        return shopInformationMapper.insertSelective(record);
    }

    @Override
    public ShopInformation selectByPrimaryKey(Integer id) {
        return shopInformationMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopInformation record) {
        return shopInformationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ShopInformation record) {
        return shopInformationMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ShopInformation> selectTen(Map map) {
        return shopInformationMapper.selectTenSell(map);
    }

    @Override
    public List<ShopInformation> selectOffShelf(int uid, int start) {
        return shopInformationMapper.selectOffShell(uid,start);
    }

    @Override
    public int getCountsOffShelf(int uid) {
        return shopInformationMapper.getCountsOffShelf(uid);
    }


    @Override
    public int getCounts() {
        return shopInformationMapper.getCounts();
    }

    @Override
    public List<Integer> getAllIds(Integer searchState, String searchName, Integer searchLevel, Integer categoryType) {
        try {
            return shopInformationMapper.getAllIds(searchState, searchName, searchLevel, categoryType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getUserSellCounts(Integer uid, Integer state, String name) {
        if (state == 0 || state == 1){
            return shopInformationMapper.getUserStateCounts(uid, state, name);
        } else
        {
            return shopInformationMapper.getUserCounts(uid, name);
        }
    }

    @Override
    public int getSearchCounts(Integer searchState, String searchName, Integer searchLevel, Integer categoryType) {
        try{
            //    searchState为-1则表示查询全部,categoryType和searchLevel为-1则表示查询全部
            return shopInformationMapper.getSearchStateCounts(searchState, searchName, searchLevel, categoryType);
        } catch (Exception e) {
            return -1;
        }

    }
    @Override
    public int selectIdByImage(String image) {
        return shopInformationMapper.selectIdByImage(image);
    }

    @Override
    public List<ShopInformation> selectUsersBySize(Integer uid, Integer page, Integer size, Integer state) {
        Integer start = (page-1) * size;
        try{

            if(state == 0 || state == 1){
               return shopInformationMapper.selectUsersStateBySize(uid, start, size, state, "");
            }
            else
            {
                return shopInformationMapper.selectUsersBySize(uid, start, size, "");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ShopInformation> selectUsersBySize(Integer uid, Integer page, Integer size, Integer display, String name) {
        Integer start = (page-1) * size;
        try{

            if(display == 0 || display == 1){
                return shopInformationMapper.selectUsersStateBySize(uid, start, size, display, name);
            }
            else
            {
                return shopInformationMapper.selectUsersBySize(uid, start, size, name);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ShopInformation> SearchByPageSize(Integer page, Integer size, Integer searchState, String searchName, Integer searchLevel, Integer categoryType) {
        Integer start = (page-1) * size;
        try{

            if(searchState == 0 || searchState == 1){
//                categoryType和searchLevel为-1则表示查询全部
                return shopInformationMapper.searchStateBySize(start, size, searchState, searchName, searchLevel, categoryType);
            }
            else
            {
                return shopInformationMapper.searchBySize(start, size, searchName, searchLevel, categoryType);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ShopInformation> selectByName(String name){
        return shopInformationMapper.selectByName(name);
    }

    @Override
    public List<ShopInformation> selectByKind(Integer kind) {
        try {
            return shopInformationMapper.selectByKind(kind);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<ShopInformation> selectUserReleaseByUid(int uid) {
        return shopInformationMapper.selectUserReleaseByUid(uid);
    }

    @Override
    public List<ShopInformation> selectUserUpByUid(int uid, int state) {
        try {
            return shopInformationMapper.selectUserAndStateByUid(uid, state);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
