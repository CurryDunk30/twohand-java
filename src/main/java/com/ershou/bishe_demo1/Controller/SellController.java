package com.ershou.bishe_demo1.Controller;

import com.alibaba.fastjson.JSONArray;
import com.ershou.bishe_demo1.Pojo.*;
import com.ershou.bishe_demo1.Service.*;
import com.ershou.bishe_demo1.tool.StringUtils;
import javafx.beans.binding.ObjectExpression;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/sell")
public class SellController {
    @Resource
    AllKindsService allKindsService;
    @Resource
    ShopInformationService shopInformationService;
    @Resource
    UserInformationService userInformationService;
    @Resource
    ShopContextService shopContextService;
    @Resource
    CommentReplyService commentReplyService;
    @Resource
    ShopCarService shopCarService;
    @Resource
    OrderFormService orderFormService;
    @Resource
    GoodsOfOrderFormService goodsOfOrderFormService;
    @Resource
    ExchangeInfoService exchangeInfoService;

    @RequestMapping("/product/save")
    public Map<String, Object> saveProduct(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        boolean isInsert = false;
        String productId = request.getParameter("productId");
        if(StringUtils.getInstance().isNullOrEmpty(request.getParameter("productId")))
        {
            isInsert = true;
        }
        String username = request.getParameter("username");
        String productName = request.getParameter("productName");
        String productSecondhand = request.getParameter("productSecondhand");
        String productPrice = request.getParameter("productPrice");
        String productStock = request.getParameter("productStock");
        String productDescription = request.getParameter("productDescription");
        String productStatus = request.getParameter("productStatus");
        String categoryType = request.getParameter("categoryType");
        String image = request.getParameter("image");
        ShopInformation shopInformation = new ShopInformation();
        shopInformation.setName(productName);
        shopInformation.setDisplay(Integer.valueOf(productStatus));
        shopInformation.setPrice(new BigDecimal(productPrice));
        shopInformation.setLevel(Integer.valueOf(productSecondhand));
        shopInformation.setQuantity(Integer.valueOf(productStock));
        shopInformation.setRemark(productDescription);
        shopInformation.setImage(image);
        shopInformation.setKind(Integer.valueOf(categoryType));
        shopInformation.setSales(0);
        try {
            shopInformation.setUid(userInformationService.selectIdByUsername(username));
            int result = 0;
            if (isInsert){
                result = shopInformationService.insert(shopInformation);
            } else {
                shopInformation.setId(Integer.valueOf(request.getParameter("productId")));
                result = shopInformationService.updateByPrimaryKeySelective(shopInformation);
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

    @RequestMapping("/product/on_sale")
    public Map<String, Object> OnSaleProduct(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer shopId = Integer.valueOf(request.getParameter("productId"));
        try {
            ShopInformation shop = shopInformationService.selectByPrimaryKey(shopId);
//            看前端0为上架
            shop.setDisplay(0);
            int result = shopInformationService.updateByPrimaryKeySelective(shop);
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

    @RequestMapping("/product/off_sale")
    public Map<String, Object> OffSaleProduct(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer shopId = Integer.valueOf(request.getParameter("productId"));
        try {
            ShopInformation shop = shopInformationService.selectByPrimaryKey(shopId);
//            看前端0为上架
            shop.setDisplay(1);
            int result = shopInformationService.updateByPrimaryKeySelective(shop);
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


    @RequestMapping("/product/delete")
    public Map<String, Object> deleteProduct(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer shopId = Integer.valueOf(request.getParameter("productId"));
        try {
//            首先判断订单中是否包含有该物品，如果有则无法删除。
            List<GoodsOfOrderForm> goods = goodsOfOrderFormService.selectBySid(shopId);
            for (GoodsOfOrderForm goodForm: goods){
                if (goodForm.getSid().equals(shopId))
                {
                    Response.replace("code", 4);
                    Response.replace("message", "订单中存在了该商品，不允许删除，可删除订单进而完成商品删除");
                    return Response;
                }
            }

            int commentType = 1;
            List<ShopContext> _comments = shopContextService.selectBySid(shopId, commentType);
            for(ShopContext _comment : _comments){
                int _commentId = _comment.getId();
                int _delReplyRes = commentReplyService.deleteByCommentId(_commentId);
                if (_delReplyRes < 0) {
                    Response.replace("code", 4);
                    Response.replace("message", "删除回复失败");
                    return Response;
                }
            }
            int _delCommentRes = shopContextService.deleteBySidAndType(shopId, commentType);
            if (_delCommentRes < 0) {
                Response.replace("code", 4);
                Response.replace("message", "删除留言失败");
                return Response;
            }

            int _delExchange = exchangeInfoService.deleteBySellerSid(shopId);
            if (_delExchange < 0) {
                Response.replace("code", 4);
                Response.replace("message", "删除交换失败");
                return Response;
            }

            int _delShopCar = shopCarService.deleteBySid(shopId);
            if (_delShopCar < 0) {
                Response.replace("code", 4);
                Response.replace("message", "删除购物车失败");
                return Response;
            }

//            最后一步删除商品
            int result = shopInformationService.deleteByPrimaryKey(shopId);
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

    @RequestMapping("/product/list_all_up")
    public Map<String, Object> ListProductUp(HttpServletRequest request,
                                                     @RequestParam("state")String state,
                                                     @RequestParam("selleruid")String sellerUid) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
//        默认搜索的是所有上架商品
        int searchSellerId = (Objects.equals(state, ""))? -1: Integer.parseInt(sellerUid);
        int searchState = (Objects.equals(state, ""))? -1: Integer.parseInt(state);


        try {
            List<ShopInformation> result = shopInformationService.selectUserUpByUid(searchSellerId, searchState);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for(ShopInformation shop : result)
            {
                Map<String, Object> shopMap = new HashMap<>();
                shopMap.put("productStatus", shop.getDisplay());
                shopMap.put("productName", shop.getName());
                shopMap.put("productId", shop.getId());
                shopMap.put("productIcon", shop.getImage());
                shopMap.put("productSecondhand", shop.getLevel());
                shopMap.put("productPrice", shop.getPrice());
                shopMap.put("productStock", shop.getQuantity());
                shopMap.put("productDescription", shop.getRemark());
                shopMap.put("categoryType", shop.getKind());
                list.add(shopMap);
            }
            Response.put("data", list);
        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

    @RequestMapping("/product/list")
    public Map<String, Object> ListProductByPagesize(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String username = request.getParameter("username");
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer size = Integer.valueOf(request.getParameter("size"));
        Integer state = Integer.valueOf(request.getParameter("productStatus"));

        try {
            Integer uid = userInformationService.selectIdByUsername(username);
            int nums = shopInformationService.getUserSellCounts(uid, state, "");
            Map<String, Object> map = new HashMap<>();
            if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 5);
                Response.replace("message", "数据不足");
                return Response;
            }
            map.put("totalElements", nums);
            List<ShopInformation> result = shopInformationService.selectUsersBySize(uid, page, size, state);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for(ShopInformation shop : result)
            {
                Map<String, Object> shopMap = new HashMap<>();
                shopMap.put("productStatus", shop.getDisplay());
                shopMap.put("productName", shop.getName());
                shopMap.put("productId", shop.getId());
                shopMap.put("productIcon", shop.getImage());
                shopMap.put("productSecondhand", shop.getLevel());
                shopMap.put("productPrice", shop.getPrice());
                shopMap.put("productStock", shop.getQuantity());
                shopMap.put("productDescription", shop.getRemark());
                shopMap.put("categoryType", shop.getKind());
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

    @RequestMapping("/product/in_list")
    public Map<String, Object> InListProductByPagesize(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        JSONArray _list = JSONObject.parseArray(request.getParameter("productIds"));
//        JSONObject[] json = new JSONObject[]{JSONObject.parseObject(request.getParameter("productIds"))};
//        String productJson = request.getParameter("productIds");
        List<Integer> productIds = _list.toJavaList(Integer.class);

        try {
            List<Map<String, Object>> list = new ArrayList<>();
            for(Integer productId : productIds)
            {
                ShopInformation shop = shopInformationService.selectByPrimaryKey(productId);
                ShopCar sc = shopCarService.selectByUidSid(uid, productId);
                Integer sellerId = shop.getUid();
                UserInformation user = userInformationService.selectByPrimaryKey(sellerId);
                Map<String, Object> shopMap = new HashMap<>();
                shopMap.put("productStatus", shop.getDisplay());
                shopMap.put("productName", shop.getName());
                shopMap.put("productId", shop.getId());
                shopMap.put("productIcon", shop.getImage());
                shopMap.put("productSecondhand", shop.getLevel());
                shopMap.put("productPrice", shop.getPrice());
                shopMap.put("productStock", shop.getQuantity());
                shopMap.put("productDescription", shop.getRemark());
                shopMap.put("categoryType", shop.getKind());
                shopMap.put("sellerId", sellerId);
                shopMap.put("sellerName", user.getNickname());
                shopMap.put("productQuantity", sc.getQuantity());
                list.add(shopMap);
            }
            Response.put("data", list);
        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

    @RequestMapping("/product/search")
    public Map<String, Object> ListUserProductByPagesize(HttpServletRequest request,
                                                         @RequestParam("productName") String productName,
                                                         @RequestParam("productStatus") Integer state) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer sellerUid = Integer.valueOf(request.getParameter("sellerUid"));
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Integer searchState = (state == null) ? -1:state;
        String searchName = (productName == null) ? "":productName;


        try {
            int nums = shopInformationService.getUserSellCounts(sellerUid, searchState, searchName);
            Map<String, Object> map = new HashMap<>();
            if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 4);
                Response.replace("message", "数据不足");
                return Response;
            }
            map.put("totalElements", nums);
            List<ShopInformation> result = shopInformationService.selectUsersBySize(sellerUid, page, size, searchState, searchName);

            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for(ShopInformation shop : result)
            {
                Map<String, Object> shopMap = new HashMap<>();
                shopMap.put("productStatus", shop.getDisplay());
                shopMap.put("productName", shop.getName());
                shopMap.put("productId", shop.getId());
                shopMap.put("productIcon", shop.getImage());
                shopMap.put("productSecondhand", shop.getLevel());
                shopMap.put("productPrice", shop.getPrice());
                shopMap.put("productStock", shop.getQuantity());
                shopMap.put("productDescription", shop.getRemark());
                shopMap.put("categoryType", shop.getKind());
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

    @RequestMapping("/product/id")
    public Map<String, Object> ProductItem(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer sid = Integer.valueOf(request.getParameter("productId"));
        String username = request.getParameter("username");

        try {
            Integer uid = userInformationService.selectIdByUsername(username);
            Map<String, Object> shopMap = new HashMap<>();

            ShopInformation shop = shopInformationService.selectByPrimaryKey(sid);
            UserInformation user = userInformationService.selectByPrimaryKey(shop.getUid());
            shopMap.put("sellerUid", user.getId());
            shopMap.put("sellerNickname", user.getNickname());
            shopMap.put("productStatus", shop.getDisplay());
            shopMap.put("productName", shop.getName());
            shopMap.put("productId", shop.getId());
            shopMap.put("productIcon", shop.getImage());
            shopMap.put("productSecondhand", shop.getLevel());
            shopMap.put("productPrice", shop.getPrice());
            shopMap.put("productStock", shop.getQuantity());
            shopMap.put("productDescription", shop.getRemark());
            shopMap.put("categoryType", shop.getKind());
            shopMap.put("categoryName",allKindsService.selectByPrimaryKey(shop.getKind()).getName());
            // 获取当前用户的购物车信息
            ShopCar sc = shopCarService.selectByUidSid(uid, shop.getId());
            int cartHasNum = 0;
            if(sc != null)
            {
                cartHasNum = sc.getQuantity();
            }
            shopMap.put("cartProductQuantity", cartHasNum);

            Response.put("data", shopMap);
        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

    //    改变paystate=0和state=2
    @RequestMapping("/order/cancel")
    public Map<String, Object> OrderCancel(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

        try {OrderForm order = orderFormService.selectByPrimaryKey(orderId);
            order.setState(1);
            order.setPaystate(0);
            int result = orderFormService.updateByPrimaryKey(order);
            if(result != 1)
            {
                Response.replace("code", 4);
                Response.replace("message", "订单生成失败");
                return Response;
            }

//            还需要修改商品信息（暂时不做）

        } catch (Exception e) {
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

    @RequestMapping("/seller/order/list")
    public Map<String, Object> OrderList(HttpServletRequest request,
                                         @RequestParam("payStatus")String payStatus,
                                         @RequestParam("orderStatus")String orderStatus) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Integer payState = (Objects.equals(payStatus, ""))? -1 : Integer.parseInt(payStatus);
        Integer orderState = (Objects.equals(orderStatus, ""))? -1 : Integer.parseInt(orderStatus);

        try {
            int nums = orderFormService.getSellerCountsByState(uid, orderState, payState);
            if(nums == -1)
            {
                Response.replace("code", 4);
                Response.replace("message", "查询出错");
                return Response;
            }
            Map<String, Object> allMap = new HashMap<>();
            if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 5);
                Response.replace("message", "数据不足");
                return Response;
            }
            allMap.put("totalElements", nums);

            List<Map<String, Object>> content = new ArrayList<>();
            List<OrderForm> orders = orderFormService.selectBySellerId(uid, page, size, orderState, payState);

//            OrderForm order = orderFormService.selectByPrimaryKey(orderId);
            for(OrderForm order: orders) {
                Map<String, Object> map = new HashMap<>();
                map.put("orderId", order.getId());
                map.put("buyerUid", order.getUid());
                map.put("buyerName", order.getName());
                map.put("buyerPhone", order.getTel());
                map.put("buyerAddress", order.getAddress());
                map.put("buyerMessage", order.getMessage());
                map.put("sellerUid", order.getSellerid());
                String _createTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getModified());
                map.put("createTime", _createTime);
                map.put("updateTime", _createTime);
                map.put("orderStatus", order.getState());
                map.put("payStatus", order.getPaystate());

                Integer _uid = order.getUid();
                UserInformation _user = userInformationService.selectByPrimaryKey(_uid);
                map.put("buyerNickname", _user.getNickname());
                map.put("buyerUsername", _user.getUsername());
                map.put("buyerRealname", _user.getRealname());
                map.put("buyerTel", _user.getPhone());
                map.put("buyerDormitory", _user.getDormitory());

                List<GoodsOfOrderForm> orderGoods = goodsOfOrderFormService.selectByOFid(order.getId());
                List<Map<String, Object>> orderDetailList = new ArrayList<>();
                BigDecimal orderAmount = new BigDecimal(0);
                for (GoodsOfOrderForm orderGood : orderGoods) {
                    Map<String, Object> _map = new HashMap<>();
                    Integer productId = orderGood.getSid();
                    Integer productQuantity = orderGood.getQuantity();
                    ShopInformation _shop = shopInformationService.selectByPrimaryKey(productId);
                    if (_shop == null) {
                        Response.replace("code", 4);
                        Response.replace("message", "订单生成失败");
                        return Response;
                    }
                    _map.put("productId", _shop.getId());
                    _map.put("productName", _shop.getName());
                    _map.put("productIcon", _shop.getImage());
                    _map.put("productPrice", _shop.getPrice());
                    _map.put("productQuantity", productQuantity);

                    orderAmount = orderAmount.add(new BigDecimal(Integer.parseInt(productQuantity.toString())).multiply(_shop.getPrice()));
                    orderDetailList.add(_map);
                }
                map.put("orderDetailList", orderDetailList);
                map.put("orderAmount", orderAmount);

                content.add(map);
            }
            allMap.put("content", content);

            Response.put("data", allMap);

        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

    @RequestMapping(value = "/order/list_all")
    public Map<String, Object> GetUserInfo(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        if(StringUtils.getInstance().isNullOrEmpty(request.getParameter("state"))
                ||StringUtils.getInstance().isNullOrEmpty(request.getParameter("paystate"))
                ||StringUtils.getInstance().isNullOrEmpty(request.getParameter("page"))
                ||StringUtils.getInstance().isNullOrEmpty(request.getParameter("size")))
        {
            Response.replace("code", 4);
            Response.replace("message", "请求数据为空");
            return Response;
        }
        int state = Integer.parseInt(request.getParameter("state"));
        int paystate = Integer.parseInt(request.getParameter("paystate"));
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));

        try {
            int nums = orderFormService.getSearchCountByStateAndPayState(state, paystate);

            Map<String, Object> map = new HashMap<>();
            if (nums == -1)
            {
                Response.replace("code", 4);
                Response.replace("message", "查询出错了");
                return Response;
            }else if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 5);
                Response.replace("message", "数据不足");
                return Response;
            }
            map.put("totalElements", nums);

            List<OrderForm> result = orderFormService.selectByPaystateAndState(page, size, state, paystate);

            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }

            List<Map<String, Object>> list = new ArrayList<>();
            for(OrderForm order : result)
            {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("orderId", order.getId());
                userMap.put("receiveName", order.getName());
                userMap.put("receiveAddress", order.getAddress());
                userMap.put("receiveTel", order.getTel());
                userMap.put("orderStatus", order.getState());
                userMap.put("orderPayStatus", order.getPaystate());
                String _createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getModified());
                userMap.put("createTime", _createTime);

                List<GoodsOfOrderForm> goods = goodsOfOrderFormService.selectByOFid(order.getId());
                List<Map<String, Object>> goodsList = new ArrayList<>();
                int totalAmount = 0;
                for(GoodsOfOrderForm goodOrderForm: goods){
                    Map<String, Object> _map = new HashMap<>();
                    _map.put("shopId", goodOrderForm.getSid());
                    _map.put("shopQuantity", goodOrderForm.getQuantity());
                    _map.put("shopOrderId", goodOrderForm.getId());

                    ShopInformation _shop = shopInformationService.selectByPrimaryKey(goodOrderForm.getSid());
                    _map.put("shopName", _shop.getName());
                    _map.put("shopPrice", _shop.getPrice());

                    UserInformation _user = userInformationService.selectByPrimaryKey(_shop.getUid());
                    _map.put("sellerId", _user.getId());
                    _map.put("sellerRealname", _user.getRealname());
                    _map.put("sellerNickname", _user.getNickname());
                    _map.put("sellerUsername", _user.getUsername());

                    totalAmount += goodOrderForm.getQuantity() * _shop.getPrice().intValue();

                    goodsList.add(_map);
                }
                userMap.put("totalAmount", totalAmount);
                userMap.put("shops", goodsList);

                list.add(userMap);
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

    @RequestMapping("/order/delete")
    public Map<String, Object> deleteReply(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");

        if(StringUtils.getInstance().isNullOrEmpty(request.getParameter("orderId")))
        {
            Response.replace("code", 4);
            Response.replace("message", "订单ID不能为空");
            return Response;
        }
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));
        try {
            int result = orderFormService.deleteByPrimaryKey(orderId);

            if (result < 1) {
                Response.replace("code", 4);
                Response.replace("message", "删除订单失败");
                return Response;
            }
            List<GoodsOfOrderForm> goodsOfOrderForms = goodsOfOrderFormService.selectByOFid(orderId);
            for(GoodsOfOrderForm goodsOfOrderForm: goodsOfOrderForms){
                int _res = goodsOfOrderFormService.deleteByPrimaryKey(goodsOfOrderForm.getId());
                if (result < 1) {
                    Response.replace("code", 4);
                    Response.replace("message", "删除订单商品失败");
                    return Response;
                }
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
