package com.hd.blog.service.picture;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName PictureService
 * @Description
 * @Author huangda
 * @Date 2021/2/5 11:00 下午
 * @Version 1.0
 **/
public interface PictureService {


    /**
     * @Description 上传图片
     * @Param imgFile
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @Author huangda
     * @Date 2021/2/6 12:16 上午
     **/
    Map<String,Object> upload(MultipartFile imgFile, String type);

    /**
     * @Description 使用流输出照片
     * @Param response
     * @Param fileName
     * @return void
     * @Author huangda
     * @Date 2021/2/6 11:25 下午
     **/
    void getPicture(HttpServletResponse response, String fileName);
}
