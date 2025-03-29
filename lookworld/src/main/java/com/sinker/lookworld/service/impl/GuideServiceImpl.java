package com.sinker.lookworld.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinker.lookworld.dao.GuideDao;
import com.sinker.lookworld.model.Guide;
import com.sinker.lookworld.model.search.GuideSearch;
import com.sinker.lookworld.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service.impl
 * Author: sinker
 * CreateTime: 2025-03-07  16:36
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Service
public class GuideServiceImpl implements GuideService {
    private GuideDao guideDao;
    @Autowired
    public void setGuideDao(GuideDao guideDao) {
        this.guideDao = guideDao;
    }

    @Override
    public List<Guide> getGuideList(GuideSearch guideSearch, Page<?> p) {
        try(Page<?> __ = PageHelper.startPage(p.getPageNum(),p.getPageSize())){
            return this.guideDao.getGuideList(guideSearch);
        }
    }

    @Override
    public boolean deleteByIds(Integer[] ids) {
        return this.guideDao.deleteByIds(ids)>0;
    }

    @Override
    public boolean addGuide(Guide guide) {
        guide.setCreatedAt(LocalDateTime.now());
        return this.guideDao.addGuide(guide)>0;
    }

    @Override
    public boolean updateGuide(Guide guide) {
        guide.setUpdatedAt(LocalDateTime.now());
        return this.guideDao.updateGuide(guide)>0;
    }

    //sin 获取导游的列表，用户添加项目时选择负责人
    @Override
    public List<GuideSearch> getGuides() {
        return this.guideDao.getGuides();
    }
}
