package com.sinker.lookworld.service;


import com.github.pagehelper.Page;
import com.sinker.lookworld.model.BookProject;
import com.sinker.lookworld.model.BookState;

import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service
 * Author: sinker
 * CreateTime: 2025-03-14  22:52
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

public interface BookingService {

    List<BookProject> getMyBooks(Integer userId, String status, Page page);


    boolean cancelBook(BookProject bookProject);

    boolean delRecord(Integer id);

}
