package com.ershou.bishe_demo1.Controller;

import com.ershou.bishe_demo1.Pojo.ExchangeInfo;
import com.ershou.bishe_demo1.Pojo.GiftInfo;
import com.ershou.bishe_demo1.Pojo.ShopInformation;
import com.ershou.bishe_demo1.Pojo.UserInformation;
import com.ershou.bishe_demo1.Service.*;
import com.ershou.bishe_demo1.tool.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

// 赠与业务的controller
@RestController
@RequestMapping("/gift")
public class GiftController {
    @Resource
    ExchangeInfoService exchangeInfoService;
    @Resource
    UserInformationService userInformationService;
    @Resource
    ShopInformationService shopInformationService;
    @Resource
    AllKindsService allKindsService;
    @Resource
    GiftInfoSevice giftInfoSevice;


    @RequestMapping("/save")
    public Map<String, Object> requestExchange(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        boolean isInsert = false;
        String giftId = request.getParameter("giftId");
        if (StringUtils.getInstance().isNullOrEmpty(request.getParameter("giftId"))) {
            isInsert = true;
        }

        String giftName = request.getParameter("giftName");
        String giftLevel = request.getParameter("giftLevel");
        String giftQuantity = request.getParameter("giftQuantity");
        String giftDescription = request.getParameter("giftDescription");
        String categoryType = request.getParameter("categoryType");
        String giveId = request.getParameter("giveId");
        String receiveUsername = request.getParameter("receiveUsername");
        String receiveName = request.getParameter("receiveName");
        String receiveAddress = request.getParameter("receiveAddress");
        String receiveTel = request.getParameter("receiveTel");

        GiftInfo _gift = new GiftInfo();
        _gift.setName(giftName);
        _gift.setLevel(Integer.valueOf(giftLevel));
        _gift.setQuantity(Integer.valueOf(giftQuantity));
        _gift.setRemark(giftDescription);
        _gift.setKind(Integer.valueOf(categoryType));
        _gift.setGiveuid(Integer.valueOf(giveId));
        _gift.setReceivename(receiveName);
        _gift.setAddress(receiveAddress);
        _gift.setTel(receiveTel);

        int _receiveId = userInformationService.selectIdByUsername(receiveUsername);
        if(_receiveId == -1) {
            Response.replace("code", 4);
            Response.replace("message", "不存在接收赠予的用户");
            return Response;
        }
        _gift.setReceiveuid(_receiveId);
        _gift.setCreatetime(new Date());
        try {
            int result = 0;
            if (isInsert){
                result = giftInfoSevice.insert(_gift);
            } else {
                if(giftInfoSevice.selectByPrimaryKey(Integer.valueOf(giftId)) != null)
                {
                    _gift.setId(Integer.valueOf(giftId));
                } else {
                    Response.replace("code", 4);
                    Response.replace("message", "修改失败，请确认");
                    return Response;
                }
                result = giftInfoSevice.updateByPrimaryKeySelective(_gift);
            }
            if (result != 1) {
                Response.replace("code", 4);
                Response.replace("message", "添加失败");
                return Response;
            }
        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }


        return Response;
    }

    @RequestMapping("/receive/record/list")
    public Map<String, Object> ListReceiveByPagesize(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer receiveId = Integer.valueOf(request.getParameter("uid"));
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer size = Integer.valueOf(request.getParameter("size"));

        try {
            int nums = giftInfoSevice.getCountsByReceive(receiveId);
            Map<String, Object> map = new HashMap<>();
            if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 5);
                Response.replace("message", "数据不足");
                return Response;
            }
            map.put("totalElements", nums);
            List<GiftInfo> result = giftInfoSevice.selectPageByReceive(receiveId, page, size);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for(GiftInfo _gift : result)
            {
                Map<String, Object> shopMap = new HashMap<>();

                shopMap.put("giftName", _gift.getName());
                shopMap.put("giftId", _gift.getId());
                shopMap.put("giftLevel", _gift.getLevel());
                shopMap.put("giftRemark", _gift.getRemark());
                String _cateName = allKindsService.selectByPrimaryKey(_gift.getKind()).getName();
                shopMap.put("giftCategoryType", _gift.getKind());
                shopMap.put("giftCategoryName", _cateName);

                shopMap.put("giftQuantity", _gift.getQuantity());
                shopMap.put("giftAddress", _gift.getAddress());

                String _date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(_gift.getCreatetime());
                shopMap.put("createTime", _date);

                Integer _giverId = _gift.getGiveuid();
                shopMap.put("giverId", _giverId);
                UserInformation user = userInformationService.selectByPrimaryKey(_giverId);
                shopMap.put("giverName", user.getRealname());
                shopMap.put("giverUsername", user.getUsername());
                shopMap.put("giverTel", user.getPhone());

                list.add(shopMap);
            }
            map.put("content", list);
            Response.put("data", map);
        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

    @RequestMapping("/give/record/list")
    public Map<String, Object> ListGiveByPagesize(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer giverId = Integer.valueOf(request.getParameter("uid"));
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer size = Integer.valueOf(request.getParameter("size"));

        try {
            int nums = giftInfoSevice.getCountsByGive(giverId);
            Map<String, Object> map = new HashMap<>();
            if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 5);
                Response.replace("message", "数据不足");
                return Response;
            }
            map.put("totalElements", nums);
            List<GiftInfo> result = giftInfoSevice.selectPageByGive(giverId, page, size);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for(GiftInfo _gift : result)
            {
                Map<String, Object> shopMap = new HashMap<>();

                shopMap.put("giftName", _gift.getName());
                shopMap.put("giftId", _gift.getId());
                shopMap.put("giftLevel", _gift.getLevel());
                String _cateName = allKindsService.selectByPrimaryKey(_gift.getKind()).getName();
                shopMap.put("giftCategoryType", _gift.getKind());
                shopMap.put("giftCategoryName", _cateName);
                shopMap.put("giftAddress", _gift.getAddress());
                shopMap.put("giftRemark", _gift.getRemark());


                shopMap.put("giftQuantity", _gift.getQuantity());

                String _date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(_gift.getCreatetime());
                shopMap.put("createTime", _date);

                Integer _receiveId = _gift.getReceiveuid();
                shopMap.put("receiveId", _receiveId);
                UserInformation user = userInformationService.selectByPrimaryKey(_receiveId);
                shopMap.put("receiveName", _gift.getReceivename());
                shopMap.put("receiveUsername", user.getUsername());
                shopMap.put("receiveTel", user.getPhone());

                list.add(shopMap);
            }
            map.put("content", list);
            Response.put("data", map);
        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

    @RequestMapping("/receive/delete")
    public Map<String, Object> ReceiveDelete(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer receiveUid = Integer.valueOf(request.getParameter("uid"));
        Integer giftId = Integer.valueOf(request.getParameter("giftId"));

        try {
            GiftInfo result = giftInfoSevice.selectByPrimaryKey(giftId);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "未查到交换数据");
                return Response;
            }
            if (!result.getReceiveuid().equals(receiveUid))
            {
                Response.replace("code", 4);
                Response.replace("message", "您不可操作性，请检查账号");
                return Response;
            }
            int _res = giftInfoSevice.deleteByPrimaryKey(giftId);
            if(_res == -1)
            {
                Response.replace("code", 4);
                Response.replace("message", "删除失败");
                return Response;
            }

        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }


    @RequestMapping("/give/delete")
    public Map<String, Object> GiverDelete(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer giverUid = Integer.valueOf(request.getParameter("uid"));
        Integer giftId = Integer.valueOf(request.getParameter("giftId"));

        try {
            GiftInfo result = giftInfoSevice.selectByPrimaryKey(giftId);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "未查到交换数据");
                return Response;
            }
            if (!result.getGiveuid().equals(giverUid))
            {
                Response.replace("code", 4);
                Response.replace("message", "您不可操作性，请检查账号");
                return Response;
            }
            int _res = giftInfoSevice.deleteByPrimaryKey(giftId);
            if(_res == -1)
            {
                Response.replace("code", 4);
                Response.replace("message", "删除失败");
                return Response;
            }

        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

}
