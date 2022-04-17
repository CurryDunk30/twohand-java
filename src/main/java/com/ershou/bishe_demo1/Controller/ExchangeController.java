package com.ershou.bishe_demo1.Controller;

import com.ershou.bishe_demo1.Pojo.ExchangeInfo;
import com.ershou.bishe_demo1.Pojo.ShopInformation;
import com.ershou.bishe_demo1.Pojo.UserInformation;
import com.ershou.bishe_demo1.Service.AllKindsService;
import com.ershou.bishe_demo1.Service.ExchangeInfoService;
import com.ershou.bishe_demo1.Service.ShopInformationService;
import com.ershou.bishe_demo1.Service.UserInformationService;
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
@RequestMapping("/exchange")
public class ExchangeController {
    @Resource
    ExchangeInfoService exchangeInfoService;
    @Resource
    UserInformationService userInformationService;
    @Resource
    ShopInformationService shopInformationService;
    @Resource
    AllKindsService allKindsService;

    @RequestMapping("/request_ex")
    public Map<String, Object> requestExchange(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        boolean isInsert = false;
        String exchangeId = request.getParameter("exchangeId");
        if(StringUtils.getInstance().isNullOrEmpty(request.getParameter("exchangeId")))
        {
            isInsert = true;
        }
//        String sellerUsername = request.getParameter("sellerUsername");
        String sellerSid = request.getParameter("sellerSid");
        String sellerId = request.getParameter("sellerId");
        String productQuantity = request.getParameter("productQuantity");
        String exchangeName = request.getParameter("exchangeName");
        String exchangeQuantity = request.getParameter("exchangeQuantity");
        String exchangeLevel = request.getParameter("exchangeLevel");
        String exchangeRemark = request.getParameter("exchangeRemark");
        String categoryType = request.getParameter("categoryType");
        String exchangeImageUrl = request.getParameter("exchangeImageUrl");
        String exchangeUid = request.getParameter("exchangeUid");
        ExchangeInfo exchangeInfo = new ExchangeInfo();
        exchangeInfo.setExchangeUid(Integer.valueOf(exchangeUid));
        exchangeInfo.setShopQuantity(Integer.valueOf(productQuantity));
        exchangeInfo.setCreateTime(new Date());
        exchangeInfo.setLevel(Integer.valueOf(exchangeLevel));
        exchangeInfo.setName(exchangeName);
        exchangeInfo.setQuantity(Integer.valueOf(exchangeQuantity));
        exchangeInfo.setRemark(exchangeRemark);
        exchangeInfo.setSellerSid(Integer.valueOf(sellerSid));
        exchangeInfo.setSellerUid(Integer.valueOf(sellerId));
        exchangeInfo.setKind(Integer.valueOf(categoryType));
        exchangeInfo.setImage(exchangeImageUrl);
        exchangeInfo.setState(0);

        try {


            int result = 0;
            if (isInsert){
                result = exchangeInfoService.insert(exchangeInfo);
            } else {
                Integer exId;
                if(exchangeInfoService.selectByPrimaryKey(Integer.valueOf(exchangeId)) != null)
                {
                    exchangeInfo.setId(Integer.valueOf(exchangeId));
                } else {
                    Response.replace("code", 4);
                    Response.replace("message", "确认交换号");
                    return Response;
                }
                result = exchangeInfoService.updateByPrimaryKeySelective(exchangeInfo);
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

    @RequestMapping("/record/list")
    public Map<String, Object> ListProductByPagesize(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer exchangeUid = Integer.valueOf(request.getParameter("uid"));
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer size = Integer.valueOf(request.getParameter("size"));
//        查看所有
        Integer state = -1;

        try {
            int nums = exchangeInfoService.getCountsByExchange(exchangeUid);
            Map<String, Object> map = new HashMap<>();
            if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 5);
                Response.replace("message", "数据不足");
                return Response;
            }
            map.put("totalElements", nums);
            List<ExchangeInfo> result = exchangeInfoService.selectByExchange(exchangeUid,state, page, size);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for(ExchangeInfo exchange : result)
            {
                Map<String, Object> shopMap = new HashMap<>();

                shopMap.put("exchangeName", exchange.getName());
                shopMap.put("exchangeId", exchange.getId());
                shopMap.put("exchangeLevel", exchange.getLevel());
                String _cateName = allKindsService.selectByPrimaryKey(exchange.getKind()).getName();
                shopMap.put("exchangeCategoryType", exchange.getKind());
                shopMap.put("exchangeCategoryName", _cateName);

                shopMap.put("exchangeQuantity", exchange.getQuantity());
                shopMap.put("productQuantity", exchange.getShopQuantity());
                shopMap.put("exchangeState", exchange.getState());
                String _date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exchange.getCreateTime());
                shopMap.put("createTime", _date);

                Integer _sellerId = exchange.getSellerUid();
                shopMap.put("sellerId", _sellerId);
                UserInformation user = userInformationService.selectByPrimaryKey(_sellerId);
                shopMap.put("sellerName", user.getRealname());
                shopMap.put("sellerUsername", user.getUsername());
                shopMap.put("sellerTel", user.getPhone());

                Integer _productId = exchange.getSellerSid();
                ShopInformation shop = shopInformationService.selectByPrimaryKey(_productId);
                shopMap.put("productId", _productId);
                shopMap.put("productName", shop.getName());
                shopMap.put("productLevel", shop.getLevel());
                shopMap.put("productRemark", shop.getRemark());

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

    @RequestMapping("/request/list")
    public Map<String, Object> ListExchangeRequest(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer sellerUid = Integer.valueOf(request.getParameter("uid"));
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer size = Integer.valueOf(request.getParameter("size"));
//        查看所有
        Integer state = -1;

        try {
            int nums = exchangeInfoService.getCountsBySeller(sellerUid);
            Map<String, Object> map = new HashMap<>();
            if (nums < (page-1)*size + 1)
            {
                Response.replace("code", 5);
                Response.replace("message", "数据不足");
                return Response;
            }
            map.put("totalElements", nums);
            List<ExchangeInfo> result = exchangeInfoService.selectBySeller(sellerUid,state, page, size);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }
            List<Map<String, Object>> list = new ArrayList<>();
            for(ExchangeInfo exchange : result)
            {
                Map<String, Object> shopMap = new HashMap<>();

                shopMap.put("exchangeName", exchange.getName());
                shopMap.put("exchangeId", exchange.getId());
                shopMap.put("exchangeLevel", exchange.getLevel());
                String _cateName = allKindsService.selectByPrimaryKey(exchange.getKind()).getName();
                shopMap.put("exchangeCategoryType", exchange.getKind());
                shopMap.put("exchangeCategoryName", _cateName);

                shopMap.put("exchangeQuantity", exchange.getQuantity());
                shopMap.put("productQuantity", exchange.getShopQuantity());
                shopMap.put("exchangeState", exchange.getState());
                String _date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(exchange.getCreateTime());
                shopMap.put("createTime", _date);

                Integer _exchangeUid = exchange.getExchangeUid();
                shopMap.put("exchangeUserId", _exchangeUid);
                UserInformation user = userInformationService.selectByPrimaryKey(_exchangeUid);
                shopMap.put("exchangeUserName", user.getRealname());
                shopMap.put("exchangeUserUsername", user.getUsername());
                shopMap.put("exchangeUserTel", user.getPhone());

                Integer _productId = exchange.getSellerSid();
                ShopInformation shop = shopInformationService.selectByPrimaryKey(_productId);
                shopMap.put("productId", _productId);
                shopMap.put("productName", shop.getName());
                shopMap.put("productLevel", shop.getLevel());
                shopMap.put("productRemark", shop.getRemark());

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

    @RequestMapping("/request/accept")
    public Map<String, Object> ExchangeRequestAccept(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer sellerUid = Integer.valueOf(request.getParameter("uid"));
        Integer exchangeId = Integer.valueOf(request.getParameter("exchangeId"));

        try {
            ExchangeInfo result = exchangeInfoService.selectByPrimaryKey(exchangeId);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "未查到交换数据");
                return Response;
            }
            if (!result.getSellerUid().equals(sellerUid))
            {
                Response.replace("code", 4);
                Response.replace("message", "您不可操作性，请检查账号");
                return Response;
            }
            Map<String, Object> shopMap = new HashMap<>();
            Integer sid = result.getSellerSid();
            ShopInformation _shop = shopInformationService.selectByPrimaryKey(sid);
            int quantity = _shop.getQuantity();
            int shopQuantity = result.getShopQuantity();
            if(quantity - shopQuantity < 0){
                Response.replace("code", 4);
                Response.replace("message", "商品数量不足，不满足交换条件");
                return Response;
            } else {
                result.setState(1);
                int _res = exchangeInfoService.updateByPrimaryKeySelective(result);
                if(_res == -1)
                {
                    Response.replace("code", 4);
                    Response.replace("message", "交换失败请重新交换");
                    return Response;
                }

                _shop.setQuantity(quantity - shopQuantity);
                 _res = shopInformationService.updateByPrimaryKeySelective(_shop);
                if(_res == -1)
                {
                    Response.replace("code", 4);
                    Response.replace("message", "交换失败请重新交换");
                    return Response;
                }
            }

            Response.put("data", shopMap);
        } catch (Exception e){
            e.printStackTrace();
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

    @RequestMapping("/request/cancel")
    public Map<String, Object> ExchangeRequestCancel(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer sellerUid = Integer.valueOf(request.getParameter("uid"));
        Integer exchangeId = Integer.valueOf(request.getParameter("exchangeId"));

        try {
            ExchangeInfo result = exchangeInfoService.selectByPrimaryKey(exchangeId);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "未查到交换数据");
                return Response;
            }
            if (!result.getSellerUid().equals(sellerUid))
            {
                Response.replace("code", 4);
                Response.replace("message", "您不可操作性，请检查账号");
                return Response;
            }
            result.setState(2);
            int _res = exchangeInfoService.updateByPrimaryKeySelective(result);
            if(_res == -1)
            {
                Response.replace("code", 4);
                Response.replace("message", "拒绝交换失败");
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
    @RequestMapping("/request/delete")
    public Map<String, Object> ExchangeRequestDelete(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer sellerUid = Integer.valueOf(request.getParameter("uid"));
        Integer exchangeId = Integer.valueOf(request.getParameter("exchangeId"));

        try {
            ExchangeInfo result = exchangeInfoService.selectByPrimaryKey(exchangeId);
            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "未查到交换数据");
                return Response;
            }
            if (!result.getSellerUid().equals(sellerUid))
            {
                Response.replace("code", 4);
                Response.replace("message", "您不可操作性，请检查账号");
                return Response;
            }
            int _state = result.getState();
//            交换成功的暂时不支持删除，需要保留记录
            if(_state == 1)
            {
                Response.replace("code", 4);
                Response.replace("message", "已同意交换不支持删除");
                return Response;
            }
            int _res = exchangeInfoService.deleteByPrimaryKey(exchangeId);
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
