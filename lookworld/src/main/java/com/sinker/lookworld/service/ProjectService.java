package com.sinker.lookworld.service;


import com.github.pagehelper.Page;
import com.sinker.lookworld.model.Project;
import com.sinker.lookworld.model.BookProject;
import com.sinker.lookworld.model.User;
import com.sinker.lookworld.model.search.ProjectSearch;
import com.sinker.lookworld.util.JsonResult;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service
 * Author: sinker
 * CreateTime: 2025-03-08  11:57
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

public interface ProjectService {
    List<Project> getProjectList(ProjectSearch projectSearch, Page<?> pi);

    boolean addProject(Project project);

    JsonResult deleteByIds(Integer[] ids);

    boolean editProject(Project project);

    boolean getBooking(BookProject bookProject);

    boolean addBooking(BookProject bookProject);


    List<User> getUserList(Integer projectId);

    boolean publicProject(Integer id, Integer pp);

    boolean finishState(Integer id, Integer state);

}
