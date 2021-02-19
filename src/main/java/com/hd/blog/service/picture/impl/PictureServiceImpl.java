package com.hd.blog.service.picture.impl;

import com.hd.blog.common.CommonException;
import com.hd.blog.constant.FileConf;
import com.hd.blog.entity.PictureConfig;
import com.hd.blog.service.picture.PictureService;
import com.hd.blog.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PictureServiceImpl
 * @Description
 * @Author huangda
 * @Date 2021/2/6 11:50 上午
 * @Version 1.0
 **/
@Slf4j
@Service
public class PictureServiceImpl implements PictureService {


    @Autowired
    PictureConfig pictureConfig;


    @Override
    public Map<String, Object> upload(MultipartFile imgFile, String type) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            byte[] bytes = imgFile.getBytes();
            String imageFileName = imgFile.getOriginalFilename();
            // 文件上传路径
            Path path = null;
            // 新生成的文件名
            String fileName = null;

            // 获取图片上传配置
            String uploadPath = null;
            String getUrl = null;
            if ("dev".equals(pictureConfig.getEnv())){
                uploadPath = pictureConfig.getDevPath();
                getUrl = pictureConfig.getDevUrl();
            }else{
                uploadPath = pictureConfig.getProdPath();
                getUrl = pictureConfig.getProdUrl();
            }

            // 根据类型选择上传路径
            if ("cover".equals(type)){
                fileName = FileUtils.getPhotoName("cover", imageFileName);
                    path = Paths.get(uploadPath + FileConf.COVER_TYPE + FileConf.SPILT + fileName);
            }
            if ("doc".equals(type)){
                fileName = FileUtils.getPhotoName("doc", imageFileName);
                    path = Paths.get(uploadPath + FileConf.DOC_TYPE + FileConf.SPILT + fileName);
            }

            Files.write(path, bytes);
            resultMap.put("uploaded", "1");
            resultMap.put("fileName", fileName);
            resultMap.put("url", getUrl + FileConf.GET + fileName);
        } catch (IOException e) {
            throw new CommonException("图片上传错误", e);
        }
        return resultMap;
    }

    @Override
    public void getPicture(HttpServletResponse response, String fileName) {
        try {
            response.setContentType("image/jpeg;charset=utf-8");
            response.setHeader("Content-Disposition", "inline; filename=girls.png");
            ServletOutputStream outputStream = response.getOutputStream();
            String path = null;
            if ("dev".equals(pictureConfig.getEnv())){
                path = pictureConfig.getDevPath();
            }else {
                path = pictureConfig.getProdPath();
            }
            if (fileName.startsWith("cover")){
                outputStream.write(Files.readAllBytes(Paths.get(path + FileConf.COVER_TYPE).resolve(fileName)));

            }
            if (fileName.startsWith("doc")){
                outputStream.write(Files.readAllBytes(Paths.get(path + FileConf.DOC_TYPE).resolve(fileName)));
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
