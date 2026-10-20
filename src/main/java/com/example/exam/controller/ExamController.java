package com.example.exam.controller;

import com.example.exam.model.*;
import com.example.exam.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ExamAnswerRepository examAnswerRepository;

    // Display the exam page
    @GetMapping("/{examId}")
    public String getExamPage(
            @PathVariable Long examId,
            Model model) {

        // Get the logged-in student
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();

        User student = userRepository.findByUsername(currentUsername)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        model.addAttribute("student", student);

        // Get the exam
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid exam Id: " + examId));

        model.addAttribute("exam", exam);

        // Load exam questions
        List<Question> questions = exam.getQuestions();
        questions.size();

        model.addAttribute("questions", questions);

        return "student/exam_page";
    }

    // Submit the exam
    @PostMapping("/submit")
    public String submitExam(
            @RequestParam Long examId,
            @RequestParam Map<String, String> submittedAnswers,
            RedirectAttributes redirectAttributes) {

        // Get the logged-in student
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();

        User student = userRepository.findByUsername(currentUsername)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Get the exam
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid exam Id: " + examId));

        int totalMarks = 0;
        int scoreAchieved = 0;

        // Create the result
        ExamResult result = new ExamResult();

        result.setStudent(student);
        result.setExam(exam);

        // Record the submission date and time
        result.setSubmissionTime(LocalDateTime.now());

        // Save the result to generate its ID
        examResultRepository.save(result);

        // Process each question
        for (Question question : exam.getQuestions()) {

            totalMarks += question.getMarks();

            String answerKey = "q_" + question.getId();

            int selectedOption = 0;

            if (submittedAnswers.containsKey(answerKey)) {
                try {
                    selectedOption = Integer.parseInt(
                            submittedAnswers.get(answerKey));

                } catch (NumberFormatException e) {
                    selectedOption = 0;
                }

                // Calculate the score
                if (selectedOption == question.getCorrectAnswer()) {
                    scoreAchieved += question.getMarks();
                }
            }

            // Save the student's answer
            ExamAnswer examAnswer =
                    new ExamAnswer(result, question, selectedOption);

            examAnswerRepository.save(examAnswer);
            result.addAnswer(examAnswer);
        }

        // Update and save the final result
        result.setScoreAchieved(scoreAchieved);
        result.setTotalMarks(totalMarks);

        examResultRepository.save(result);

        // Send score information to the result page
        redirectAttributes.addFlashAttribute(
                "score", scoreAchieved);

        redirectAttributes.addFlashAttribute(
                "total", totalMarks);

        redirectAttributes.addFlashAttribute(
                "resultId", result.getId());

        return "redirect:/student/result";
    }
}