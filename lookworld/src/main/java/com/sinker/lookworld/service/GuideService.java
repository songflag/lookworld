package com.sinker.lookworld.service;


import com.github.pagehelper.Page;
import com.sinker.lookworld.model.Guide;
import com.sinker.lookworld.model.search.GuideSearch;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service
 * Author: sinker
 * CreateTime: 2025-03-07  16:36
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

public interface GuideService {
    List<Guide> getGuideList(GuideSearch guideSearch, Page<?> p);

    boolean deleteByIds(Integer[] ids);

    boolean addGuide(Guide guide);

    boolean updateGuide(Guide guide);

    List<GuideSearch> getGuides();

}
