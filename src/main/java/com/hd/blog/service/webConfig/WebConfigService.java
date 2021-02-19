package com.hd.blog.service.webConfig;

import com.hd.blog.entity.WebConfig;

/**
 * @InterfaceName WebConfigService
 * @Description
 * @Author huangda
 * @Date 2021/2/1 2:07 下午
 * @Version 1.0
 **/
public interface WebConfigService {

    /**
     * @Description 获取网站配置
     * @Param
     * @return com.hd.blog.entity.WebConfig
     * @Author huangda
     * @Date 2021/2/1 2:10 下午
     **/
    WebConfig getWebConfig();
}
