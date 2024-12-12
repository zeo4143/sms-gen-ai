package com.assessment.sms.authetication.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Student {
    @Id
    private String studentId;
    private String password;
}
