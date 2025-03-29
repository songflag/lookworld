package com.sinker.lookworld.api;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.sinker.lookworld.model.User;
import com.sinker.lookworld.model.search.UserSearch;
import com.sinker.lookworld.service.UserService;
import com.sinker.lookworld.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.api
 * Author: sinker
 * CreateTime: 2025-03-04  17:05
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@RestController
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminApi {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public JsonResult getList(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer limit,
                              UserSearch userSearch) {
        Page<?> p = new Page<>(page, limit);
        List<User> list = this.userService.getUserAll(userSearch, p);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        JsonResult jr = JsonResult.success("查询成功", list);
        jr.setCount((int) pageInfo.getTotal());
        return jr;
    }

    @DeleteMapping("/delete")
    public JsonResult deleteByIds(@RequestBody Integer[] ids) {
        if (ids != null) {
            boolean is = this.userService.deleteByIds(ids);
            if (is) {
                return JsonResult.success("删除成功！");
            }
            return JsonResult.success("删除失败！");
        }
        return JsonResult.fail("传入值不能为空");
    }

    @PostMapping("/add")
    public JsonResult addUser(@RequestBody @Validated User user) {
        boolean is = this.userService.addUser(user);
        if (is) {
            return JsonResult.success("添加成功");
        } else {
            return JsonResult.fail("添加失败");
        }
    }

    @PatchMapping("/update")
    public JsonResult updateUser(@RequestBody @Validated User user) {
        boolean is = this.userService.updateUser(user);
        if (is){
            return JsonResult.success("修改成功");
        }else {
            return JsonResult.fail("修改失败");
        }
    }

}
