package com.sinker.lookworld.model.search;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.model.search
 * Author: sinker
 * CreateTime: 2025-03-07  16:50
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Setter
@Getter
public class GuideSearch implements Serializable {
    private Integer id;
    private String name;
    private String phone;
}
