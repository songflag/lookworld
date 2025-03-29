package com.sinker.lookworld.api;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.sinker.lookworld.model.Permission;
import com.sinker.lookworld.model.Role;
import com.sinker.lookworld.model.search.RoleAddEdit;
import com.sinker.lookworld.service.PermService;
import com.sinker.lookworld.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.api
 * Author: sinker
 * CreateTime: 2025-03-21  11:14
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@RestController
@RequestMapping("/api/perm")
public class PermApi {
    private PermService permService;

    @Autowired
    public void setPermService(PermService permService) {
        this.permService = permService;
    }

    //sin 查询权限列表
    @GetMapping("/permList")
    public JsonResult getPermList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit){
        if(page!=null&&limit!=null){
            Page<?> pi = new Page<>(page,limit);
            List<Permission> list = this.permService.getPermList(pi);
            PageInfo<Permission> pageInfo = new PageInfo<>(list);
            if(!list.isEmpty()){
                JsonResult jr = JsonResult.success("查询成功", list);
                jr.setCount((int) pageInfo.getTotal());
                return jr;
            }
        }
        return JsonResult.fail("查询失败");
    }

    //sin 添加权限
    @PostMapping("/addPerm")
    public JsonResult addPerm(@RequestBody Permission permission){
        if(permission!=null){
            boolean is = this.permService.addPerm(permission);
            if(is){
                return JsonResult.success("添加成功");
            }
            return JsonResult.fail("添加失败");
        }
        return JsonResult.fail("传入值不能为空");
    }

    //sin 修改权限
    @PostMapping("/updatePerm")
    public JsonResult updatePerm(@RequestBody Permission permission){
        if(permission!=null){
            boolean is = this.permService.updatePermById(permission);
            if(is){
                return JsonResult.success("修改成功");
            }
            return JsonResult.fail("修改失败");
        }
        return JsonResult.fail("传入值不能为空");
    }

    //sin 删除权限
    @DeleteMapping("/deletePerm")
    public JsonResult deletePerm(@RequestBody Integer[] ids){
        if(ids!=null){
            boolean is = this.permService.deletePermByIds(ids);
            if(is){
                return JsonResult.success("删除成功");
            }
            return JsonResult.fail("删除失败");
        }
        return JsonResult.fail("传入值不能为空");
    }

    //sin 获取角色列表
    @GetMapping("/roleList")
    public JsonResult getRoleList(){
        List<Role> list = this.permService.getRoleList();
        if(!list.isEmpty()){
            return JsonResult.success("查询成功",list);
        }
        return JsonResult.fail("查询失败");
    }

    //sin 添加角色权限
    @PostMapping("/addRole")
    public JsonResult addRole(@RequestBody RoleAddEdit role){
        if(role!=null){
            boolean is = this.permService.addRole(role);
            if(is){
                return JsonResult.success("添加成功");
            }
            return JsonResult.fail("添加失败");
        }
        return JsonResult.fail("传入值为空");
    }

    //sin 修改角色权限
    @PostMapping("/updateRole")
    public JsonResult updateRole(@RequestBody RoleAddEdit role){
        if(role!=null){
            boolean is = this.permService.updateRole(role);
            if(is){
                return JsonResult.success("修改成功");
            }
            return JsonResult.fail("修改失败");
        }
        return JsonResult.fail("传入值为空");
    }

}
