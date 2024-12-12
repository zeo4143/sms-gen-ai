package com.assessment.sms.authetication.repo;

import com.assessment.sms.authetication.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, String> {

    Student findByStudentId(String studentId);

}

