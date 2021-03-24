package com.sh.demo.common.exception;


import com.sh.demo.result.ResultCode;
import com.sh.demo.result.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 自定义异常
 */
@Slf4j
@ControllerAdvice
public class MyShiroException {
    /**
     * 处理Shiro权限拦截异常
     * 如果返回JSON数据格式请加上 @ResponseBody注解
     * @Return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultVo defaultErrorHandler(Exception e){

        log.info("===========>出现异常信息～～～～～～～");
        log.info(e.getMessage());
        if (e instanceof  BusinessException){
            //返回自定义的信息
            return ResultVo.error(e.getMessage());
        }else if (e instanceof AuthorizationException){
           // 返回权限不足提示
            return ResultVo.error(ResultCode.FORBIDDEN);
        }else if (e instanceof MissingServletRequestParameterException) {
           // 返回缺失参数信息
            String message = e.getMessage();
            int start = message.indexOf("'");
            int end = message.lastIndexOf("'");
            String substring = "";
            if (start != -1 && end != -1) {
                substring = message.substring(start + 1, end);
            }
            return  ResultVo.error("缺少  "+substring+"  参数～～～");
        }else if (e instanceof IncorrectCredentialsException){
            return ResultVo.error("用户不存在或者密码错误");
        }else if (e instanceof LockedAccountException){
            return ResultVo.error("登录失败，该用户已被冻结");
        } else if (e instanceof AuthenticationException){
            return ResultVo.error("该用户不存在");
        }
            // 返回不能定义的异常
        return ResultVo.error("服务器繁忙");
    }






}
