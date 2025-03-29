package com.sinker.lookworld.model.search;


import lombok.Getter;
import lombok.Setter;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.model.search
 * Author: sinker
 * CreateTime: 2025-03-23  23:07
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Getter
@Setter
public class RoleAddEdit {
    private Integer id;
    private String name;
    private Integer[] permissions;

}
