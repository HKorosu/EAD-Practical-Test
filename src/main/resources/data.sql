INSERT INTO subject_t (subject_code, subject_name, credit)
SELECT 'JAVA', 'Java Programming', 4
WHERE NOT EXISTS (SELECT 1 FROM subject_t WHERE subject_code = 'JAVA');

INSERT INTO subject_t (subject_code, subject_name, credit)
SELECT 'PHP', 'PHP Programming', 3
WHERE NOT EXISTS (SELECT 1 FROM subject_t WHERE subject_code = 'PHP');

INSERT INTO subject_t (subject_code, subject_name, credit)
SELECT 'WDA', 'Web Development and Applications', 3
WHERE NOT EXISTS (SELECT 1 FROM subject_t WHERE subject_code = 'WDA');

INSERT INTO student_t (student_code, full_name, address)
SELECT '2007A10', 'Nguyễn Văn A', 'Hà Nội'
WHERE NOT EXISTS (SELECT 1 FROM student_t WHERE student_code = '2007A10');

INSERT INTO student_score_t (student_id, subject_id, score1, score2)
SELECT student.student_id, subject.subject_id, 8.5, 7.0
FROM student_t student
JOIN subject_t subject ON subject.subject_code = 'JAVA'
WHERE student.student_code = '2007A10'
  AND NOT EXISTS (
      SELECT 1 FROM student_score_t score
      WHERE score.student_id = student.student_id AND score.subject_id = subject.subject_id
  );
