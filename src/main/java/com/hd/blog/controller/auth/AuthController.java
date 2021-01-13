package com.hd.blog.controller.auth;

import com.hd.blog.common.Result;
import com.hd.blog.entity.SysUser;
import com.hd.blog.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AuthController
 * @Description 用户验证处理类
 * @Author huangda
 * @Date 2021/1/11 1:55 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public String login(HttpServletRequest request, @RequestParam(name = "username", required = false)String username,
                        @RequestParam(name = "password", required = false)String password){
        Map<String,Object> resultMap =  authService.login(username, password);
        if (StringUtils.isNotBlank(String.valueOf(resultMap.get("msg")))){
            return Result.error(String.valueOf(resultMap.get("msg")));
        }
        return Result.success((SysUser)resultMap.get("user"));
    }

    @GetMapping("/getInfo")
    public String getInfo(HttpServletRequest request, @RequestParam(name = "token", required = false)String token){
        if (request.getAttribute("userUid") == null){
            return Result.error("token用户过期");
        }
        Map<String,Object> resultMap = authService.getUserInfo(request.getAttribute("userUid").toString(),token);
        return Result.success(resultMap);
    }

    @GetMapping("/test401")
    public String test401(HttpServletRequest request, @RequestParam(name = "token", required = false)String token){
        return Result.error("");
    }
}
