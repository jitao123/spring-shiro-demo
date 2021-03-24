package com.sh.demo.controller;

import com.sh.demo.common.util.SHA256Util;
import com.sh.demo.common.util.ShiroUtils;
import com.sh.demo.core.entity.SysUserEntity;
import com.sh.demo.core.entity.SysUserRoleEntity;
import com.sh.demo.core.service.SysUserRoleService;
import com.sh.demo.core.service.SysUserService;
import com.sh.demo.result.ResultVo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

/**
 * @Description 用户登录
 */
@RestController
@RequestMapping("/userLogin")
public class UserLoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 登录
     */
    @RequestMapping("/login")
    public ResultVo login(@RequestParam("email") String email, @RequestParam("password") String password) throws Exception{
        //验证身份和登陆
        Subject subject = SecurityUtils.getSubject();
        //进行身份验证
        SysUserEntity sysUserEntity = sysUserService.selectUserByEmails(email);
        if (ObjectUtils.isEmpty(sysUserEntity)){
            // 向上抛出用户不存在异常
            throw new  IncorrectCredentialsException();
        }
        UsernamePasswordToken token = new UsernamePasswordToken(sysUserEntity.getUsername(), password);
            //进行登录操作
        subject.login(token);
        return   ResultVo.success("登录成功",ShiroUtils.getSession().getId().toString());
    }
    /**
     * 未登录
     */
    @RequestMapping("/unauth")
    public ResultVo unauth(){
        return   ResultVo.error("未登录");
    }


    /**
     * 添加一个用户演示接口
     * 这里仅作为演示不加任何权限和重复查询校验
     */
    @RequestMapping("/testAddUser")
    public ResultVo testAddUser(){
        // 设置基础参数
        SysUserEntity sysUser = new SysUserEntity();
        sysUser.setUsername("user1");
        sysUser.setState("NORMAL");
        // 随机生成盐值
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setSalt(salt);
        // 进行加密
        String password ="123456";
        sysUser.setPassword(SHA256Util.sha256(password, sysUser.getSalt()));
        // 保存用户
        sysUserService.save(sysUser);
        // 保存角色
        SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
        // 保存用户完之后会把ID返回给用户实体
        sysUserRoleEntity.setUserId(sysUser.getUserId());
        sysUserRoleService.save(sysUserRoleEntity);
        // 返回结果
        return ResultVo.success("添加成功");
    }
}