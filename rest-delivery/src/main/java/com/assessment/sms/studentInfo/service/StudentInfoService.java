package com.assessment.sms.studentInfo.service;

import com.assessment.sms.studentInfo.model.StudentInfoModel;
import com.assessment.sms.studentInfo.mongo.repo.StudentInfoRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("studentInfoService")
public class StudentInfoService {

    @Autowired
    public StudentInfoRepoImpl studentInfoRepo;

    public List<StudentInfoModel> getAllStudentInfos() {
        return studentInfoRepo.getAllStudentInfo();
    }
    
    public StudentInfoModel getStudentInfoByRegNo(String regNo) {
        return studentInfoRepo.getStudentInfoByRegNo(regNo);
    }


    public List<StudentInfoModel> getStudentsInfoByParam(Map<String, Object> params) {
        return studentInfoRepo.getStudentsInfoByParam(params);
    }

    
    public List<StudentInfoModel> createStudentInfo(List<StudentInfoModel> studentInfoModelList) {
        return studentInfoRepo.createStudentsInfo(studentInfoModelList);
    }

    
    public List<StudentInfoModel> updateStudentInfo(List<StudentInfoModel> studentInfoModelList) {
        return studentInfoRepo.updateStudentsInfo(studentInfoModelList);
    }


    public void deleteStudentInfo(String studentId) {
        studentInfoRepo.deleteStudentInfo(studentId);
    }
}
