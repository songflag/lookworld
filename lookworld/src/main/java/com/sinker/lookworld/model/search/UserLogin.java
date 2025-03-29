package com.sinker.lookworld.model.search;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.model.search
 * Author: sinker
 * CreateTime: 2025-03-10  19:24
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Data
public class UserLogin {
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 4,max = 12,message = "用户名必须4-12位")
    private String username;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4,max = 12,message = "密码必须4-12位")
    private String password;
    @NotEmpty(message = "验证码不能为空")
    private String captcha;
    private String captchaId;
}
