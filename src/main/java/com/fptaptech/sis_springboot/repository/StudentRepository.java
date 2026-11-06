package com.fptaptech.sis_springboot.repository;

import com.fptaptech.sis_springboot.model.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllByOrderByFullNameAsc();

    boolean existsByStudentCode(String studentCode);
}
