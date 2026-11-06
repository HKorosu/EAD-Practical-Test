package com.fptaptech.sis_springboot.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentForm {

    @NotBlank(message = "Vui lòng nhập mã sinh viên.")
    @Size(max = 20, message = "Mã sinh viên không được vượt quá 20 ký tự.")
    private String studentCode;

    @NotBlank(message = "Vui lòng nhập họ và tên.")
    @Size(max = 100, message = "Họ và tên không được vượt quá 100 ký tự.")
    private String fullName;

    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự.")
    private String address;

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
