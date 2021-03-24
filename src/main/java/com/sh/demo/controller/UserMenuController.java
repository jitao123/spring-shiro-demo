package com.sh.demo.controller;

import com.sh.demo.common.util.ShiroUtils;
import com.sh.demo.core.entity.SysMenuEntity;
import com.sh.demo.core.entity.SysRoleEntity;
import com.sh.demo.core.entity.SysRoleMenuEntity;
import com.sh.demo.core.entity.SysUserEntity;
import com.sh.demo.core.service.SysMenuService;
import com.sh.demo.core.service.SysRoleMenuService;
import com.sh.demo.core.service.SysRoleService;
import com.sh.demo.core.service.SysUserService;
import com.sh.demo.result.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 权限测试
 */
@RestController
@RequestMapping("/menu")
public class UserMenuController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 获取用户信息集合
     * @Return
     */
    @RequestMapping("/getUserInfoList")
    @RequiresPermissions("sys:user:info")
    public ResultVo getUserInfoList(){
        List<SysUserEntity> sysUserEntityList = sysUserService.list();
        return ResultVo.success(sysUserEntityList);
    }

    /**
     * 获取角色信息集合
     * @Return
     */
    @RequestMapping("/getRoleInfoList")
    @RequiresPermissions("sys:role:info")
    public ResultVo getRoleInfoList(){
        List<SysRoleEntity> sysRoleEntityList = sysRoleService.list();
        return ResultVo.success(sysRoleEntityList);
    }

    /**
     * 获取权限信息集合
     * @Return
     */
    @RequestMapping("/getMenuInfoList")
    @RequiresPermissions("sys:menu:info")
    public ResultVo getMenuInfoList(){
        List<SysMenuEntity> sysMenuEntityList = sysMenuService.list();
        return ResultVo.success(sysMenuEntityList);
    }

    /**
     * 获取所有数据
     * @Return
     */
    @RequestMapping("/getInfoAll")
    @RequiresPermissions("sys:info:all")
    public ResultVo getInfoAll(){
        Map<String,Object> map = new HashMap<>();
        List<SysUserEntity> sysUserEntityList = sysUserService.list();
        map.put("sysUserEntityList",sysUserEntityList);
        List<SysRoleEntity> sysRoleEntityList = sysRoleService.list();
        map.put("sysRoleEntityList",sysRoleEntityList);
        List<SysMenuEntity> sysMenuEntityList = sysMenuService.list();
        map.put("sysMenuEntityList",sysMenuEntityList);

        return ResultVo.success(map);
    }

    /**
     * 添加管理员角色权限(测试动态权限更新)
     * @Return
     */
    @RequestMapping("/addMenu")
    public ResultVo addMenu(){
        //添加管理员角色权限
        SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
        sysRoleMenuEntity.setMenuId(4L);
        sysRoleMenuEntity.setRoleId(1L);
        sysRoleMenuService.save(sysRoleMenuEntity);
        //清除缓存
        String username = "admin";
        ShiroUtils.deleteCache(username,false);
        return ResultVo.success("权限添加成功");
    }
}