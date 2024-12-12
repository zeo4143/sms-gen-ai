package com.assessment.sms.auth.controller;

import com.assessment.sms.auth.service.StudentAuthService;
import com.assessment.sms.authetication.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class StudentAuthController {

    @Autowired
    private StudentAuthService studentAuthService;

    @PostMapping("/signup")
    public Student registerStudent(@RequestBody Student student) {
        return studentAuthService.registerStudent(student);
    }

    @PostMapping("/login")
    public String login(@RequestBody Student student) {
        return studentAuthService.verifyToken(student);
    }
}
