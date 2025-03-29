package com.sinker.lookworld.dao;


import com.sinker.lookworld.model.Guide;
import com.sinker.lookworld.model.search.GuideSearch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.dao
 * Author: sinker
 * CreateTime: 2025-03-07  16:37
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Mapper
public interface GuideDao {

    List<Guide> getGuideList(GuideSearch guideSearch);

    int deleteByIds(Integer[] ids);

    int addGuide(Guide guide);

    int updateGuide(Guide guide);

    List<GuideSearch> getGuides();


}
