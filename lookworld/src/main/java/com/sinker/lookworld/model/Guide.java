package com.sinker.lookworld.model;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.model
 * Author: sinker
 * CreateTime: 2025-03-07  16:34
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Data
public class Guide implements Serializable {
    private Integer id;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
