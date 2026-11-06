package com.fptaptech.sis_springboot.web;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ScoreForm {

    @NotNull(message = "Vui lòng chọn sinh viên.")
    @Positive(message = "Sinh viên không hợp lệ.")
    private Integer studentId;

    @NotNull(message = "Vui lòng chọn môn học.")
    @Positive(message = "Môn học không hợp lệ.")
    private Integer subjectId;

    @NotNull(message = "Vui lòng nhập điểm lần 1.")
    @DecimalMin(value = "0.0", message = "Điểm phải từ 0 đến 10.")
    @DecimalMax(value = "10.0", message = "Điểm phải từ 0 đến 10.")
    @Digits(integer = 2, fraction = 2, message = "Điểm chỉ được có tối đa 2 chữ số thập phân.")
    private BigDecimal score1;

    @NotNull(message = "Vui lòng nhập điểm lần 2.")
    @DecimalMin(value = "0.0", message = "Điểm phải từ 0 đến 10.")
    @DecimalMax(value = "10.0", message = "Điểm phải từ 0 đến 10.")
    @Digits(integer = 2, fraction = 2, message = "Điểm chỉ được có tối đa 2 chữ số thập phân.")
    private BigDecimal score2;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public BigDecimal getScore1() {
        return score1;
    }

    public void setScore1(BigDecimal score1) {
        this.score1 = score1;
    }

    public BigDecimal getScore2() {
        return score2;
    }

    public void setScore2(BigDecimal score2) {
        this.score2 = score2;
    }
}
