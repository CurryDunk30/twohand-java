package com.ershou.bishe_demo1.Controller;

import com.ershou.bishe_demo1.tool.FileUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/pic")
public class FileController {
    @RequestMapping(value = "/avatar")
    public void GetAvatar(@RequestParam("avatar") String filename,
                                            HttpServletResponse response
    ) throws IOException {
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletResponse response = requestAttributes.getResponse();
        // 设置信息给客户端不解析
        String type = new MimetypesFileTypeMap().getContentType(filename);
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type",type);
        // 设置编码
        String hehe = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        String filePath = "E:\\vueprojs\\deal-vue-master\\src\\save_images\\user_avatars\\" + filename;
        FileUtils.download(filePath, response);
    }



    @RequestMapping(value = "/shops")
    public void GetShopImg(@RequestParam("shopimage") String filename,
                          HttpServletResponse response
    ) throws IOException {
        // 设置信息给客户端不解析
        String type = new MimetypesFileTypeMap().getContentType(filename);
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type",type);
        // 设置编码
        String hehe = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        String filePath = "E:\\vueprojs\\deal-vue-master\\src\\save_images\\shops\\" + filename;
        FileUtils.download(filePath, response);
    }

}
