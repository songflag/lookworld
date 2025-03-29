package com.sinker.lookworld.common;


import cn.dev33.satoken.stp.StpUtil;
import com.sinker.lookworld.model.Permission;
import com.sinker.lookworld.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.common
 * Author: sinker
 * CreateTime: 2025-03-12  16:47
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Component
public class SecurityHandlerInterceptor implements HandlerInterceptor {
    private RoleService roleService;
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    //sin 权限校验拦截：判断该用户是否有该路径的权限
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String uri = req.getRequestURI();
        String method = req.getMethod();
        PathMatcher matcher = new AntPathMatcher();
        boolean hasMatch=true;
        List<Permission> allPerms = getAllPerms();
        for (Permission perm : allPerms){
            String url = perm.getUrl();
            if (matcher.match(url,uri)&&("all".equals(perm.getType())||method.equals(perm.getType()))){
                hasMatch=false;
                if(StpUtil.hasPermission(perm.getName())){
                    return true;
                }
            }
        }
        if(hasMatch){
            return true;
        }
        resp.setStatus(904);
        return false;
    }

    //sin 获取数据库中的所有权限
    private List<Permission> getAllPerms(){
        return this.roleService.getPermissions();
    }
}
