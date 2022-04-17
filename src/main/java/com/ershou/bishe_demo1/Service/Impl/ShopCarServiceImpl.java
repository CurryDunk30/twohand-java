package com.ershou.bishe_demo1.Service.Impl;

import com.ershou.bishe_demo1.Mapper.ShopCarMapper;
import com.ershou.bishe_demo1.Pojo.ShopCar;
import com.ershou.bishe_demo1.Service.ShopCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wsk1103 on 2017/5/13.
 */
@Service
public class ShopCarServiceImpl implements ShopCarService {
    @Resource
    private ShopCarMapper goodsCarMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int deleteByUidSid(Integer uid, Integer sid){
        try
        {
            return goodsCarMapper.deleteByUidSid(uid, sid);
        } catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int deleteBySid(Integer sid){
        try
        {
            return goodsCarMapper.deleteBySid(sid);
        } catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int getUserBuyCounts(Integer uid){
        try{
            return goodsCarMapper.getUserBuyCounts(uid);
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insert(ShopCar record) {
        try{
            return goodsCarMapper.insert(record);
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int insertSelective(ShopCar record) {
        return goodsCarMapper.insertSelective(record);
    }

    @Override
    public ShopCar selectByPrimaryKey(Integer id) {
        return goodsCarMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ShopCar record) {
        try{
            return goodsCarMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int updateByPrimaryKey(ShopCar record) {
        return goodsCarMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ShopCar> selectByUid(int scid) {
        try {
            return goodsCarMapper.selectByUid(scid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ShopCar> selectByUid(int uid, int page, int size) {
        Integer start = (page-1) * size;
        try{

            return goodsCarMapper.selectUsersBySize(uid, start, size);

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ShopCar selectByUidSid(Integer uid, Integer sid) {
        try
        {
            return goodsCarMapper.selectByUidSid(uid, sid);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
