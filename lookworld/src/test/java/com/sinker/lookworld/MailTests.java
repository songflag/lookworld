package com.sinker.lookworld;


import com.sinker.lookworld.service.impl.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld
 * Author: sinker
 * CreateTime: 2025-03-11  15:52
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */
@SpringBootTest
public class MailTests {

    @Autowired
    private EmailService emailService;



    @Test
    public void sendEmail() throws MessagingException {
        String code =emailService.sendHtmlEmail("sgq8892@163.com","修改");
        System.out.println(code);
    }



}
