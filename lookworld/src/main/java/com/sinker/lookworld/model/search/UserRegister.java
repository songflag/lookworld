package com.sinker.lookworld.model.search;


import com.sinker.lookworld.model.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.model.search
 * Author: sinker
 * CreateTime: 2025-03-17  14:11
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Getter
@Setter
public class UserRegister extends User {
    @NotEmpty(message = "验证码不能为空")
    @Length(min = 4,max = 4,message = "验证码必须四位")
    private String emailCode;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4,max = 12,message = "密码必须在4-12位")
    private String password1;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4,max = 12,message = "密码必须在4-12位")
    private String password2;
}
