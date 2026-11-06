package com.fptaptech.sis_springboot.web;

import com.fptaptech.sis_springboot.service.StudentInformationService;
import com.fptaptech.sis_springboot.service.StudentInformationService.DuplicateStudentCodeException;
import com.fptaptech.sis_springboot.service.StudentInformationService.DuplicateStudentScoreException;
import com.fptaptech.sis_springboot.service.StudentInformationService.InvalidStudentException;
import com.fptaptech.sis_springboot.service.StudentInformationService.InvalidSubjectException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

    private final StudentInformationService studentInformationService;

    public StudentController(StudentInformationService studentInformationService) {
        this.studentInformationService = studentInformationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("students", studentInformationService.getStudentInformation());
        model.addAttribute("studentCount", studentInformationService.getStudents().size());
        model.addAttribute("subjectCount", studentInformationService.getSubjects().size());
        return "index";
    }

    @GetMapping("/students/new")
    public String newStudent(Model model) {
        model.addAttribute("studentForm", new StudentForm());
        return "student-form";
    }

    @PostMapping("/students")
    public String createStudent(
            @Valid @ModelAttribute("studentForm") StudentForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "student-form";
        }

        try {
            studentInformationService.addStudent(form.getStudentCode(), form.getFullName(), form.getAddress());
        } catch (DuplicateStudentCodeException exception) {
            bindingResult.rejectValue("studentCode", "duplicate", "Mã sinh viên này đã tồn tại.");
            return "student-form";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Đã thêm sinh viên thành công.");
        return "redirect:/";
    }

    @GetMapping("/scores/new")
    public String newScore(Model model) {
        prepareScoreForm(model);
        return "score-form";
    }

    @PostMapping("/scores")
    public String createScore(
            @Valid @ModelAttribute("scoreForm") ScoreForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            prepareChoices(model);
            return "score-form";
        }

        try {
            studentInformationService.addScore(
                    form.getStudentId(), form.getSubjectId(), form.getScore1(), form.getScore2());
        } catch (DuplicateStudentScoreException exception) {
            bindingResult.rejectValue("subjectId", "duplicate", "Sinh viên đã có điểm môn học này.");
            prepareChoices(model);
            return "score-form";
        } catch (InvalidStudentException exception) {
            bindingResult.rejectValue("studentId", "invalid", "Sinh viên đã chọn không tồn tại.");
            prepareChoices(model);
            return "score-form";
        } catch (InvalidSubjectException exception) {
            bindingResult.rejectValue("subjectId", "invalid", "Môn học đã chọn không tồn tại.");
            prepareChoices(model);
            return "score-form";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Đã thêm điểm thành công.");
        return "redirect:/";
    }

    private void prepareScoreForm(Model model) {
        model.addAttribute("scoreForm", new ScoreForm());
        prepareChoices(model);
    }

    private void prepareChoices(Model model) {
        model.addAttribute("students", studentInformationService.getStudents());
        model.addAttribute("subjects", studentInformationService.getSubjects());
    }
}
