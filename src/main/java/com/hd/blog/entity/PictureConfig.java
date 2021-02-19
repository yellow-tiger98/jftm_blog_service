package com.hd.blog.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName PictureConfig
 * @Description 图片上传配置
 * @Author huangda
 * @Date 2021/2/7 2:34 下午
 * @Version 1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "upload")
public class PictureConfig {

    private String env;

    private String devUrl;

    private String prodUrl;

    private String devPath;

    private String prodPath;
}
