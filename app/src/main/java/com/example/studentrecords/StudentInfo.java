package com.example.studentrecords;

public class StudentInfo {
    private String name;
    private String roll_no;
    private String gender;
    private String qualification;
    private String dob;
    private String imageUri;
    private String age;

    public StudentInfo(String name, String roll_no, String gender, String qualification, String dob, String imageUri, String age) {
        this.name = name;
        this.roll_no = roll_no;
        this.gender = gender;
        this.qualification = qualification;
        this.dob = dob;
        this.imageUri = imageUri;
        this.age = age;
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

    public String getAge() {
        return age;
    }

    public String getImageUri() {
        return imageUri;
    }
}
