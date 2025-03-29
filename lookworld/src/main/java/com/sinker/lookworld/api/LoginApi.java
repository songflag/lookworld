package com.sinker.lookworld.api;


import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.sinker.lookworld.model.User;
import com.sinker.lookworld.model.search.UserEditPW;
import com.sinker.lookworld.model.search.UserLogin;
import com.sinker.lookworld.model.search.UserRegister;
import com.sinker.lookworld.service.UserService;
import com.sinker.lookworld.service.impl.EmailService;
import com.sinker.lookworld.util.JsonResult;
import com.wf.captcha.SpecCaptcha;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.api
 * Author: sinker
 * CreateTime: 2025-03-10  15:50
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@RestController
@RequestMapping("/api/login")
public class LoginApi {
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    //sin 验证码
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse resp) throws IOException {
        SpecCaptcha specCaptcha = new SpecCaptcha(110, 40, 4);
        resp.setContentType("image/gif");
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0L);
        String uuid = UUID.randomUUID().toString();
        //存入到redis中
        redisTemplate.opsForValue().set("captcha-" + uuid, specCaptcha.text().toLowerCase(), Duration.ofSeconds(90));
        resp.setHeader("captcha-id", uuid);
        specCaptcha.out(resp.getOutputStream());
    }

    //sin 用户登录
    @PostMapping(value = "/userLogin", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResult userLogin(@RequestBody @Validated UserLogin userLogin) {
        //todo 校验参数
        //sin 校验验证码
        String rtCaptcha = (String) this.redisTemplate.opsForValue().get("captcha-" + userLogin.getCaptchaId());
        //sin 验证码失效
        if (rtCaptcha == null) {
            return JsonResult.fail("验证码失效");
        }
        //sin 验证码不正确
        if (!rtCaptcha.equals(userLogin.getCaptcha().toLowerCase())) {
            return JsonResult.fail("验证码不正确");
        }
        //sin 验证码正确,删除验证码
        this.redisTemplate.delete("captcha-" + userLogin.getCaptchaId());
        User user = this.userService.getUserByAccount(userLogin.getUsername());
        if (user == null) {
            return JsonResult.fail("用户不存在");
        }
        //todo使用密码加密
        if (!user.getPassword().equals(userLogin.getPassword())) {
            return JsonResult.fail("用户名和密码不匹配");
        }
        //todo 验证通过,颁发jwt令牌
        HashMap<String, String> map = new HashMap<>();
        map.put("account", user.getAccount());
//        String token = JwtUtil.getToken(map);
        map.put("photo", user.getPicture());
        map.put("id", user.getId().toString());
        map.put("roleId",user.getRole().getId().toString());
        map.put("roleName", user.getRole().getName());
        StpUtil.login(user.getId(), SaLoginConfig.setExtra("account", user.getAccount()));
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        map.put("token", tokenInfo.getTokenValue());
//        this.redisTemplate.opsForValue().set(token,true,Duration.ofSeconds(1800));
        return JsonResult.success("登录成功", map);
    }

    //sin 获取密码修改邮箱验证码
    @PostMapping(value = "/emailCode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResult getEditEmailCode(@RequestBody Map<String, String> map) {
        //sin 一系列判断是否适合发送邮箱验证码
        String account = map.get("account");
        String email = map.get("email");
        String code = map.get("code");
        if (!StringUtils.hasText(account) || !StringUtils.hasText(email)) {
            return JsonResult.fail("账户和邮件不能为空");
        }
        if (code.equals("edit")) {
            code = "修改";
            User user = this.userService.getUserByAccount(account);
            if (!email.equals(user.getEmail())) {
                return JsonResult.fail("邮件和账户不匹配");
            }
        } else {
            code = "注册";
        }
        try {
            //sin 获取邮箱验证码
            String s = this.emailService.sendHtmlEmail(email, code);
            this.redisTemplate.opsForValue().set(account + email, s, Duration.ofSeconds(300));
        } catch (Exception e) {
            return JsonResult.fail("获取验证码失败");
        }
        return JsonResult.success("发送邮件成功");
    }


    //sin 修改密码
    @PostMapping(value = "/editPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonResult editPassword(@RequestBody @Validated UserEditPW user) {
        try {
            String eCode = (String) this.redisTemplate.opsForValue().get(user.getAccount() + user.getEmail());
            if (eCode == null) {
                return JsonResult.fail("验证码失效");
            }
            if (!user.getEmailCode().equals(eCode)) {
                return JsonResult.fail("验证码不正确");
            }
        } catch (Exception e) {
            return JsonResult.fail("验证码已失效");
        }
        if (!user.getPassword1().equals(user.getPassword2())) {
            return JsonResult.fail("两次输入密码不一致");
        }
        boolean is = this.userService.updatePWByAccount(user);
        if (!is) {
            return JsonResult.fail("修改密码失败");
        }
        return JsonResult.success("修改密码成功");
    }

    //sin 注册用户
    @PostMapping("/register")
    public JsonResult registerUser(@RequestBody UserRegister user) {
        try {
            String code = (String) this.redisTemplate.opsForValue().get(user.getAccount() + user.getEmail());
            if (code == null) {
                return JsonResult.fail("验证码失效");
            }
            if (!user.getEmailCode().equals(code)) {
                return JsonResult.fail("验证码不正确");
            }
        } catch (Exception e) {
            return JsonResult.fail("验证码已失效");
        }
        if (!user.getPassword1().equals(user.getPassword2())) {
            return JsonResult.fail("两次输入密码不一致");
        }
        user.setPassword(user.getPassword1());
        boolean is = this.userService.registerUser(user);
        if (!is) {
            return JsonResult.fail("注册失败");
        }
        return JsonResult.success("注册成功");
    }

    @GetMapping("/logout")
    public JsonResult logout(){
        StpUtil.logout();
        return JsonResult.success("退出成功");
    }

}
