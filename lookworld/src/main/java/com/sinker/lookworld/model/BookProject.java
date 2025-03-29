package com.sinker.lookworld.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.model.search
 * Author: sinker
 * CreateTime: 2025-03-14  19:31
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Getter
@Setter
@JsonIgnoreProperties("handler")
public class BookProject implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer guideId;
    private Integer projectId;
    private BookState status;
    private Integer userView;
    private Integer adminView;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //sin 用于预约时减少库存数量
    private Integer bookCount;

    //sin 每个与预约对应一个项目
    private Project project;
}
