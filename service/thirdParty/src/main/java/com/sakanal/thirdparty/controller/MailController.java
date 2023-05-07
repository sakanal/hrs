package com.sakanal.thirdparty.controller;

import com.sakanal.service.dto.EmailCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/thirdParty/Mail")
public class MailController {
    @Resource
    private JavaMailSender javaMailSender;

    //发件人邮箱
    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendMail(String subject, String text, String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //收件人
        mailMessage.setTo(email);  //收件人邮箱也可以为其他，如qq邮箱
        //发件人
        mailMessage.setFrom(fromEmail);
        //设置标题
        mailMessage.setSubject(subject);
        //设置正文
        mailMessage.setText(text);
        //发送邮件
        javaMailSender.send(mailMessage);
    }

    @RequestMapping("/send")
    public String send(@RequestBody EmailCodeDTO emailCodeDTO) {
        sendMail(emailCodeDTO.getSubject(), emailCodeDTO.getText(),emailCodeDTO.getEmail());  //收件人邮箱作为参数是为了从前端获取
        return "success";
    }

}

