package com.hd.blog.handler.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hd.blog.utils.JsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt认证进入点,用于token失效或者非法
 * @author 陌溪
 * @date 2020年9月19日10:04:54
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Map<String, Object> result = new HashMap<>(3);
        result.put("code", "401");
        result.put("msg", msg);
        result.put("data", "token无效或过期,请重新登录");
        response.getWriter().write(JsonUtils.objectToJson(result));
    }
}

