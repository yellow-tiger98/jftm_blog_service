package com.hd.blog.aspect;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 请求日志切面类，用于每次请求的日志打印
 * @Date 2020-12-08
 * @Author huangda
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("execution(public * com.hd.blog.controller..*.*(..))")
    public void webLog(){};

    /**
     * @description 在切点之前织入，也就是执行请求方法之前
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void before(JoinPoint joinPoint) throws Throwable{
        // 获取前端请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取请求参数进行打印
        List<Object> argList = new ArrayList<>();
        Object[] objects = joinPoint.getArgs();
        int index = 0;
        for (Object obj : objects){
            if (index != 0){
                argList.add(obj);
            }
            index++;
        }



        // 与上一行日志空一行
        log.info("");
        // 打印请求相关参数
        log.info("========================================== Start ==========================================");
        // 打印请求路径
        log.info("url           :{}" ,request.getRequestURL().toString());
        // 打印Http请求类型
        log.info("HTTP Method   :{}" ,request.getMethod());
        // 打印Controller的全路径 以及 方法名
        log.info("Class Method  :{},{}", joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        // 打印请求的IP
        log.info("IP            :{}", request.getRemoteAddr());
        // 打印请求入参
        log.info("Request Args  :{}", new Gson().toJson(argList));
    }

    /**
     * @description 环绕，用于计算执行方法耗时
     * @param proceedingJoinPoint
     * @return 方法执行结果
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
        log.info("Response Args  :{}", new Gson().toJson(result));
        // 执行耗时
        log.info("Time-Consuming :{}", System.currentTimeMillis() - startTime);

        log.info("========================================== end ==========================================");
        return result;
    }

    /**
     * @description 在切点之后织入，也就是方法执行完之后
     * @throws Throwable
     */
    @After("webLog()")
    public void after() throws Throwable{

    }

}
