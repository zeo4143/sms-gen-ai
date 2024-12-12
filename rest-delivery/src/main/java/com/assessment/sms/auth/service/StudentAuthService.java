package com.assessment.sms.auth.service;

import com.assessment.sms.authetication.model.Student;
import com.assessment.sms.authetication.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("StudentService")
public class StudentAuthService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private StudentRepo studentRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Student registerStudent(Student student) {
        student.setPassword(encoder.encode(student.getPassword()));
        return studentRepo.saveAndFlush(student);
    }

    public String verifyToken(Student student) {
        System.out.println(student.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        student.getStudentId(),
                        student.getPassword()
                )
        );
        System.out.println("principal "+authentication.getPrincipal());
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(student.getStudentId());
            System.out.println(token);
            return token;
        } else {
            return "Invalid Token";
        }
    }
}
