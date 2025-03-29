package com.sinker.lookworld.api;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.sinker.lookworld.model.Project;
import com.sinker.lookworld.model.BookProject;
import com.sinker.lookworld.model.User;
import com.sinker.lookworld.model.search.ProjectSearch;
import com.sinker.lookworld.service.ProjectService;
import com.sinker.lookworld.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.api
 * Author: sinker
 * CreateTime: 2025-03-08  22:43
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@RestController
@RequestMapping(value = "/api/project",produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectApi {
    private ProjectService projectService;
    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list")
    public JsonResult getProjectList(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit,
                                   ProjectSearch projectSearch){
        Page<?> pi = new Page<>(page,limit);
        List<Project> list = this.projectService.getProjectList(projectSearch,pi);
        PageInfo<Project> pageInfo = new PageInfo<>(list);
        if (!list.isEmpty()) {
            JsonResult jr = JsonResult.success("查询成功", list);
            jr.setCount((int) pageInfo.getTotal());
            return jr;
        }
        return JsonResult.fail("查询失败");
    }

    //sin 通过id删除项目
    @DeleteMapping("/delete")
    public JsonResult DeleteByIds(@RequestBody Integer [] ids){
        if (ids!=null){
            return this.projectService.deleteByIds(ids);
        }
        return JsonResult.fail("删除失败了");
    }

    //sin 添加项目
    @PostMapping("/add")
    public JsonResult addUser(@RequestBody @Validated Project project) {
        boolean is = this.projectService.addProject(project);
        if (is) {
            return JsonResult.success("添加成功");
        } else {
            return JsonResult.fail("添加失败");
        }
    }

    //sin 根据id修改项目
    @PatchMapping("/update")
    public JsonResult updateUser(@RequestBody @Validated Project project) {
        boolean is = this.projectService.editProject(project);
        if (is){
            return JsonResult.success("修改成功");
        }else {
            return JsonResult.fail("修改失败");
        }
    }

    //sin 获取预约项目的人员列表
    @GetMapping("/userList")
    public JsonResult getUserList(Integer projectId){
        System.out.println(projectId);
        //todo 判断参数
        List<User> list = this.projectService.getUserList(projectId);
        if (list!=null){
            return JsonResult.success("获取成功",list);
        }
        return JsonResult.fail("获取失败");
    }

    //sin 发布项目
    @PostMapping("/publicProject")
    public JsonResult publicProject(@RequestBody Map<String,Integer> map){
        Integer id = map.get("projectId");
        Integer pp = map.get("publicProject");
        if(id!=null&&pp!=null){
            String st = "关闭成功";
            if(pp==1){
                st="发布成功";
            }
            boolean is = this.projectService.publicProject(id,pp);
            if(is){
                return JsonResult.success(st);
            }
        }
        return JsonResult.fail("发布失败");
    }

    //sin 设置项目完成状态
    @PostMapping("/finishState")
    public  JsonResult finishState(@RequestBody Map<String,Integer> map){
        Integer id = map.get("id");
        Integer state = map.get("state");
        if(id!=null&&state!=null){
            boolean is = this.projectService.finishState(id,state);
            if(is){
                return  JsonResult.success("成功修改状态");
            }
        }
        return  JsonResult.fail("操作失败");
    }
}
