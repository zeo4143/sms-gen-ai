package com.assessment.sms.studentInfo.repo;

import com.assessment.sms.studentInfo.model.StudentAcademicRecordsModel;
import com.assessment.sms.studentInfo.model.StudentAcademicRecordsRequest;

import java.util.List;
import java.util.Map;

public interface StudentAcademicRecordsRepo {

    StudentAcademicRecordsModel getSingleStudentAcademicRecordsByStudentById(String studentId);

    List<StudentAcademicRecordsModel> getStudentsAcademicRecordsByStudentsById(List<String> studentIds);

    List<StudentAcademicRecordsModel> getStudentsAcademicRecordsByParam(List<String> studentIds, Map<String, String> params);

    List<StudentAcademicRecordsModel> updateStudentsAcademicRecords(List<StudentAcademicRecordsRequest> studentAcademicRecordsRequests);

}
