package com.hd.blog.common;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @ClassName CommonEntity
 * @Description 公共实体类
 * @Author huangda
 * @Date 2021/1/17 10:39 下午
 * @Version 1.0
 **/
@Data
public class CommonEntity {

    // 当前页
    @TableField(exist = false)
    public Long page;

    // 每页数量
    @TableField(exist = false)
    public Long limit;
}
