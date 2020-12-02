package com.hd.blog.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        //判断是不是ajax请求
        boolean isAjax = false;
        if(StringUtils.isNotBlank(request.getHeader("x-requested-with"))&& request.getHeader("x-requested-with").equals("XMLHttpRequest")){
            isAjax = true;
        }


        response.sendRedirect("/toDenied");
    }


}
