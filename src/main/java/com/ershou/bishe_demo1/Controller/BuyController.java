package com.ershou.bishe_demo1.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ershou.bishe_demo1.Pojo.*;
import com.ershou.bishe_demo1.Service.*;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/buy")
public class BuyController {
    @Resource
    AllKindsService allKindsService;
    @Resource
    ShopInformationService shopInformationService;
    @Resource
    UserInformationService userInformationService;
    @Resource
    ShopCarService shopCarService;
    @Resource
    OrderFormService orderFormService;
    @Resource
    GoodsOfOrderFormService goodsOfOrderFormService;


    @RequestMapping("/cart/delete")
    public Map<String, Object> deleteCartProduct(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer shopId = Integer.valueOf(request.getParameter("productId"));
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        try {
            int result = shopCarService.deleteByUidSid(uid, shopId);
            if (result == -1 || result == 0) {
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

    @RequestMapping("/cart/list")
    public Map<String, Object> ListCartByPagesize(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer size = Integer.valueOf(request.getParameter("size"));
//        Integer state = Integer.valueOf(request.getParameter("productStatus"));


        try {
//            先获取所有购物车商品ID，然后查出商品具体信息传出去
            int nums = shopCarService.getUserBuyCounts(uid);
            if(nums == -1)
            {
                Response.replace("code", 4);
                Response.replace("message", "查询出错");
                return Response;
            }
            Map<String, Object> map = new HashMap<>();
            if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 5);
                Response.replace("message", "数据不足");
                return Response;
            }
            map.put("totalElements", nums);

            List<ShopCar> result = shopCarService.selectByUid(uid, page, size);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for(ShopCar shopCar : result)
            {
                int shopid = shopCar.getSid();
                int quantity = shopCar.getQuantity();
                ShopInformation shop = shopInformationService.selectByPrimaryKey(shopid);
                UserInformation user = userInformationService.selectByPrimaryKey(uid);
                Map<String, Object> shopMap = new HashMap<>();
                shopMap.put("productStatus", shop.getDisplay());
                shopMap.put("productName", shop.getName());
                shopMap.put("productId", shop.getId());
                shopMap.put("productIcon", shop.getImage());
                shopMap.put("productSecondhand", shop.getLevel());
                shopMap.put("productPrice", shop.getPrice());
                shopMap.put("productStock", shop.getQuantity());
                shopMap.put("productDescription", shop.getRemark());
                shopMap.put("productQuantity", quantity);
                shopMap.put("categoryType", shop.getKind());
                shopMap.put("sellerId", user.getId());
                shopMap.put("sellerName", user.getNickname());
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

    @RequestMapping(value = "/cart/update", method = RequestMethod.POST)
    public Map<String, Object> updateCartProductQ(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        Integer productId = Integer.valueOf(request.getParameter("productId"));
        Integer quantity = Integer.valueOf(request.getParameter("productQuantity"));
        try {
//            ShopInformation shop = shopInformationService.selectByPrimaryKey(productId);
            ShopCar shopCar = shopCarService.selectByUidSid(uid, productId);
            shopCar.setQuantity(quantity);
            int result = shopCarService.updateByPrimaryKeySelective(shopCar);
            if (result == -1 || result == 0) {
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

    @RequestMapping("/cart/add")
    public Map<String, Object> addProductToCart(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        Integer productId = Integer.valueOf(request.getParameter("productId"));
        Integer quantity = Integer.valueOf(request.getParameter("productQuantity"));
        try {
//            ShopInformation shop = shopInformationService.selectByPrimaryKey(productId);
            ShopCar shopCar = new ShopCar();
            shopCar.setUid(uid);
            shopCar.setSid(productId);
//            shopCar.setDisplay(shop.getDisplay());
            ShopCar shopCarRecord = shopCarService.selectByUidSid(uid, productId);
//            如果已经存在了记录则直接相加
            int result = 0;
            if (shopCarRecord != null) {
                shopCar.setQuantity(shopCarRecord.getQuantity() + quantity);
                shopCar.setId(shopCarRecord.getId());
                result = shopCarService.updateByPrimaryKeySelective(shopCar);
            }
            else{
                shopCar.setQuantity(quantity);
                result = shopCarService.insert(shopCar);
            }

            if (result == -1 || result == 0) {
                Response.replace("code", 4);
                Response.replace("message", "加入购物车失败");
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

    @RequestMapping("/order/create")
    public Map<String, Object> CreateOrder(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        String orderName = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String message = request.getParameter("message");
        Integer sellerUid = Integer.valueOf(request.getParameter("sellerUid"));
        JSONArray Jsons = JSONArray.parseArray(request.getParameter("items"));

//        List<String> mapList = Jons.toJavaList(String.class);

//        记得减少数量
        try {
            OrderForm orderRecord = new OrderForm();
            orderRecord.setAddress(address);
//            订单状态为订单开始
            orderRecord.setState(0);
            orderRecord.setPaystate(0);
            orderRecord.setUid(uid);
            orderRecord.setSellerid(sellerUid);
            orderRecord.setMessage(message);
            orderRecord.setTel(phone);
            orderRecord.setName(orderName);
            orderRecord.setModified(new Date());
            int result = orderFormService.insert(orderRecord);
            if (result == -1 || result == 0) {
                Response.replace("code", 4);
                Response.replace("message", "订单生成失败");
                return Response;
            }
            OrderForm newOrder = orderFormService.selectUserNew(uid);

            for (int i=0; i<Jsons.size(); i++){
                JSONObject _json = Jsons.getJSONObject(i);
                Integer productId = (Integer) _json.get("productId");
                Integer productQuantity = (Integer) _json.get("productQuantity");
                GoodsOfOrderForm gorder = new GoodsOfOrderForm();
                gorder.setQuantity(productQuantity);
                gorder.setSid(productId);
                gorder.setOrderid(newOrder.getId());
                int _result = goodsOfOrderFormService.insert(gorder);
                if(_result != 1)
                {
                    Response.replace("code", 4);
                    Response.replace("message", "订单生成失败");
                    return Response;
                }
            }
            Map<String, Object> _map = new HashMap<>();
            _map.put("orderId", newOrder.getId());
            Response.put("data", _map);

        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

    @RequestMapping("/order/detail")
    public Map<String, Object> OrderDetail(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

//        记得减少数量
        try {
            Map<String, Object> map = new HashMap<>();
            OrderForm order = orderFormService.selectByPrimaryKey(orderId);
            map.put("orderId", order.getId());
            map.put("buyerUid", order.getUid());
            map.put("buyerName", order.getName());
            map.put("buyerPhone", order.getTel());
            map.put("buyerAddress", order.getAddress());
            map.put("buyerMessage", order.getMessage());
            map.put("sellerUid", order.getSellerid());
            map.put("createTime", order.getModified());
            map.put("updateTime", order.getModified());
            map.put("orderStatus", order.getState());
            map.put("payStatus", order.getPaystate());

            Integer _uid = order.getSellerid();
            UserInformation _user = userInformationService.selectByPrimaryKey(_uid);
            map.put("sellerName", _user.getNickname());
            map.put("sellerRealname", _user.getRealname());
            map.put("sellerPhone", _user.getPhone());
            map.put("sellerDormitory", _user.getDormitory());

            List<GoodsOfOrderForm> orderGoods = goodsOfOrderFormService.selectByOFid(orderId);
            List<Map<String, Object>> orderDetailList = new ArrayList<>();
            BigDecimal orderAmount = new BigDecimal(0);
            for(GoodsOfOrderForm orderGood:orderGoods){
                Map<String, Object> _map = new HashMap<>();
                Integer productId = orderGood.getSid();
                Integer productQuantity = orderGood.getQuantity();
                ShopInformation _shop = shopInformationService.selectByPrimaryKey(productId);
                if(_shop == null)
                {
                    Response.replace("code", 4);
                    Response.replace("message", "订单生成失败");
                    return Response;
                }
                _map.put("orderId", order.getId());
                _map.put("productId", _shop.getId());
                _map.put("productName", _shop.getName());
                _map.put("productPrice", _shop.getPrice());
                _map.put("shopQuantity", productQuantity);

                orderAmount = orderAmount.add(new BigDecimal(Integer.parseInt(productQuantity.toString())).multiply(_shop.getPrice()));
                orderDetailList.add(_map);
            }
            map.put("orderDetailList", orderDetailList);
            map.put("orderAmount", orderAmount);

            Response.put("data", map);

        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

//    paystate=1 state=1
    @RequestMapping("/order/pay")
    public Map<String, Object> OrderPay(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer orderId = Integer.valueOf(request.getParameter("orderId"));

        try {
            OrderForm order = orderFormService.selectByPrimaryKey(orderId);
            order.setState(1);
            order.setPaystate(1);
            int result = orderFormService.updateByPrimaryKey(order);
            if(result != 1)
            {
                Response.replace("code", 4);
                Response.replace("message", "订单生成失败");
                return Response;
            }

//            还需要修改商品信息（暂时不做）
            List<GoodsOfOrderForm> goodsOfOrderForms = goodsOfOrderFormService.selectByOFid(orderId);
            for(GoodsOfOrderForm go: goodsOfOrderForms) {
                int sid = go.getSid();
                int sc = shopCarService.deleteByUidSid(order.getUid(), sid);
                if(sc != 1)
                {
                    Response.replace("code", 4);
                    Response.replace("message", "删除购物车失败");
                    return Response;
                }

                ShopInformation _shop = shopInformationService.selectByPrimaryKey(sid);
                _shop.setSales(_shop.getSales() + go.getQuantity());
                _shop.setQuantity(_shop.getQuantity() - go.getQuantity());

                int _res = shopInformationService.updateByPrimaryKeySelective(_shop);
                if(_res != 1){
                    Response.replace("code", 4);
                    Response.replace("message", "更新商品数据失败");
                    return Response;
                }
            }

        } catch (Exception e) {
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

    @RequestMapping("/order/list")
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
            int nums = orderFormService.getCountsByState(uid, orderState, payState);
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
            List<OrderForm> orders = orderFormService.selectByUid(uid, page, size, orderState, payState);

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

                Integer _uid = order.getSellerid();
                UserInformation _user = userInformationService.selectByPrimaryKey(_uid);
                map.put("sellerName", _user.getNickname());
                map.put("sellerRealname", _user.getRealname());
                map.put("sellerPhone", _user.getPhone());
                map.put("sellerDormitory", _user.getDormitory());

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
}
