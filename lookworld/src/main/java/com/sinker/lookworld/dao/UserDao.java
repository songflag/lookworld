package com.sinker.lookworld.dao;


import com.sinker.lookworld.model.User;
import com.sinker.lookworld.model.search.UserEditPW;
import com.sinker.lookworld.model.search.UserRegister;
import com.sinker.lookworld.model.search.UserSearch;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.dao
 * Author: sinker
 * CreateTime: 2025-03-04  17:22
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Mapper
public interface UserDao {

    List<User> getUserAll(UserSearch userSearch);

    int deleteByIds(Integer[] ids);

    int addUser(User user);

    int updateUser(User user);

    User getUserByAccount(String account);

    int updatePW(UserEditPW user);

    int registerUser(UserRegister user);

}
