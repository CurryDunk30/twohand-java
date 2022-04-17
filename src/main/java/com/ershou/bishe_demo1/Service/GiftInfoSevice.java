package com.ershou.bishe_demo1.Service;

import com.ershou.bishe_demo1.Pojo.GiftInfo;

import java.util.List;

public interface GiftInfoSevice {
    int deleteByPrimaryKey(Integer id);

    int insert(GiftInfo record);

    int insertSelective(GiftInfo record);

    GiftInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GiftInfo record);

    int updateByPrimaryKey(GiftInfo record);

    int getCountsByReceive(int receiveUid);
    int getCountsByGive(int giveUid);

    List<GiftInfo> selectPageByReceive(Integer receiveUid, Integer start, Integer size);
    List<GiftInfo> selectPageByGive(Integer giveUid, Integer start, Integer size);
    List<GiftInfo> selectByKind(Integer kind);
}
