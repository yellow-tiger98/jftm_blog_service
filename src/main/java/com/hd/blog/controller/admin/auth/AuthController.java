package com.hd.blog.controller.admin.auth;

import com.hd.blog.common.Result;
import com.hd.blog.entity.SysUser;
import com.hd.blog.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @PutMapping("/updatePwd")
    public String updatePassword(HttpServletRequest request, @RequestParam(name = "oldPwd", required = true)String oldPwd,
                                 @RequestParam(name = "newPwd", required = true)String newPwd){
        if (request.getAttribute("userUid") == null){
            return Result.error("token用户过期");
        }
        if (oldPwd .equals(newPwd)){
            return Result.error("修改失败，原因:新密码与旧密码一致");
        }
        authService.updatePwd(request.getAttribute("userUid").toString(), oldPwd, newPwd);
        return Result.success();
    }

    @PostMapping("/logout")
    public String logOut(){
        // 因为先经过过滤器，如果用户token还有效，就会往request的请求上下文中存储一些信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = (String) request.getAttribute("token");
        authService.logout(token);
        return Result.success();
    }
}
