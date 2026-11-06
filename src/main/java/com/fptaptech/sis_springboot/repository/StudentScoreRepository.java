package com.fptaptech.sis_springboot.repository;

import com.fptaptech.sis_springboot.model.StudentScore;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentScoreRepository extends JpaRepository<StudentScore, Integer> {

    @Query("""
            select score from StudentScore score
            join fetch score.student
            join fetch score.subject
            where score.student.studentId in :studentIds
            order by score.student.studentId, score.subject.subjectName
            """)
    List<StudentScore> findAllForStudents(@Param("studentIds") Collection<Integer> studentIds);

    boolean existsByStudentStudentIdAndSubjectSubjectId(Integer studentId, Integer subjectId);
}
