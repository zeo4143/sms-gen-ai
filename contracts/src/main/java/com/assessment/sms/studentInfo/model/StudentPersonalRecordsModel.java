package com.assessment.sms.studentInfo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StudentPersonalRecordsModel {
    private String motherName;
    private String fatherName;
    private AddressModel addressModel;
    private String studentContactNumber;
    private String parentContactNumber;
    private String emergencyContactNumber;
    private String email;
    private Date dob;
    private String gender;
    private Date dateOfJoining;
}
