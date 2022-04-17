package com.ershou.bishe_demo1.Controller;

import com.ershou.bishe_demo1.Pojo.*;
import com.ershou.bishe_demo1.Service.*;
import com.ershou.bishe_demo1.tool.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/want")
public class WantController {
    @Resource
    AllKindsService allKindsService;
    @Resource
    ShopInformationService shopInformationService;
    @Resource
    UserInformationService userInformationService;
    @Resource
    ShopCarService shopCarService;
    @Resource
    WantShopService wantShopService;
    @Resource
    ShopContextService shopContextService;
    @Resource
    CommentReplyService commentReplyService;


    @RequestMapping("/save")
    public Map<String, Object> saveWant(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        boolean isInsert = false;
        if(StringUtils.getInstance().isNullOrEmpty(request.getParameter("wantId")))
        {
            isInsert = true;
        }
        String wantId = request.getParameter("wantId");

        String uid = request.getParameter("uid");
        String wantName = request.getParameter("wantName");
        String wantLevel = request.getParameter("wantLevel");
        String wantPrice = request.getParameter("wantPrice");
        String wantQuantity = request.getParameter("wantQuantity");
        String wantDescription = request.getParameter("wantDescription");
        String wantState = request.getParameter("wantState");
        String categoryType = request.getParameter("categoryType");
        String image = request.getParameter("image");

        WantShop _want = new WantShop();
        _want.setLevel(Integer.valueOf(wantLevel));
        _want.setName(wantName);
        _want.setUid(Integer.valueOf(uid));
        _want.setPrice(new BigDecimal(wantPrice));
        _want.setDisplay(Integer.valueOf(wantState));
        _want.setQuantity(Integer.valueOf(wantQuantity));
        _want.setRemark(wantDescription);
        _want.setImage(image);
        _want.setKind(Integer.valueOf(categoryType));
        _want.setModified(new Date());
        try {
            int result = 0;
            if (isInsert){
                result = wantShopService.insert(_want);
            } else {
                _want.setId(Integer.valueOf(wantId));
                result = wantShopService.updateByPrimaryKey(_want);
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

    @RequestMapping("/on_want")
    public Map<String, Object> OnWant(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer wantId = Integer.valueOf(request.getParameter("wantId"));
        try {
            WantShop wshop = wantShopService.selectByPrimaryKey(wantId);

            wshop.setDisplay(1);
            int result = wantShopService.updateByPrimaryKeySelective(wshop);
            if (result != 1) {
                Response.replace("code", 4);
                Response.replace("message", "删除商品失败");
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

    @RequestMapping("/off_want")
    public Map<String, Object> OffWant(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String wantId = request.getParameter("wantId");
        try {
            WantShop wshop = wantShopService.selectByPrimaryKey(Integer.valueOf(wantId));

            wshop.setDisplay(0);
            int result = wantShopService.updateByPrimaryKeySelective(wshop);
            if (result != 1) {
                Response.replace("code", 4);
                Response.replace("message", "删除商品失败");
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


    @RequestMapping("/delete")
    public Map<String, Object> deleteProduct(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String wantId = request.getParameter("wantId");
        try {
            int commentType = 0;
            List<ShopContext> _comments = shopContextService.selectBySid(Integer.parseInt(wantId), commentType);
            for(ShopContext _comment : _comments){
                int _commentId = _comment.getId();
                int _delReplyRes = commentReplyService.deleteByCommentId(_commentId);
                if (_delReplyRes < 0) {
                    Response.replace("code", 4);
                    Response.replace("message", "删除回复失败");
                    return Response;
                }
            }
            int _delCommentRes = shopContextService.deleteBySidAndType(Integer.valueOf(wantId), commentType);
            if (_delCommentRes < 0) {
                Response.replace("code", 4);
                Response.replace("message", "删除留言失败");
                return Response;
            }

            int result = wantShopService.deleteByPrimaryKey(Integer.valueOf(wantId));
            if (result != 1) {
                Response.replace("code", 4);
                Response.replace("message", "删除商品失败");
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

    @RequestMapping("/search/list_all")
    public Map<String, Object> ListProductByPagesize(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        int state = Integer.parseInt(request.getParameter("wantState"));


        try {
            int nums = wantShopService.getCountsByState(state);
            Map<String, Object> map = new HashMap<>();
            if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 4);
                Response.replace("message", "数据不足");
                return Response;
            }
            map.put("totalElements", nums);
            List<WantShop> result = wantShopService.searchStateBySize(state, -1, -1, "", page, size);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for(WantShop shop : result)
            {
                Map<String, Object> shopMap = new HashMap<>();
                shopMap.put("wantState", shop.getDisplay());
                shopMap.put("wantName", shop.getName());
                shopMap.put("wantId", shop.getId());
                shopMap.put("wantIcon", shop.getImage());
                shopMap.put("wantLevel", shop.getLevel());
                shopMap.put("wantPrice", shop.getPrice());
                shopMap.put("wantQuantity", shop.getQuantity());
                shopMap.put("wantDescription", shop.getRemark());
                shopMap.put("categoryType", shop.getKind());
                String _date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(shop.getModified());
                shopMap.put("updateTime", _date);
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

    @RequestMapping("/search/list_user")
    public Map<String, Object> ListUserWantByPagesize(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        int uid = Integer.parseInt(request.getParameter("uid"));
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        int state = Integer.parseInt(request.getParameter("wantState"));


        try {
            int nums = wantShopService.getCountsByUidAndState(uid, state);
            Map<String, Object> map = new HashMap<>();
            if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 5);
                Response.replace("message", "数据不足");
                return Response;
            }
            map.put("totalElements", nums);
            List<WantShop> result = wantShopService.searchStateByUserAndSize(uid, state, -1, -1, "", page, size);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for(WantShop shop : result)
            {
                Map<String, Object> shopMap = new HashMap<>();
                shopMap.put("wantState", shop.getDisplay());
                shopMap.put("wantName", shop.getName());
                shopMap.put("wantId", shop.getId());
                shopMap.put("wantIcon", shop.getImage());
                shopMap.put("wantLevel", shop.getLevel());
                shopMap.put("wantPrice", shop.getPrice());
                shopMap.put("wantQuantity", shop.getQuantity());
                shopMap.put("wantDescription", shop.getRemark());
                shopMap.put("categoryType", shop.getKind());
                String _date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(shop.getModified());
                shopMap.put("updateTime", _date);
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


    @RequestMapping("/search/id")
    public Map<String, Object> ProductItem(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer wantId = Integer.valueOf(request.getParameter("wantId"));
        Integer uid = Integer.valueOf(request.getParameter("uid"));

        try {
            Map<String, Object> shopMap = new HashMap<>();

            WantShop shop = wantShopService.selectByPrimaryKey(wantId);
            UserInformation user = userInformationService.selectByPrimaryKey(shop.getUid());
            shopMap.put("wanterUid", user.getId());
            shopMap.put("wanterNickname", user.getNickname());
            shopMap.put("wantName", shop.getName());
            shopMap.put("wantLevel", shop.getLevel());
            shopMap.put("wantQuantity", shop.getQuantity());
            shopMap.put("wantPrice", shop.getPrice());
            shopMap.put("wantId", shop.getId());
            shopMap.put("wantDescription", shop.getRemark());
            shopMap.put("wantIcon", shop.getImage());
            shopMap.put("wantState", shop.getDisplay());
            shopMap.put("categoryType", shop.getKind());
            shopMap.put("categoryName",allKindsService.selectByPrimaryKey(shop.getKind()).getName());

            Response.put("data", shopMap);
        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

}
