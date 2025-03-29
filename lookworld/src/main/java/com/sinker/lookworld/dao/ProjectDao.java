package com.sinker.lookworld.dao;


import com.sinker.lookworld.model.BookProject;
import com.sinker.lookworld.model.Project;
import com.sinker.lookworld.model.search.ProjectSearch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.dao
 * Author: sinker
 * CreateTime: 2025-03-08  11:58
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Mapper
public interface ProjectDao {

    List<Project> getLists(ProjectSearch projectSearch);

    int addProject(Project project);

    int deleteByIds(Integer[] ids);

    int editProject(Project project);

    @Select(value = "select count(0) from bookings where user_id=#{userId} and project_id = #{projectId} and status=#{status}")
    int getBooking(BookProject bookProject);

    boolean addBooking(BookProject bookProject);

    @Update(value = "update projects set book_count=#{bookCount} where id = #{projectId} ")
    void setBookCount(Integer bookCount,Integer projectId);

    @Update(value = "update projects set public_project = #{pp} where id = #{id}")
    int publicProject(Integer id, Integer pp);

    @Update(value = "update projects set state = #{state} where id = #{id}")
    int finishState(Integer id, Integer state);

    @Select(value = "select `count`-book_count from projects where id = #{id}")
    int getBookUserCount(Integer id);


}
