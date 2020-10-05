package com.example.studentrecords;

public class StudentInfo {
    private String name;
    private String roll_no;
    private String gender;
    private String qualification;
    private String dob;
    private String imageUri;

    public StudentInfo(String name, String roll_no, String gender, String qualification, String dob, String imageUri) {
        this.name = name;
        this.roll_no = roll_no;
        this.gender = gender;
        this.qualification = qualification;
        this.dob = dob;
        this.imageUri = imageUri;
    }

    public String get_Name() {
        return name;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public String getGender() {
        return gender;
    }

    public String getQualification() {
        return qualification;
    }

    public String getDob() {
        return dob;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}