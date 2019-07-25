package com.linkedaim.admin.common.aop;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @author zhaoyangyang
 * @title 日志注解打印
 * @date 2019-07-18
 */
@Component
@Aspect
public class WriteLogHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Pointcut("@annotation(com.linkedaim.admin.common.aop.WriteLog) && execution(* com.linkedaim.admin.*.*(..))")
    public void pointCut() {
    }

    private static Logger logger = LoggerFactory.getLogger(WriteLogHandler.class);
    private static String lineSplit = "\n|\t";


    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        printStart(stringBuilder);
        stringBuilder.append(lineSplit);
        stringBuilder.append("执行方法:");
        buildMethodInfo(joinPoint, stringBuilder);
        stringBuilder.append(lineSplit);

        try {
            Object retVal = joinPoint.proceed();

            stringBuilder.append("返回值:" + JSON.toJSONString(retVal));
            return retVal;
        } catch (Throwable ex) {
            stringBuilder.append("发生异常:");
            throw ex;
        } finally {
            //调用
            stringBuilder.append(lineSplit);
            stringBuilder.append("耗时(微秒):");
            stringBuilder.append(System.currentTimeMillis() - startTime);
            printEnd(stringBuilder);
            logger.info(stringBuilder.toString());
        }


    }

    private void buildMethodInfo(JoinPoint joinPoint, StringBuilder stringBuilder) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        stringBuilder.append(method.getName());
        stringBuilder.append(lineSplit);
        stringBuilder.append("参数依次为:");
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Map) {
                Set set = ((Map) args[i]).keySet();
                stringBuilder.append(lineSplit);
                stringBuilder.append("\t");
                stringBuilder.append(i);
                stringBuilder.append(":");
                stringBuilder.append(JSON.toJSONString(set));
            } else {
                if (args[i] instanceof Serializable) {
                    stringBuilder.append(lineSplit);
                    stringBuilder.append("\t");
                    stringBuilder.append(i);
                    stringBuilder.append(":");
                    stringBuilder.append(JSON.toJSONString(args[i]));
                } else if (args[i] instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) args[i];
                    stringBuilder.append(lineSplit);
                    stringBuilder.append("\t");
                    stringBuilder.append(i);
                    stringBuilder.append(":");
                    stringBuilder.append(file.getName());
                }
            }
        }


    }

    private void printStart(StringBuilder stringBuilder) {
        stringBuilder.append("\n+-----------------");
    }

    private void printEnd(StringBuilder stringBuilder) {
        stringBuilder.append("\n\\__________________");
    }

}
