package com.ershou.bishe_demo1.Controller;

import com.ershou.bishe_demo1.Mapper.UserPasswordMapper;
import com.ershou.bishe_demo1.Pojo.ShopInformation;
import com.ershou.bishe_demo1.Pojo.UserAddress;
import com.ershou.bishe_demo1.Pojo.UserInformation;
import com.ershou.bishe_demo1.Pojo.UserPassword;
import com.ershou.bishe_demo1.Service.UserInformationService;
import com.ershou.bishe_demo1.Service.UserPasswordService;
import com.ershou.bishe_demo1.Service.UserService;
import com.ershou.bishe_demo1.token.TokenProccessor;
import com.ershou.bishe_demo1.tool.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserInformationService userInformationService;

    @Resource
    private UserService userService;

    @Resource
    private UserPasswordService userPasswordService;

    //验证登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(HttpServletRequest request,
                                     @RequestBody Map<String, Object> form) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String username = form.get("username").toString();
        String password = form.get("password").toString();
        String role = form.get("role").toString();
        Integer roleInt = Integer.valueOf(role);
        if (StringUtils.getInstance().isNullOrEmpty(username) || StringUtils.getInstance().isNullOrEmpty(password)) {
            Response.replace("code", 4);
            Response.replace("message", "用户名为空");
            return Response;
        }

        boolean b = userService.CheckUserPasswd(username, password);
        //失败，不存在该手机号码
        if (!b) {
            Response.replace("code", 4);
            Response.replace("message", "登录失败，请检查账号密码");
            return Response;
        }
        UserInformation user = userInformationService.selectByUsername(username);
        if(!roleInt.equals(user.getRole()))
        {
            Response.replace("code", 4);
            Response.replace("message", "用户身份不匹配");
        }
        UserInformation userinfo = userInformationService.selectByUsername(username);
        Map<String, Object> data = new HashMap<>();
//        生成TOKEN
        String token = TokenProccessor.getInstance().makeToken();
        data.put("token", token);
        data.put("nickname", userinfo.getNickname());
        data.put("uid", userinfo.getId());
        data.put("username", userinfo.getUsername());
        data.put("realname", userinfo.getRealname());
        data.put("headIcon", userinfo.getAvatar());
        data.put("role", userinfo.getRole());
        Response.put("data", data);
        return Response;
    }

    // 退出
    @RequestMapping(value = "/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        return Response;
    }


    // 注册（用户信息，用户密码，用户账户信息表）
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map<String, Object> SignUp(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String username = request.getParameter("username").toString();
        String nickname = request.getParameter("nickname").toString();
        String password = request.getParameter("password").toString();
        String realname = request.getParameter("realname").toString();
        String gender = request.getParameter("gender").toString();
        String sno = request.getParameter("sno").toString();
        String phone = request.getParameter("phone").toString();
        String email = request.getParameter("email").toString();
        String dormitory = request.getParameter("dormitory").toString();
        String avatarname = request.getParameter("avatarname").toString();
        String role = request.getParameter("role").toString();
        UserInformation user = new UserInformation();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setRealname(realname);
        user.setGender(gender);
        user.setSno(sno);
        user.setPhone(phone);
        user.setEmail(email);
        user.setDormitory(dormitory);
        user.setAvatar(avatarname);
        user.setRole(Integer.valueOf(role));
        user.setCreatetime(new Date());
        try {
            int result = userInformationService.insert(user);
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
        Integer uid = userInformationService.selectIdByUsername(username);
        boolean b = userService.SignUp(uid, password);
        if (!b){
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        return Response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Map<String, Object> UpdateUserInfo(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String realname = request.getParameter("realname");
        String gender = request.getParameter("gender");
        String sno = request.getParameter("sno");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String dormitory = request.getParameter("dormitory");
        String avatarname = request.getParameter("avatarname");
        String role = request.getParameter("role");
        UserInformation user = new UserInformation();
        user.setId(uid);
        user.setUsername(username);
        user.setNickname(nickname);
        user.setRealname(realname);
        user.setGender(gender);
        user.setSno(sno);
        user.setPhone(phone);
        user.setEmail(email);
        user.setDormitory(dormitory);
        user.setAvatar(avatarname);
        user.setRole(Integer.valueOf(role));
        try {
            int result = userInformationService.updateByPrimaryKeySelective(user);
            if (result != 1) {
                Response.replace("code", 4);
                Response.replace("message", "更新用户信息失败");
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

    @RequestMapping(value = "/usershops/info")
    public Map<String, Object> GetUserShopInfo(HttpServletRequest request,
                                           @RequestParam("sellerUid")Integer uid) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        UserInformation user = userInformationService.selectByPrimaryKey(uid);
        if(user == null)
        {
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            return Response;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("sellerUid", uid);
        map.put("sellerNickname", user.getNickname());
        Response.put("data", map);

        return Response;
    }

    @RequestMapping(value = "/info")
    public Map<String, Object> GetUserInfo(HttpServletRequest request,
                                           @RequestParam("username")String username) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        UserInformation user = userInformationService.selectByUsername(username);
        if(user == null)
        {
            Response.replace("code", 4);
            Response.replace("message", "出错啦");
            return Response;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("uid", user.getId());
        map.put("username", user.getUsername());
        map.put("nickname", user.getNickname());
        map.put("headIcon", user.getAvatar());
        map.put("realname", user.getRealname());
        map.put("gender", user.getGender());
        map.put("phone", user.getPhone());
        map.put("email", user.getEmail());
        map.put("dormitory", user.getDormitory());
        map.put("role", user.getRole());
        map.put("sno", user.getSno());
        Response.put("data", map);

        return Response;
    }

    @RequestMapping(value = "/info/list_all")
    public Map<String, Object> GetUserInfo(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        if(StringUtils.getInstance().isNullOrEmpty(request.getParameter("searchRole"))
                ||StringUtils.getInstance().isNullOrEmpty(request.getParameter("page"))
                ||StringUtils.getInstance().isNullOrEmpty(request.getParameter("size")))
        {
            Response.replace("code", 4);
            Response.replace("message", "请求数据为空");
            return Response;
        }
        String searchType = request.getParameter("searchType");
        int searchRole = Integer.parseInt(request.getParameter("searchRole"));
        String searchContent = request.getParameter("searchContent");
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));

        try {
            int nums = userService.getSearchCountByRole(searchContent, searchRole, searchType);

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

            List<UserInformation> result = userService.searchUserPageByRole(page, size, searchContent, searchRole, searchType);

            if (result == null)
            {
                Response.replace("code", 4);
                Response.replace("message", "出错了");
                return Response;
            }

            List<Map<String, Object>> list = new ArrayList<>();
            for(UserInformation user : result)
            {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("UserId", user.getId());
                userMap.put("UserUsername", user.getUsername());
                userMap.put("UserRealname", user.getRealname());
                userMap.put("UserNickname", user.getNickname());
                userMap.put("role", user.getRole());
                userMap.put("sno", user.getSno());
                userMap.put("phone", user.getPhone());
                userMap.put("email", user.getEmail());
                userMap.put("gender", user.getGender());
                userMap.put("headIcon", user.getAvatar());
                userMap.put("dormitory", user.getDormitory());
                String _createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreatetime());
                userMap.put("createTime", _createTime);
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

    @RequestMapping(value = "/address/info")
    public Map<String, Object> GetUserAddrInfo(HttpServletRequest request,
                                           @RequestParam("uid")Integer uid) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        List<UserAddress> addrs = userService.GetUserAddress(uid);
        if (addrs.size() == 0)
        {
            Response.replace("message", "无记录");
        }

        if(addrs == null)
        {
            Response.replace("code", 4);
            Response.replace("message", "出错啦");
            return Response;
        }
        List<Map<String, Object>> data = new ArrayList<>();
        for(UserAddress addr: addrs){
            Map<String, Object> map = new HashMap<>();
            map.put("addressId", addr.getId());
            map.put("buyerName", addr.getName());
            map.put("buyerPhone", addr.getTel());
            map.put("buyerAddress", addr.getAddress());
            map.put("defaultAddress", addr.getCommon());
            data.add(map);
        }
        Response.put("data", data);

        return Response;
    }

    @RequestMapping(value = "/address/set_default")
    public Map<String, Object> SetDefaultUserAddrInfo(HttpServletRequest request,
                                               @RequestParam("uid")Integer uid, @RequestParam("addressId")Integer addressId) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        UserAddress addr = new UserAddress();
        addr.setUid(uid);
        addr.setId(addressId);
        //        设置为默认
        addr.setCommon(1);

        // 其他地址设为非默认，找出默认的。
        List<UserAddress> DefaultAddrs = userService.GetUserDefaultAddress(uid);
        for(UserAddress _addr : DefaultAddrs){
            _addr.setCommon(0);
            userService.ModifyUserAddress(_addr);
        }
        boolean b = userService.ModifyUserAddress(addr);
        if(!b)
        {
            Response.replace("code", 4);
            Response.replace("message", "出错啦");
            return Response;
        }


        return Response;
    }

    @RequestMapping(value = "/address/save", method = RequestMethod.POST)
    public Map<String, Object> AddUserAddrInfo(HttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String buyerName = request.getParameter("buyerName");
//        Integer addressId = Integer.valueOf(request.getParameter("addressId"));
        Integer uid = Integer.valueOf(request.getParameter("uid"));
        String tel = request.getParameter("buyerPhone");
        String address = request.getParameter("buyerAddress");
        Integer common = Integer.valueOf(request.getParameter("defaultAddress"));
        UserAddress addr = new UserAddress();
        addr.setAddress(address);
//        addr.setId(addressId);
        addr.setUid(uid);
        addr.setName(buyerName);
        addr.setCommon(common);
        addr.setTel(tel);

        if(common == 1){
            // 其他地址设为非默认，找出默认的。
            List<UserAddress> DefaultAddrs = userService.GetUserDefaultAddress(uid);
            for(UserAddress _addr : DefaultAddrs){
                _addr.setCommon(0);
                userService.ModifyUserAddress(_addr);
            }
        }

        boolean b = userService.AddUserAddress(addr);
        if(!b)
        {
            Response.replace("code", 4);
            Response.replace("message", "出错啦");
            return Response;
        }

        return Response;
    }

    @RequestMapping(value = "/address/delete")
    public Map<String, Object> DeleteUserAddrInfo(HttpServletRequest request,
                                                  @RequestParam("addressId")Integer addressId) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");


        boolean b = userService.DeleteUserAddress(addressId);

        if(!b)
        {
            Response.replace("code", 4);
            Response.replace("message", "出错啦");
            return Response;
        }
        return Response;
    }

    // 检查账户是否存在
    @RequestMapping(value = "/register/check")
    public Map<String, Object> SignUpCheck(HttpServletRequest request,
                                           @RequestBody Map<String, Object> form) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String username = form.get("username").toString();
        String nickname = form.get("nickname").toString();
        if (StringUtils.getInstance().isNullOrEmpty(username) || StringUtils.getInstance().isNullOrEmpty(nickname)) {
            Response.replace("code", 4);
            Response.replace("message", "用户名为空");
            return Response;
        }
        try {
            UserInformation user = userInformationService.selectByUsername(username);
            if (user != null) {
                Response.replace("code", 4);
                Response.replace("message", "用户名存在");
            }
            return Response;
        } catch (Exception e) {
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            e.printStackTrace();
            return Response;
        }
    }

    // 检查账户是否存在
    @RequestMapping(value = "/check_username")
    public Map<String, Object> CheckUsername(HttpServletRequest request,
                                           @RequestParam("username") String username) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        if (StringUtils.getInstance().isNullOrEmpty(username)) {
            Response.replace("code", 4);
            Response.replace("message", "用户名为空");
            return Response;
        }
        try {
            UserInformation user = userInformationService.selectByUsername(username);
            if (user == null) {
                Response.replace("code", 4);
                Response.replace("message", "用户名不存在");
            }
            assert user != null;
            Response.put("data", user.getId());
            return Response;
        } catch (Exception e) {
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            e.printStackTrace();
            return Response;
        }
    }

    @RequestMapping(value = "/info_simply")
    public Map<String, Object> SignUpCheck(HttpServletRequest request,
                                           @RequestParam("uid") Integer uid) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        if (StringUtils.getInstance().isNullOrEmpty(uid)) {
            Response.replace("code", 4);
            Response.replace("message", "用户名为空");
            return Response;
        }
        try {
            UserInformation user = userInformationService.selectByPrimaryKey(uid);
            if (user == null) {
                Response.replace("code", 4);
                Response.replace("message", "用户名不存在");
            }
            assert user != null;
            Map<String, Object> map = new HashMap<>();
            map.put("uid", user.getId());
            map.put("username", user.getUsername());
            map.put("nickname", user.getNickname());
            map.put("realname", user.getRealname());
            map.put("phone", user.getPhone());
            map.put("dormitory", user.getDormitory());
            map.put("sno", user.getSno());
            Response.put("data", map);
            return Response;
        } catch (Exception e) {
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            e.printStackTrace();
            return Response;
        }
    }

    // 检查账户密码
    @RequestMapping(value = "/check_password")
    public Map<String, Object> CheckUserPassword(HttpServletRequest request,
                                           @RequestBody Map<String, Object> form) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String username = form.get("username").toString();
        String password = form.get("password").toString();
        Integer role = Integer.valueOf(form.get("role").toString());
        if (StringUtils.getInstance().isNullOrEmpty(username) || StringUtils.getInstance().isNullOrEmpty(password)) {
            Response.replace("code", 4);
            Response.replace("message", "用户名为空");
            return Response;
        }
        try {
            boolean b = userService.CheckUserPasswd(username, password);
            if (!b) {
                Response.replace("code", 4);
                Response.replace("message", "密码错误");
            }
            UserInformation user = userInformationService.selectByUsername(username);
            if(!Objects.equals(user.getRole(), role))
            {
                Response.replace("code", 4);
                Response.replace("message", "用户身份不匹配");
            }
            return Response;
        } catch (Exception e) {
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            e.printStackTrace();
            return Response;
        }
    }


    // 检查账户是否存在
    @RequestMapping(value = "/update_password")
    public Map<String, Object> ChangeUserPassword(HttpServletRequest request,
                                                 @RequestBody Map<String, Object> form) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        String username = form.get("username").toString();
        String password = form.get("password").toString();
        if (StringUtils.getInstance().isNullOrEmpty(username) || StringUtils.getInstance().isNullOrEmpty(password)) {
            Response.replace("code", 4);
            Response.replace("message", "用户名为空");
            return Response;
        }
        try {
            int uid = userInformationService.selectIdByUsername(username);
            UserPassword record = userPasswordService.selectByUid(uid);
            record.setPassword(password);
            record.setModified(new Date());
            int n = userPasswordService.updateByPrimaryKey(record);
            if (n != 1) {
                Response.replace("code", 4);
                Response.replace("message", "更新失败");
            }
            return Response;
        } catch (Exception e) {
            Response.replace("code", 4);
            Response.replace("message", "出错了");
            e.printStackTrace();
            return Response;
        }
    }
//    @RequestMapping("/icon")
//    public Map<String, Object> upload(HttpServletRequest request,
//                                      @RequestBody Map<String, Object> form) {
//        MultipartFile image = (MultipartFile) form.get("multipartFile");
//        String username = form.get("username").toString();
//        Map<String, Object> Response = new HashMap<>();
//        Response.put("code", 0);
//        Response.put("message", "ok");
//        try {
//            String imgName = username.replaceAll(" ", "");
//            //保存图片
//            String imgFilePath = "E:\\vueprojs\\images\\user_avatars\\" + imgName;
//            OutputStream out = new FileOutputStream(imgFilePath);
//            out.write(image.getBytes());
//            out.flush();
//            out.close();
//            return Response;
//        } catch (Exception ioE)
//        {
//            Response.replace("code", 4);
//            Response.replace("message", "上传图片失败");
//            ioE.printStackTrace();
//            return Response;
//        }
//    }

    @RequestMapping("/addicon")
    public Map<String, Object> upload(MultipartHttpServletRequest request) {
        Map<String, Object> Response = new HashMap<>();
        Response.put("code", 0);
        Response.put("message", "ok");
        try {
            MultipartFile image = request.getFile("file");
            String  fileName = image.getOriginalFilename();

            String imgName = UUID.randomUUID() + "-" + fileName.replaceAll(" ", "");
            //保存图片
            String imgFilePath = "E:\\vueprojs\\deal-vue-master\\src\\save_images\\user_avatars\\" + imgName;
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

}
