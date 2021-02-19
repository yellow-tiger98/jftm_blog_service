package com.hd.blog.controller.admin.picture;

import com.hd.blog.common.Result;
import com.hd.blog.constant.FileConf;
import com.hd.blog.service.picture.PictureService;
import com.hd.blog.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PictureController
 * @Description
 * @Author huangda
 * @Date 2021/2/5 10:59 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @PostMapping("/upload")
    public String upload(MultipartFile file, MultipartFile upload){
        Map<String, Object> map = new HashMap<>();
        if (file != null){
            // 用于封面图上传
            map = pictureService.upload(file, FileConf.COVER_TYPE);
            return Result.success(map);
        }
        if (upload != null){
            // 用于文章内图片上传
            map = pictureService.upload(upload, FileConf.DOC_TYPE);
            return JsonUtils.objectToJson(map);
        }
        return Result.error("图片上传出错！");
    }

    @GetMapping("/getImage/{name}")
    public void getImage(HttpServletResponse response, @PathVariable("name") String name){
        pictureService.getPicture(response, name);
    }
}
