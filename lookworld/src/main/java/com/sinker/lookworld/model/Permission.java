package com.sinker.lookworld.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.model
 * Author: sinker
 * CreateTime: 2025-03-12  15:22
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Data
public class Permission implements Serializable {

    private Integer id;
    private String name;
    private String url;
    private String type;
    private String  description;
}
