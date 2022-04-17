package com.ershou.bishe_demo1.Mapper;

import com.ershou.bishe_demo1.Pojo.ExchangeInfo;
import com.ershou.bishe_demo1.Pojo.ShopInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExchangeInfoMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteBySellerSid(Integer sellerSid);

    int insert(ExchangeInfo record);

    int insertSelective(ExchangeInfo record);

    ExchangeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExchangeInfo record);

    int updateByPrimaryKey(ExchangeInfo record);

    int getSearchStateCounts(String name, Integer kind, Integer level, Integer state);

    int getExchangeStateCounts(Integer exchangeUid, Integer state, String name);
    int getCountsByExchange(Integer exchangeUid);
    int getSellerStateCounts(Integer sellerUid,  Integer state, String name);
    int getCountsBySeller(Integer sellerUid);

    int getCounts();


    List<ExchangeInfo> selectByExchangeAndStatePage(Integer exchangeUid, Integer state, Integer start,Integer size);
    List<ExchangeInfo> selectByExchangeAndState(Integer exchangeUid, Integer state);
    List<ExchangeInfo> selectBySellerAndStatePage(Integer sellerUid, Integer state, Integer start,Integer size);
    List<ExchangeInfo> selectBySellerAndState(Integer sellerUid, Integer state);

    int selectIdByImage(String image);

    List<ExchangeInfo> selectByName(String name);
    List<ExchangeInfo> selectByKind(Integer kind);

}
