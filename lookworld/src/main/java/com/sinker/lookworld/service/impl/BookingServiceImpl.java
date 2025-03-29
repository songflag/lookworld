package com.sinker.lookworld.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinker.lookworld.dao.BookingDao;
import com.sinker.lookworld.dao.ProjectDao;
import com.sinker.lookworld.model.BookProject;
import com.sinker.lookworld.model.BookState;
import com.sinker.lookworld.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service.impl
 * Author: sinker
 * CreateTime: 2025-03-14  22:52
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Service
public class BookingServiceImpl implements BookingService {
    private BookingDao bookingDao;
    @Autowired
    public void setBookingDao(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }
    private ProjectDao projectDao;
    @Autowired
    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    //sin 预约项目
    @Override
    public List<BookProject> getMyBooks(Integer userId,String status, Page page) {
        try(Page<?> __ = PageHelper.startPage(page.getPageNum(),page.getPageSize())){
            return this.bookingDao.getMyBooks(userId,status);
        }

    }

    //sin 取消预约，使用事务，保证取消预约和增加库存数量的原子性操作
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean cancelBook(BookProject bookProject) {
        bookProject.setStatus(BookState.CANCELLED);
        this.projectDao.setBookCount(bookProject.getBookCount(),bookProject.getProjectId());
        return this.bookingDao.cancelBook(bookProject)>0;
    }

    //sin 删除预约记录
    @Override
    public boolean delRecord(Integer id) {
        return this.bookingDao.delRecordById(id)>0;
    }


}
