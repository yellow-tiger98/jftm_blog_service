package com.hd.blog.service.webConfig.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hd.blog.entity.WebConfig;
import com.hd.blog.mapper.webConfig.WebConfigMapper;
import com.hd.blog.service.webConfig.WebConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName WebConfigServiceImpl
 * @Description
 * @Author huangda
 * @Date 2021/2/1 2:07 下午
 * @Version 1.0
 **/
@Slf4j
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService {



    @Override
    public WebConfig getWebConfig() {
        List<WebConfig> webConfigList = list();
        return webConfigList.get(0);
    }
}
