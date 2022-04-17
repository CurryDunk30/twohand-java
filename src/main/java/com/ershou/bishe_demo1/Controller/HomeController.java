package com.ershou.bishe_demo1.Controller;

import com.ershou.bishe_demo1.Service.*;
import com.ershou.bishe_demo1.Pojo.*;
import com.ershou.bishe_demo1.bean.ShopInformationBean;
import com.ershou.bishe_demo1.tool.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeController {
//    @Resource
//    private ShopInformationService shopInformationService;
//    @Resource
//    private SpecificeService specificeService;
//    @Resource
//    private ClassificationService classificationService;
//    @Resource
//    private AllKindsService allKindsService;
//    @Resource
//    private ShopContextService shopContextService;
    @Resource
    private UserInformationService userInformationService;

    @RequestMapping(value = {"/", "/home"})
    public Map<String, String> home(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>(3);
        map.put("作者信息", "xx");
        map.put("博客地址", "http://blog.itcodai.com");
        map.put("CSDN地址", "http://blog.csdn.net/eson_15");
        map.put("粉丝数量", "4153");
        UserInformation user1 = userInformationService.selectByPrimaryKey(123);
        return map;
    }

//    //通过分类的第三层id获取全名
//    private String getSortName(int sort) {
//        StringBuilder stringBuffer = new StringBuilder();
//        Specific specific = selectSpecificBySort(sort);
//        int cid = specific.getCid();
//        Classification classification = selectClassificationByCid(cid);
//        int aid = classification.getAid();
//        AllKinds allKinds = selectAllKindsByAid(aid);
//        stringBuffer.append(allKinds.getName());
//        stringBuffer.append("-");
//        stringBuffer.append(classification.getName());
//        stringBuffer.append("-");
//        stringBuffer.append(specific.getName());
////        System.out.println(sort);
//        return stringBuffer.toString();
//    }

    //获取商品，分页,一次性获取end个
//    private List<ShopInformation> selectTen(int start, int end) {
//        Map map = new HashMap();
//        map.put("start", (start - 1) * end);
//        map.put("end", end);
//        List<ShopInformation> list = shopInformationService.selectTen(map);
//        return list;
//    }

//
//    //获得商品总页数
//    private int getShopCounts() {
//        return shopInformationService.getCounts();
//    }
//
//    //获得商品留言总页数
//    private int getShopContextCounts(int sid) {
//        return shopContextService.getCounts(sid);
//    }
//
//    //获得商品留言，10条
//    private List<ShopContext> selectShopContextBySid(int sid, int start) {
//        return shopContextService.findById(sid, (start - 1) * 10);
//    }
}
