package com.sinker.lookworld.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.model
 * Author: sinker
 * CreateTime: 2025-03-08  11:51
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Data
@JsonIgnoreProperties("handler")
public class Project implements Serializable{
    private Integer id;
    private String title;
    private String description;
    private Integer guideId;
    private Double price;
    private LocalDate startTime;
    private LocalDate endTime;
    private Integer duration;
    private String location;
    private Integer bookCount;
    private Integer count;
    private Integer publicProject;
    private Integer state;
    private Integer adminView;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //sin导游的信息
    private Guide guide;
    //
}
