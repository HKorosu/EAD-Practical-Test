package com.fptaptech.sis_springboot.repository;

import com.fptaptech.sis_springboot.model.Subject;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    List<Subject> findAllByOrderBySubjectNameAsc();
}
