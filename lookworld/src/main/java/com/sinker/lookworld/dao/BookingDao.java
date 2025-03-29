package com.sinker.lookworld.dao;


import com.sinker.lookworld.model.BookProject;
import com.sinker.lookworld.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.dao
 * Author: sinker
 * CreateTime: 2025-03-14  22:53
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Mapper
public interface BookingDao {

     List<BookProject> getMyBooks(Integer userId, String status);

     int cancelBook(BookProject bookProject);

     @Delete(value = "delete from bookings where id = #{id}")
     int delRecordById(Integer id);

     @Select(value = "select user_id from bookings where project_id = #{projectId} and status=#{status}")
     List<Integer> getIds(Integer projectId, String status);

     List<User> getUserList(List<Integer> ids);

     int isHaveUser(Integer[] ids, String status);

     @Update(value = "update bookings set status=#{status} where project_id=#{id} and status = #{status2}")
     int setStatusByProjectId(Integer id, String status,String status2);
}
