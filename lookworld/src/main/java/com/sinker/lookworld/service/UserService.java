package com.sinker.lookworld.service;


import com.github.pagehelper.Page;
import com.sinker.lookworld.model.User;
import com.sinker.lookworld.model.search.UserEditPW;
import com.sinker.lookworld.model.search.UserRegister;
import com.sinker.lookworld.model.search.UserSearch;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service
 * Author: sinker
 * CreateTime: 2025-03-04  17:25
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

public interface UserService {
    List<User> getUserAll(UserSearch userSearch, Page<?> p);

    boolean deleteByIds(Integer[] ids);

    boolean addUser(User user);

    boolean updateUser(User user);

    User getUserByAccount(String account);

    boolean updatePWByAccount(UserEditPW user);

    boolean registerUser(UserRegister user);

}
