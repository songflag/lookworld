package com.sinker.lookworld.service.impl;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.service.impl
 * Author: sinker
 * CreateTime: 2025-03-11  15:44
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String sendFrom;
    private final String html1="<!DOCTYPE html><html lang=\"zh-CN\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>看世界验证码通知</title><style>body {font-family: Arial, sans-serif; line-height: 1.6; margin: 0; padding: 0; background-color: #f4f4f4;}.container {max-width: 600px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1);}.header {text-align: center; color: #333; margin-bottom: 20px;}.content {margin-bottom: 20px;}.code {font-size: 24px; font-weight: bold; color: #333; text-align: center; margin: 20px 0;}.note {color: #666; font-size: 14px; text-align: center;}</style></head><body><div class=\"container\"><div class=\"header\"><h1>验证码通知</h1></div><div class=\"content\"><p>尊敬的用户您好！</p><p>您的验证码为：</p><div class=\"code\">";
    private final String html2="</div><div class=\"note\">请在5分钟内完成";
    private final String html3="验证，否则失效</div></div></div></body></html>";
    private JavaMailSender javaMailSender;
    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    //sin实现邮箱验证的类，html就是返回html的内容，mode就是验证的模式，是注册还是修改密码
    public String sendHtmlEmail(String to,String mode) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(sendFrom); //发件人
        helper.setTo(to); // 收件人
        helper.setSubject("看世界预约系统验证"); // 主题
        String code = getCode();
        helper.setText(html1+code+html2+mode+html3,true); // 内容
        javaMailSender.send(mimeMessage); // 发送邮件
        return code;
    }
    //sin获取随机验证码
    public String getCode(){
        double random = Math.random()*10000+1000;
        return String.valueOf(((int) random)%10000);
    }

}
