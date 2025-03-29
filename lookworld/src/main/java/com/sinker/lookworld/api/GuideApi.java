package com.sinker.lookworld.api;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.sinker.lookworld.model.Guide;
import com.sinker.lookworld.model.User;
import com.sinker.lookworld.model.search.GuideSearch;
import com.sinker.lookworld.service.GuideService;
import com.sinker.lookworld.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.api
 * Author: sinker
 * CreateTime: 2025-03-07  16:39
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@RestController
@RequestMapping(value = "/api/guide",produces = MediaType.APPLICATION_JSON_VALUE)
public class GuideApi {
    private GuideService guideService;
    @Autowired
    public void setGuideService(GuideService guideService) {
        this.guideService = guideService;
    }

    @GetMapping("/list")
    public JsonResult getGuideList(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit,
                                   GuideSearch guideSearch){
        Page<?> p = new Page<>(page,limit);
        List<Guide> list = this.guideService.getGuideList(guideSearch,p);
        PageInfo<Guide> pageInfo = new PageInfo<>(list);
        if (!list.isEmpty()) {
            JsonResult jr = JsonResult.success("查询成功", list);
            jr.setCount((int) pageInfo.getTotal());
            return jr;
        }
        return JsonResult.fail("查询失败");
    }

    @DeleteMapping("/delete")
    public JsonResult DeleteByIds(@RequestBody Integer [] ids){
        if (ids!=null){
            boolean is = this.guideService.deleteByIds(ids);
            if (is){
                return JsonResult.success("删除成功了");
            }
        }
        return JsonResult.fail("删除失败了");
    }

    @PostMapping("/add")
    public JsonResult addUser(@RequestBody @Validated Guide guide) {
        boolean is = this.guideService.addGuide(guide);
        if (is) {
            return JsonResult.success("添加成功");
        } else {
            return JsonResult.fail("添加失败");
        }
    }

    @PatchMapping("/update")
    public JsonResult updateUser(@RequestBody @Validated Guide guide) {
        boolean is = this.guideService.updateGuide(guide);
        if (is){
            return JsonResult.success("修改成功");
        }else {
            return JsonResult.fail("修改失败");
        }
    }

    @GetMapping("/guides")
    public JsonResult getGuides(){
        List<GuideSearch> list =  this.guideService.getGuides();
        if(list!=null){
            return JsonResult.success("查询成功",list);
        }
        return JsonResult.fail("查询失败");
    }

}
