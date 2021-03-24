package com.sh.demo.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sh.demo.core.dao.SysUserDao;
import com.sh.demo.core.entity.SysUserEntity;
import com.sh.demo.core.service.SysUserService;

import java.util.List;

/**
 * @Description 系统用户业务实现
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    /**
     * 根据用户名查询实体
     * @Param  username 用户名
     * @Return SysUserEntity 用户实体
     */
    @Override
    public SysUserEntity selectUserByName(String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserEntity::getUsername,username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户邮箱号查询实体
     * @param email  用户名
     * @return SysUserEntity 用户实体
     */
    @Override
    public SysUserEntity selectUserByEmails(String email) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserEntity::getEmailAddress,email);
        return this.baseMapper.selectOne(queryWrapper);
    }
}