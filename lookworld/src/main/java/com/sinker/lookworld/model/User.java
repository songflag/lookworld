package com.sinker.lookworld.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.model
 * Author: sinker
 * CreateTime: 2025-03-04  17:23
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Data
@JsonIgnoreProperties({"handler"})
public class User implements Serializable {
    private Integer id;
    @NotEmpty(message = "账户不能为空")
    @Length(min = 3,max = 12,message = "账户必须3~12位")
    private String account;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6,max = 12,message = "密码必须6~12位")
    private String password;
    private String picture;
    @NotEmpty(message = "姓名不能为空")
    private String name;
    private String gender;
    @NotEmpty(message = "电话不能为空")
    @Length(min = 11,max = 11,message = "电话必须11位")
    private String phone;
    @Email(message = "邮箱格式不正确")
    private String email;
    private Integer isAdmin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    //sin 用户角色
    private Role role;
}
