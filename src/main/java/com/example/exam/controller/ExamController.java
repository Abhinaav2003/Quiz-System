package com.example.exam.controller;

import com.example.exam.model.*;
import com.example.exam.repository.*;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    @GetMapping("/{examId}")
    public String getExamPage(
            @PathVariable Long examId,
            Model model,
            HttpSession session) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();

        User student = userRepository.findByUsername(currentUsername)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        model.addAttribute("student", student);

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid exam Id: " + examId));

        model.addAttribute("exam", exam);

        List<Question> questions = exam.getQuestions();
        questions.size();

        model.addAttribute("questions", questions);

        // Record the start time for this exam in the current session.
        @SuppressWarnings("unchecked")
        Map<Long, LocalDateTime> startTimes =
                (Map<Long, LocalDateTime>)
                        session.getAttribute("examStartTimes");

        if (startTimes == null) {
            startTimes = new HashMap<>();
            session.setAttribute("examStartTimes", startTimes);
        }

        // Set the start time only if this exam has not already started.
        startTimes.putIfAbsent(examId, LocalDateTime.now());

        return "student/exam_page";
    }

    @PostMapping("/submit")
    public String submitExam(
            @RequestParam Long examId,
            @RequestParam Map<String, String> submittedAnswers,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();

        User student = userRepository.findByUsername(currentUsername)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid exam Id: " + examId));

        // Retrieve the start time recorded when the exam page opened.
        @SuppressWarnings("unchecked")
        Map<Long, LocalDateTime> startTimes =
                (Map<Long, LocalDateTime>)
                        session.getAttribute("examStartTimes");

        LocalDateTime submissionTime = LocalDateTime.now();
        LocalDateTime startTime = null;

        if (startTimes != null) {
            startTime = startTimes.remove(examId);
        }

        // Prevent a negative duration if the start time is unavailable.
        long durationSeconds = startTime == null
                ? 0L
                : Math.max(0L,
                    Duration.between(startTime, submissionTime).getSeconds());

        int totalMarks = 0;
        int scoreAchieved = 0;

        ExamResult result = new ExamResult();

        result.setStudent(student);
        result.setExam(exam);
        result.setStartTime(startTime);
        result.setSubmissionTime(submissionTime);
        result.setDurationSeconds(durationSeconds);

        examResultRepository.save(result);

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

                if (selectedOption == question.getCorrectAnswer()) {
                    scoreAchieved += question.getMarks();
                }
            }

            ExamAnswer examAnswer =
                    new ExamAnswer(result, question, selectedOption);

            examAnswerRepository.save(examAnswer);
            result.addAnswer(examAnswer);
        }

        result.setScoreAchieved(scoreAchieved);
        result.setTotalMarks(totalMarks);

        examResultRepository.save(result);

        redirectAttributes.addFlashAttribute("score", scoreAchieved);
        redirectAttributes.addFlashAttribute("total", totalMarks);
        redirectAttributes.addFlashAttribute("resultId", result.getId());

        return "redirect:/student/result";
    }
}