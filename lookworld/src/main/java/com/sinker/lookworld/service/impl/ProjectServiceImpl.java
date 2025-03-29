package com.sinker.lookworld.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinker.lookworld.dao.BookingDao;
import com.sinker.lookworld.dao.ProjectDao;
import com.sinker.lookworld.model.BookProject;
import com.sinker.lookworld.model.BookState;
import com.sinker.lookworld.model.Project;
import com.sinker.lookworld.model.User;
import com.sinker.lookworld.model.search.ProjectSearch;
import com.sinker.lookworld.service.ProjectService;
import com.sinker.lookworld.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * BelongsProject: lookWorld
 * BelongsPackage: com.sinker.lookWorld.service.impl
 * Author: sinker
 * CreateTime: 2025-03-08  11:58
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectDao projectDao;
    @Autowired
    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }
    private BookingDao bookingDao;
    @Autowired
    public void setBookingDao(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    //sin 通过page分页获取项目信息
    @Override
    public List<Project> getProjectList(ProjectSearch projectSearch, Page<?> pi) {
        try (Page<?> __ = PageHelper.startPage(pi.getPageNum(),pi.getPageSize())){
            return this.projectDao.getLists(projectSearch);
        }
    }

    //sin 添加项目
    @Override
    public boolean addProject(Project project) {
        project.setCreatedAt(LocalDateTime.now());
        project.setEndTime(LocalDate.from(project.getStartTime().atStartOfDay().plusDays(project.getDuration()/25+1)));
        return this.projectDao.addProject(project)>0;
    }

    //sin 批量删除项目
    @Override
    public JsonResult deleteByIds(Integer[] ids)  {
        //sin 判断该项目是否已经有用户预约，有的话不能删除此项目
        boolean hasUser =  this.bookingDao.isHaveUser(ids,BookState.fromName("已预约"))>0;
        if(hasUser){
            return JsonResult.fail("该项目有用户与预约，无法删除，请通知用户取消预约");
        }else {
            boolean is = this.projectDao.deleteByIds(ids)>0;
            if(is){
                return JsonResult.success("删除成功");
            }
        }
        return JsonResult.fail("删除失败");
    }

    //sin 编辑项目
    @Override
    public boolean editProject(Project project) {
        int count = this.projectDao.getBookUserCount(project.getId());
        if(count>project.getCount()){
            return false;
        }
        project.setBookCount(project.getCount()-count);
        project.setUpdatedAt(LocalDateTime.now());
        project.setEndTime(LocalDate.from(project.getStartTime().atStartOfDay().plusDays(project.getDuration()/25+1)));
        return this.projectDao.editProject(project)>0;
    }

    //sin 判断该用户是否已经预约过此项目
    @Override
    public boolean getBooking(BookProject bookProject) {
        bookProject.setStatus(BookState.CONFIRMED);
        return this.projectDao.getBooking(bookProject)>0;
    }


    //sin 事务性操作，预约和减少可被预约的人数，应该是原子性的操作
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addBooking(BookProject bookProject) {
        //sin 设置一些预约属性
        bookProject.setStatus(BookState.CONFIRMED);
        bookProject.setCreatedAt(LocalDateTime.now());
        bookProject.setUpdatedAt(LocalDateTime.now());

        //sin 减少可以被预约的人数
        this.projectDao.setBookCount(bookProject.getBookCount(),bookProject.getProjectId());
        return this.projectDao.addBooking(bookProject);
    }


    //sin获取预约信息
    @Override
    public List<User> getUserList(Integer projectId) {
//         this.projectDao.getProjectById(projectId);
        List<Integer> ids = this.bookingDao.getIds(projectId,BookState.fromName("已预约"));
        if(ids!=null&& !ids.isEmpty()){
            return this.bookingDao.getUserList(ids);
        }
        return null;
    }

    @Override
    public boolean publicProject(Integer id, Integer pp) {
        return this.projectDao.publicProject(id,pp)>0;
    }


    //sin 事务，修改 完成状态同时需要修改预约表中的状态
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean finishState(Integer id, Integer state) {
        int count1 = this.bookingDao.setStatusByProjectId(id,BookState.fromName("已完成"),BookState.fromName("已预约"));
        int count2 = this.projectDao.finishState(id,state);
        return count1 > 0 && count2 > 0;
    }
}
