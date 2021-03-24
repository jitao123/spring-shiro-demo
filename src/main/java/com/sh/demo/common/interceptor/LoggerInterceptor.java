package com.sh.demo.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sh.demo.common.util.RegexMobileUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 日志拦截器
 */
@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {

    /**logger*/
    private static Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    /**请求进入时间*/
    private final static String REQUEST_ENTER_TIME = "__request_enter_time__";

    /**过滤静态资源正则*/
    private final static String REGEX_STATIC_RESOURCE = "^(/static/)+";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!RegexMobileUtil.find(REGEX_STATIC_RESOURCE, request.getRequestURI())) {
            request.setAttribute(REQUEST_ENTER_TIME, System.currentTimeMillis());
            log.info(">>>>>>>>>新的请求进入");
            log.info("请求方式: " + request.getMethod());
            log.info("url: " + request.getRequestURI());
            log.info("完整请求路径: " + request.getRequestURL());
            log.info("请求参数: " + JSON.toJSONString(request.getParameterMap(), SerializerFeature.DisableCircularReferenceDetect), SerializerFeature.WriteMapNullValue);
            log.info("ContentType: " + request.getContentType());
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {


        if (!RegexMobileUtil.find(REGEX_STATIC_RESOURCE, request.getRequestURI())) {

            Long enterTime = (Long) request.getAttribute(REQUEST_ENTER_TIME);

            logger.info("处理耗时: " + (System.currentTimeMillis() - enterTime) + "ms");

            logger.info(">>>>>>>>>请求结束");
        }
    }
}
