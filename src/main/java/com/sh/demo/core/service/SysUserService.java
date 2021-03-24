package com.sh.demo.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sh.demo.core.entity.SysUserEntity;

/**
 * @Description 系统用户业务接口
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 根据用户名查询实体
     * @param  username 用户名
     * @return SysUserEntity 用户实体
     */
    SysUserEntity selectUserByName(String username);


    /**
     * 根据用户邮箱号码查询实体
     * @param  email 用户邮箱
     * @return SysUserEntity 用户实体
     */
    SysUserEntity selectUserByEmails(String email);

}

