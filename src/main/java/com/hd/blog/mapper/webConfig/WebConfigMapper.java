package com.hd.blog.mapper.webConfig;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hd.blog.entity.WebConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @InterfaceName WebConfigMapper
 * @Description
 * @Author huangda
 * @Date 2021/2/1 2:08 下午
 * @Version 1.0
 **/
@Component
@Mapper
public interface WebConfigMapper extends BaseMapper<WebConfig> {
}
