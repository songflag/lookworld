package com.sinker.lookworld.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.model
 * Author: sinker
 * CreateTime: 2025-03-12  15:21
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Getter
@Setter
@JsonIgnoreProperties("handler")
public class Role implements Serializable {
    private Integer id;
    private String name;
    private List<Permission> permissions;
}
