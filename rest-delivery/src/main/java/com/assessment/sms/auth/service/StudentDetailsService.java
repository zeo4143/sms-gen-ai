package com.assessment.sms.auth.service;

import com.assessment.sms.authetication.model.Student;
import com.assessment.sms.authetication.model.StudentPrincipal;
import com.assessment.sms.authetication.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("StudentDetailsService")
public class StudentDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public UserDetails loadUserByUsername(String studentName) throws UsernameNotFoundException {
        Student student = studentRepo.findByStudentId(studentName);
        if (student == null) {
            throw new UsernameNotFoundException(studentName);
        }

        return new StudentPrincipal(student);
    }
}
