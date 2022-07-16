package com.ojt.student_mybatis.model;

import java.util.List;

public class Student {
    private int id;
    private String stuId;
    private String stuName;
    private String stuDob;
    private String stuGender;
    private String stuPhone;
    private String stuEducation;
    private List<String> stuCourseId;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStuId() {
        return stuId;
    }
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
    public String getStuName() {
        return stuName;
    }
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
    public String getStuDob() {
        return stuDob;
    }
    public void setStuDob(String stuDob) {
        this.stuDob = stuDob;
    }
    public String getStuGender() {
        return stuGender;
    }
    public void setStuGender(String stuGender) {
        this.stuGender = stuGender;
    }
    public String getStuPhone() {
        return stuPhone;
    }
    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }
    public String getStuEducation() {
        return stuEducation;
    }
    public void setStuEducation(String stuEducation) {
        this.stuEducation = stuEducation;
    }
    public List<String> getStuCourseId() {
        return stuCourseId;
    }
    public void setStuCourseId(List<String> stuCourseId) {
        this.stuCourseId = stuCourseId;
    }
    @Override
    public String toString() {
        return "Student [id=" + id + ", stuCourseId=" + stuCourseId + ", stuDob=" + stuDob + ", stuEducation="
                + stuEducation + ", stuGender=" + stuGender + ", stuId=" + stuId + ", stuName=" + stuName
                + ", stuPhone=" + stuPhone + "]";
    }
    
}
