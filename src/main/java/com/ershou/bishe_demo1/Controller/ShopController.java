package com.ershou.bishe_demo1.Controller;

import com.ershou.bishe_demo1.Pojo.*;
import com.ershou.bishe_demo1.Service.*;
import com.ershou.bishe_demo1.tool.StringUtils;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Resource
    AllKindsService allKindsService;
    @Resource
    ShopInformationService shopInformationService;
    @Resource
    ShopContextService shopContextService;
    @Resource
    CommentReplyService commentReplyService;
    @Resource
    UserInformationService userInformationService;
    @Resource
    WantShopService wantShopService;
    @Resource
    ExchangeInfoService exchangeInfoService;
    @Resource
    GiftInfoSevice giftInfoSevice;

    @RequestMapping("/category/list")
    public Map<String, Object> getKinds(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        List<AllKinds> allKinds = allKindsService.selectAll();
        List<Map<String, String>> categories = new ArrayList<>();
        for (AllKinds kind : allKinds) {
            Map<String, String> map = new HashMap<>();
            map.put("categoryType", kind.getId().toString());
            map.put("categoryName", kind.getName());
            categories.add(map);
        }
        Response.put("data", categories);
        return Response;
    }

    @RequestMapping("/category/check")
    public Map<String, Object> checkKindName(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String kindName = request.getParameter("categoryName");
        if(checkCategoryNameOverride(kindName)){
            Response.put("code", 4);
            Response.put("message", "类型名称已存在无法添加！");
            return Response;
        }
        return Response;
    }

    @RequestMapping("/category/add")
    public Map<String, Object> addKind(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String newKindName = request.getParameter("newCategoryName");
        if(newKindName.equals("其他"))
        {
            Response.replace("code", 4);
            Response.replace("message", "\"其他\"类型禁止删除");
            return Response;
        }

        if(checkCategoryNameOverride(newKindName)){
            Response.put("code", 4);
            Response.put("message", "类型名称已存在无法添加！");
            return Response;
        }

        AllKinds _kind = new AllKinds();
        _kind.setName(newKindName);
        _kind.setModified(new Date());
        int _res = allKindsService.insert(_kind);
        if (_res != 1) {
            Response.replace("code", 4);
            Response.replace("message", "添加类型失败");
            return Response;
        }

        return Response;
    }

    @RequestMapping("/category/save")
    public Map<String, Object> saveKind(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String modifyCategoryName = request.getParameter("modifyCategoryName");
        String categoryType = request.getParameter("categoryType");
        if(checkCategoryNameOverride(modifyCategoryName)){
            Response.put("code", 4);
            Response.put("message", "类型名称已存在无法修改名称！");
            return Response;
        }
        AllKinds _kind = allKindsService.selectByPrimaryKey(Integer.valueOf(categoryType));
        if(_kind.getName().equals("其他"))
        {
            Response.replace("code", 4);
            Response.replace("message", "\"其他\"类型禁止删除");
            return Response;
        }

        _kind.setName(modifyCategoryName);
        _kind.setModified(new Date());
        int _res = allKindsService.updateByPrimaryKey(_kind);
        if (_res != 1) {
            Response.replace("code", 4);
            Response.replace("message", "添加类型失败");
            return Response;
        }

        return Response;
    }

    @RequestMapping("/category/delete")
    public Map<String, Object> deleteKind(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String categoryType = request.getParameter("categoryType");
        Integer kindId = Integer.valueOf(categoryType);

        AllKinds _kind = allKindsService.selectByPrimaryKey(kindId);
        String _categoryName = _kind.getName();
        if(_categoryName.equals("其他"))
        {
            Response.replace("code", 4);
            Response.replace("message", "\"其他\"类型禁止删除");
            return Response;
        }
        int _otherType = getOtherCategoryType();
        if(_otherType == -1){
            Response.replace("code", 4);
            Response.replace("message", "获取其他类型类型号失败");
            return Response;
        }

        int _res = allKindsService.deleteByPrimaryKey(kindId);
        if (_res != 1) {
            Response.replace("code", 4);
            Response.replace("message", "删除类型失败");
            return Response;
        } else {
//            将 shops, want_shops, gift, exchanges中所有类型为categoryType的都改为其他的categoryType
            List<ShopInformation> _shops = shopInformationService.selectByKind(kindId);
            List<GiftInfo> _gifts = giftInfoSevice.selectByKind(kindId);
            List<ExchangeInfo> _exchanges = exchangeInfoService.selectByKind(kindId);
            List<WantShop> _wants = wantShopService.selectByKind(kindId);
            for(ShopInformation _shop: _shops){
                _shop.setKind(_otherType);
                int res = shopInformationService.updateByPrimaryKeySelective(_shop);
                if(res != 1) {
                    Response.replace("code", 4);
                    Response.replace("message", "更新类型失败");
                    return Response;
                }
            }
            for(GiftInfo _gift: _gifts){
                _gift.setKind(_otherType);
                int res = giftInfoSevice.updateByPrimaryKeySelective(_gift);
                if(res != 1) {
                    Response.replace("code", 4);
                    Response.replace("message", "更新类型失败");
                    return Response;
                }
            }
            for(ExchangeInfo _exchange: _exchanges){
                _exchange.setKind(_otherType);
                int res = exchangeInfoService.updateByPrimaryKeySelective(_exchange);
                if(res != 1) {
                    Response.replace("code", 4);
                    Response.replace("message", "更新类型失败");
                    return Response;
                }
            }
            for(WantShop _wshop: _wants){
                _wshop.setKind(_otherType);
                int res = wantShopService.updateByPrimaryKeySelective(_wshop);
                if(res != 1) {
                    Response.replace("code", 4);
                    Response.replace("message", "更新类型失败");
                    return Response;
                }
            }

        }
        Map<String, Object> data = new HashMap<>();
        data.put("categoryName", _categoryName);
        Response.put("data", data);
        return Response;
    }

    int getOtherCategoryType(){
        List<AllKinds> allKinds = allKindsService.selectAll();
        for (AllKinds kind : allKinds) {
            if(kind.getName().equals("其他")){
                return kind.getId();
            }
        }
        return -1;
    }

    boolean checkCategoryNameOverride(String modifyCategoryName){
        List<AllKinds> allKinds = allKindsService.selectAll();
        for (AllKinds kind : allKinds) {
            if(kind.getName().equals(modifyCategoryName)){
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/comment/list")
    public Map<String, Object> getAllComments(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
//        String searchName = request.getParameter("searchName");
        int commentType = Integer.parseInt(request.getParameter("commentType"));
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));

        int nums = shopContextService.getCountsByType(commentType);
        if (nums < (page-1)*size + 1)
        {
            Response.replace("code", 5);
            Response.replace("message", "数据不足");
            return Response;
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("totalElements", nums);

        List<ShopContext> comments = shopContextService.selectByTypePage(commentType, page, size);
        if(comments == null) {
            Response.put("date", new ArrayList<>());
            return Response;
        }

        List<Map<String, Object>> commentAndReplys = new ArrayList<>();
        for (ShopContext comment : comments) {
            Map<String, Object> map = new HashMap<>();
            map.put("productId", comment.getSid());
            map.put("commentId", comment.getId());
            map.put("commentUid", comment.getUid());
            map.put("commentType", comment.getType());
            map.put("commentContent", comment.getContent());
            String _commentTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(comment.getTime());
            map.put("commentTime", _commentTime);

            int commentUid = comment.getUid();
            UserInformation _user = userInformationService.selectByPrimaryKey(commentUid);
            map.put("commentUserName", _user.getUsername());
            map.put("commentUserNickname", _user.getNickname());
            map.put("commentUserId", _user.getId());
            map.put("isReply", false);

            ShopInformation shop = shopInformationService.selectByPrimaryKey(comment.getSid());
            Integer replyUid = shop.getUid();
            List<CommentReply> replys = commentReplyService.selectByCommentId(comment.getId());
            if (replys != null) {
                List<Map<String, Object>> _replysList = new ArrayList<>();
                for (CommentReply _reply : replys) {
                    Map<String, Object> _map = new HashMap<>();
                    _map.put("commentContent", _reply.getContent());
                    String _date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(_reply.getTime());
                    _map.put("commentTime", _date);
                    _map.put("productId", comment.getSid());
                    _map.put("commentId", "回复");
                    _map.put("replyId", _reply.getId());
                    _map.put("commentUid", replyUid);
                    _map.put("isReply", true);
                    _replysList.add(_map);
                }
                map.put("replys", _replysList);
            }
            commentAndReplys.add(map);
        }

        dataMap.put("content", commentAndReplys);

        Response.put("data", dataMap);
        return Response;
    }

    @RequestMapping("/comment/id")
    public Map<String, Object> getComments(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        int productId = Integer.parseInt(request.getParameter("productId"));
        int commentType = Integer.parseInt(request.getParameter("commentType"));
        List<ShopContext> comments = shopContextService.selectBySid(productId, commentType);
        if(comments == null) {
            Response.put("date", new ArrayList<>());
            return Response;
        }

        List<Map<String, Object>> commentAndReplys = new ArrayList<>();
        for (ShopContext comment : comments) {
            Map<String, Object> map = new HashMap<>();
            map.put("commentId", comment.getId());
            map.put("commentContent", comment.getContent());
            String _commentTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(comment.getTime());
            map.put("commentTime", _commentTime);

            int commentUid = comment.getUid();
            UserInformation _user = userInformationService.selectByPrimaryKey(commentUid);
            map.put("commentUserName", _user.getUsername());
            map.put("commentUserNickname", _user.getNickname());
            map.put("commentUserId", _user.getId());

            List<CommentReply> replys = commentReplyService.selectByCommentId(comment.getId());
            if (replys != null) {
                List<Map<String, Object>> _replysList = new ArrayList<>();
                for (CommentReply _reply : replys) {
                    Map<String, Object> _map = new HashMap<>();
                    _map.put("content", _reply.getContent());
                    String _date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(_reply.getTime());
                    _map.put("replyTime", _date);
                    _replysList.add(_map);
                }
                map.put("replys", _replysList);
            }
            commentAndReplys.add(map);
        }
        Response.put("data", commentAndReplys);
        return Response;
    }

    @RequestMapping("/to_comment")
    public Map<String, Object> requestExchange(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        boolean isInsert = false;

        if(StringUtils.getInstance().isNullOrEmpty(request.getParameter("commentContent"))
                ||StringUtils.getInstance().isNullOrEmpty(request.getParameter("commentType"))
                ||StringUtils.getInstance().isNullOrEmpty(request.getParameter("uid")))
        {
            Response.replace("code", 4);
            Response.replace("message", "请求数据为空");
            return Response;
        }
        String content = request.getParameter("commentContent");
        String commentType = request.getParameter("commentType");
        String commentUid = request.getParameter("uid");
        String productId = request.getParameter("productId");


        ShopContext newComment = new ShopContext();
        newComment.setContent(content);
        newComment.setType(Integer.valueOf(commentType));
        newComment.setUid(Integer.valueOf(commentUid));
        newComment.setSid(Integer.valueOf(productId));
        newComment.setTime(new Date());

        try {
            int result = 0;
            result = shopContextService.insert(newComment);

            if (result != 1) {
                Response.replace("code", 4);
                Response.replace("message", "评论失败");
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

    @RequestMapping("/comment/to_reply")
    public Map<String, Object> commentReply(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        boolean isInsert = false;

        if(StringUtils.getInstance().isNullOrEmpty(request.getParameter("replyContent"))
                ||StringUtils.getInstance().isNullOrEmpty(request.getParameter("commentId"))
                ||StringUtils.getInstance().isNullOrEmpty(request.getParameter("uid")))
        {
            Response.replace("code", 4);
            Response.replace("message", "请求数据为空");
            return Response;
        }
        String content = request.getParameter("replyContent");
        String commentId = request.getParameter("commentId");
        String replyUid = request.getParameter("uid");


        CommentReply newReply = new CommentReply();
        newReply.setContent(content);
        newReply.setCommentid(Integer.valueOf(commentId));
        newReply.setReplyuid(Integer.valueOf(replyUid));
        newReply.setTime(new Date());

        try {
            int result = 0;
            result = commentReplyService.insert(newReply);

            if (result != 1) {
                Response.replace("code", 4);
                Response.replace("message", "回复");
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

//    留言删除，同时删除所有回复。
    @RequestMapping("/comment/delete")
    public Map<String, Object> deleteComment(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");

        if(StringUtils.getInstance().isNullOrEmpty(request.getParameter("commentId")))
        {
            Response.replace("code", 4);
            Response.replace("message", "请求数据为空");
            return Response;
        }
        Integer commentId = Integer.valueOf(request.getParameter("commentId"));


        try {
//            先删除评论，再删除留言的回复
            int _delComment = shopContextService.deleteByPrimaryKey(commentId);
            if (_delComment < 1) {
                Response.replace("code", 4);
                Response.replace("message", "删除留言失败");
                return Response;
            }

            int result = commentReplyService.deleteByCommentId(commentId);

            if (result < 0) {
                Response.replace("code", 4);
                Response.replace("message", "删除回复失败");
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

    @RequestMapping("/reply/delete")
    public Map<String, Object> deleteReply(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");

        if(StringUtils.getInstance().isNullOrEmpty(request.getParameter("replyId")))
        {
            Response.replace("code", 4);
            Response.replace("message", "请求数据为空");
            return Response;
        }
        Integer replyId = Integer.valueOf(request.getParameter("replyId"));
        try {
            int result = commentReplyService.deleteByPrimaryKey(replyId);

            if (result < 1) {
                Response.replace("code", 4);
                Response.replace("message", "删除回复失败");
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

    @RequestMapping("/addshopimg")
    public Map<String, Object> upload(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        try {
            MultipartFile image = request.getFile("file");
            String  fileName = image.getOriginalFilename();

            String imgName = UUID.randomUUID() + "-" + fileName.replaceAll(" ", "");
            //保存图片
            String imgFilePath = "E:\\vueprojs\\deal-vue-master\\src\\save_images\\shops\\" + imgName;
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(image.getBytes());
            out.flush();
            out.close();
            Response.put("imageName", imgName);
            return Response;
        } catch (Exception ioE)
        {
            Response.replace("code", 4);
            Response.replace("message", "上传图片失败");
            ioE.printStackTrace();
            return Response;
        }
    }

//    查询的是所有上架的商品
    @RequestMapping("/product/search")
    public Map<String, Object> ListUserProductByPagesize(HttpServletRequest request,
                                                         @RequestParam("productName") String productName,
                                                         @RequestParam("productStatus") String productStatus,
                                                         @RequestParam("productSecondhand") String level,
                                                         @RequestParam("categoryType") String categoryType) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Integer searchState = (Objects.equals(productStatus, "")) ? -1 : Integer.parseInt(productStatus);;
        String searchName = (productName == null) ? "":productName;
        Integer searchLevel = (Objects.equals(level, "")) ? -1 : Integer.parseInt(level);
        Integer searchCategoryType = (Objects.equals(categoryType, "")) ? -1 : Integer.parseInt(categoryType);

        try {
            int nums = shopInformationService.getSearchCounts(searchState, searchName, searchLevel, searchCategoryType);
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
            List<ShopInformation> result = shopInformationService.SearchByPageSize(page, size, searchState, searchName, searchLevel, searchCategoryType);

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

    //    随便看看
    @RequestMapping("/product/random")
    public Map<String, Object> ListProductByRandom(HttpServletRequest request,
                                                         @RequestParam("productName") String productName,
                                                         @RequestParam("productStatus") String productStatus,
                                                         @RequestParam("productSecondhand") String level,
                                                         @RequestParam("categoryType") String categoryType) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        int number = Integer.parseInt(request.getParameter("number"));
        Integer searchState = (Objects.equals(productStatus, "")) ? -1 : Integer.parseInt(productStatus);;
        String searchName = (productName == null) ? "":productName;
        Integer searchLevel = (Objects.equals(level, "")) ? -1 : Integer.parseInt(level);
        Integer searchCategoryType = (Objects.equals(categoryType, "")) ? -1 : Integer.parseInt(categoryType);

        try {
            int nums = shopInformationService.getSearchCounts(searchState, searchName, searchLevel, searchCategoryType);
            List<Integer> ids = shopInformationService.getAllIds(searchState, searchName, searchLevel, searchCategoryType);
            Vector<Integer> indexs;
            if (nums > number)
            {
                indexs = getRandomNums(nums, number);
            } else {
                indexs = getRandomNums(nums, nums);
            }
            List<ShopInformation> result = new ArrayList<>();
            for(Integer index: indexs)
            {
                int _shopId = ids.get(index);
                ShopInformation _shop = shopInformationService.selectByPrimaryKey(_shopId);
                result.add(_shop);
            }


            if (result.size() < 1)
            {
                Response.replace("code", 5);
                Response.replace("message", "未查到数据");
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

    Vector<Integer>  getRandomNums(int total, int num) {
        Random r = new Random();

        Vector<Integer> v = new Vector<Integer>();

        int count = 0;

        while(count < num) {
            int number = r.nextInt(total);
            if(!v.contains(number)){
                v.add(number);
                count ++;
            }
        }

        return v;
    }
}
