package com.sinker.lookworld.api;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.sinker.lookworld.model.BookProject;
import com.sinker.lookworld.model.BookState;
import com.sinker.lookworld.model.Project;
import com.sinker.lookworld.model.search.ProjectSearch;
import com.sinker.lookworld.service.BookingService;
import com.sinker.lookworld.service.ProjectService;
import com.sinker.lookworld.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.api
 * Author: sinker
 * CreateTime: 2025-03-14  22:50
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@RestController
@RequestMapping(value = "/api/booking", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingApi {
    private BookingService bookingService;
    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    private ProjectService projectService;
    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    //sin 获取用户的预约信息
    @GetMapping("/myList")
    public JsonResult getMyBooks(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit,
                                 Integer userId, String status){
        Page<?> pi = new Page<>(page,limit);
        List<BookProject> list = this.bookingService.getMyBooks(userId,BookState.fromName(status),pi);
        PageInfo<BookProject> pageInfo = new PageInfo<>(list);
        if (!list.isEmpty()) {
            JsonResult jr = JsonResult.success("查询成功", list);
            jr.setCount((int) pageInfo.getTotal());
            return jr;
        }
        return JsonResult.fail("查询失败");
    }

    //sin 用户取消预约api
    @PatchMapping("/cancelBook")
    public JsonResult cancelBook(@RequestBody BookProject bookProject){
        boolean is =  this.bookingService.cancelBook(bookProject);
        if(is){
            return JsonResult.success("取消预约成功");
        }
        return JsonResult.fail("取消预约失败");
    }

    //sin 删除用户的预约记录
    @DeleteMapping("/delRecord")
    public JsonResult delRecord(@RequestBody Map<String,Integer> map){
        Integer id = map.get("id");
        if(id!=null){
            boolean is = this.bookingService.delRecord(id);
            if(is){
                return JsonResult.success("删除成功");
            }
        }
        return JsonResult.fail("删除失败");
    }

    //sin  预约项目
    @PostMapping("/booking")
    public JsonResult bookProject(@RequestBody BookProject bookProject){
        if(bookProject.getBookCount()<0){
            return JsonResult.fail("预约失败，预约人数已经达到上限！");
        }
        boolean hasBook = this.projectService.getBooking(bookProject);
        if(hasBook){
            return JsonResult.fail("你已经预定该项目，快去个人中心查看吧！");
        }
        boolean addBooking = this.projectService.addBooking(bookProject);
        if(addBooking){
            return JsonResult.success("预定成功，期待你再次预约！");
        }
        return JsonResult.fail("预约出错了！");
    }

    //sin 获取项目列表
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

}
