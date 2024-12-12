package com.assessment.sms.studentInfo.repo;

import com.assessment.sms.studentInfo.model.StudentPersonalRecordsModel;
import com.assessment.sms.studentInfo.model.StudentPersonalRecordsRequest;

import java.util.List;
import java.util.Map;

public interface StudentPersonalRecordsRepo {

    StudentPersonalRecordsModel getSingleStudentPersonalRecordsById(String studentId);

    List<StudentPersonalRecordsModel> studentPersonalRecordsByStudentById(List<String> studentIds);

    List<StudentPersonalRecordsModel> studentPersonalRecordsByParam(List<String> studentIds, Map<String, String> params);

    List<StudentPersonalRecordsModel> updateStudentPersonalRecords(List<StudentPersonalRecordsRequest> studentPersonalRecordsRequests);

}
