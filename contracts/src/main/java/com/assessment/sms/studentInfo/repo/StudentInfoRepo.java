package com.assessment.sms.studentInfo.repo;

import com.assessment.sms.studentInfo.model.StudentInfoModel;

import java.util.List;
import java.util.Map;

public interface StudentInfoRepo {

    StudentInfoModel getStudentInfoByRegNo(String studentId);

    List<StudentInfoModel> getAllStudentInfo();

    List<StudentInfoModel> getStudentsInfoByParam(Map<String, Object> params);


    List<StudentInfoModel> createStudentsInfo(List<StudentInfoModel> studentInfoModelList);

    List<StudentInfoModel> updateStudentsInfo(List<StudentInfoModel> studentInfoModelList);

    void deleteStudentInfo(String studentId);
}
