package com.fptaptech.sis_springboot.service;

import com.fptaptech.sis_springboot.model.Student;
import com.fptaptech.sis_springboot.model.StudentScore;
import com.fptaptech.sis_springboot.model.Subject;
import com.fptaptech.sis_springboot.repository.StudentRepository;
import com.fptaptech.sis_springboot.repository.StudentScoreRepository;
import com.fptaptech.sis_springboot.repository.SubjectRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentInformationService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final StudentScoreRepository studentScoreRepository;

    public StudentInformationService(
            StudentRepository studentRepository,
            SubjectRepository subjectRepository,
            StudentScoreRepository studentScoreRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.studentScoreRepository = studentScoreRepository;
    }

    @Transactional(readOnly = true)
    public List<StudentRow> getStudentInformation() {
        List<Student> students = studentRepository.findAllByOrderByFullNameAsc();
        if (students.isEmpty()) {
            return List.of();
        }

        List<Integer> studentIds = students.stream().map(Student::getStudentId).toList();
        Map<Integer, List<ScoreRow>> scoresByStudent = studentScoreRepository.findAllForStudents(studentIds)
                .stream()
                .collect(Collectors.groupingBy(
                        score -> score.getStudent().getStudentId(),
                        Collectors.mapping(this::toScoreRow, Collectors.toList())));

        return students.stream()
                .map(student -> new StudentRow(
                        student.getStudentId(),
                        student.getStudentCode(),
                        student.getFullName(),
                        student.getAddress(),
                        scoresByStudent.getOrDefault(student.getStudentId(), List.of())))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Student> getStudents() {
        return studentRepository.findAllByOrderByFullNameAsc();
    }

    @Transactional(readOnly = true)
    public List<Subject> getSubjects() {
        return subjectRepository.findAllByOrderBySubjectNameAsc();
    }

    @Transactional
    public void addStudent(String studentCode, String fullName, String address) {
        if (studentRepository.existsByStudentCode(studentCode.trim())) {
            throw new DuplicateStudentCodeException();
        }
        studentRepository.save(new Student(studentCode.trim(), fullName.trim(), blankToNull(address)));
    }

    @Transactional
    public void addScore(Integer studentId, Integer subjectId, BigDecimal score1, BigDecimal score2) {
        if (studentScoreRepository.existsByStudentStudentIdAndSubjectSubjectId(studentId, subjectId)) {
            throw new DuplicateStudentScoreException();
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(InvalidStudentException::new);
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(InvalidSubjectException::new);
        studentScoreRepository.save(new StudentScore(student, subject, score1, score2));
    }

    private ScoreRow toScoreRow(StudentScore score) {
        BigDecimal weightedScore = GradeCalculator.weightedScore(score.getScore1(), score.getScore2());
        return new ScoreRow(
                score.getSubject().getSubjectCode(),
                score.getSubject().getSubjectName(),
                score.getSubject().getCredit(),
                score.getScore1(),
                score.getScore2(),
                weightedScore,
                GradeCalculator.grade(weightedScore));
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    public record StudentRow(
            Integer studentId,
            String studentCode,
            String fullName,
            String address,
            List<ScoreRow> scores) {
    }

    public record ScoreRow(
            String subjectCode,
            String subjectName,
            Integer credit,
            BigDecimal score1,
            BigDecimal score2,
            BigDecimal weightedScore,
            String grade) {
    }

    public static class DuplicateStudentCodeException extends RuntimeException {
    }

    public static class DuplicateStudentScoreException extends RuntimeException {
    }

    public static class InvalidStudentException extends RuntimeException {
    }

    public static class InvalidSubjectException extends RuntimeException {
    }
}
