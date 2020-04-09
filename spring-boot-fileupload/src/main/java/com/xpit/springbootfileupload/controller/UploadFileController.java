package com.xpit.springbootfileupload.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传控制层
 */
@RestController
public class UploadFileController {
    // 日期格式化
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @PostMapping("/file/upload")
    public String upload(MultipartFile file, HttpServletRequest request) throws IOException {
        String format = sdf.format(new Date());
        //存放文件的路径
        //String realPath = request.getSession().getServletContext().getRealPath("/upload") + format;
        String realPath = "F:\\upload" + format;
        File folder = new File(realPath);
        if (!folder.isDirectory()) {
            //如果目录不存在则创建
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        // 需要抛出异常
        file.transferTo(new File(folder, newName));
        // 图片上传后访问的地址
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/upload" + format + newName;
        return "url:" + url;

    }
}
