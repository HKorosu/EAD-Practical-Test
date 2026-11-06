package com.fptaptech.sis_springboot;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fptaptech.sis_springboot.model.Student;
import com.fptaptech.sis_springboot.model.Subject;
import com.fptaptech.sis_springboot.repository.StudentRepository;
import com.fptaptech.sis_springboot.repository.SubjectRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StudentWebTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    void rendersStudentListAndBothForms() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Nguyễn Văn A")))
                .andExpect(content().string(containsString("7.45")))
                .andExpect(content().string(containsString("grade-b")));

        mockMvc.perform(get("/students/new")).andExpect(status().isOk());
        mockMvc.perform(get("/scores/new")).andExpect(status().isOk());
    }

    @Test
    void addsStudentAndScoreFromWebsite() throws Exception {
        mockMvc.perform(post("/students")
                        .param("studentCode", "WEBTEST01")
                        .param("fullName", "Sinh viên kiểm thử")
                        .param("address", "Hà Nội"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Student student = studentRepository.findAll().stream()
                .filter(item -> item.getStudentCode().equals("WEBTEST01"))
                .findFirst()
                .orElseThrow();
        List<Subject> subjects = subjectRepository.findAll();
        Subject subject = subjects.stream()
                .filter(item -> item.getSubjectCode().equals("PHP"))
                .findFirst()
                .orElseThrow();

        mockMvc.perform(post("/scores")
                        .param("studentId", student.getStudentId().toString())
                        .param("subjectId", subject.getSubjectId().toString())
                        .param("score1", "8.00")
                        .param("score2", "7.00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sinh viên kiểm thử")))
                .andExpect(content().string(containsString("7.30")));
    }
}
