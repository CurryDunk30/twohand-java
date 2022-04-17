package com.ershou.bishe_demo1.Service;


import com.ershou.bishe_demo1.Pojo.ExchangeInfo;
import com.ershou.bishe_demo1.Pojo.ShopInformation;

import java.util.List;

public interface ExchangeInfoService {
    int deleteByPrimaryKey(Integer id);
    int deleteBySellerSid(Integer id);

    int insert(ExchangeInfo record);

    int insertSelective(ExchangeInfo record);

    ExchangeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExchangeInfo record);

    int updateByPrimaryKey(ExchangeInfo record);

    List<ExchangeInfo> selectBySeller(Integer sellerUid, Integer state, Integer page,Integer size);
    List<ExchangeInfo> selectBySeller(Integer sellerUid, Integer state);

    List<ExchangeInfo> selectByExchange(Integer exchangeUid, Integer state, Integer page,Integer size);
    List<ExchangeInfo> selectByExchange(Integer exchangeUid, Integer state);
    List<ExchangeInfo> selectByKind(Integer kind);

    int getCountsByExchange(Integer exchangeUid, Integer state, String name);
    int getCountsByExchange(Integer exchangeUid);
    int getCountsBySeller(Integer sellerUid,  Integer state, String name);
    int getCountsBySeller(Integer sellerUid);
}
