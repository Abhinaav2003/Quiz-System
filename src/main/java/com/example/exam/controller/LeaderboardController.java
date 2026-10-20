package com.example.exam.controller;

import com.example.exam.model.*;
import com.example.exam.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Uses the existing ExamResultRepository.findByExam(exam), so the repository needs no change.
 *
 * Put leaderboard.html in src/main/resources/templates/.
 * If you keep your student pages in a folder, change the return value below
 * to match, for example "student/leaderboard".
 */
@Controller
public class LeaderboardController {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamResultRepository examResultRepository;

    /** One line of the leaderboard. Public fields so Thymeleaf can read them directly. */
    public static class Row {
        public final int rank;
        public final String name;
        public final String profilePicUrl;
        public final int score;
        public final int total;
        public final double percentage;
        public final boolean me;

        Row(int rank, String name, String profilePicUrl, int score, int total, double percentage, boolean me) {
            this.rank = rank;
            this.name = name;
            this.profilePicUrl = profilePicUrl;
            this.score = score;
            this.total = total;
            this.percentage = percentage;
            this.me = me;
        }
    }

    @GetMapping("/leaderboard/{examId}")
    public String leaderboard(@PathVariable Long examId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exam Id:" + examId));

        // Best order first: highest percentage, and if tied, the earlier submission
        Comparator<ExamResult> best = Comparator
                .comparingDouble((ExamResult r) -> percentage(r)).reversed()
                .thenComparing(ExamResult::getSubmissionTime);

        // If a student took the exam more than once, only their best attempt counts
        Map<String, ExamResult> bestPerStudent = new LinkedHashMap<>();
        for (ExamResult r : examResultRepository.findByExam(exam)) {
            String key = r.getStudent().getUsername();
            ExamResult current = bestPerStudent.get(key);
            if (current == null || best.compare(r, current) < 0) {
                bestPerStudent.put(key, r);
            }
        }

        List<ExamResult> results = new ArrayList<>(bestPerStudent.values());
        results.sort(best);

        List<Row> rows = new ArrayList<>();
        Integer myRank = null;
        for (int i = 0; i < results.size(); i++) {
            ExamResult r = results.get(i);
            User student = r.getStudent();
            boolean me = student.getUsername().equals(currentUsername);
            if (me) myRank = i + 1;
            rows.add(new Row(i + 1, student.getFullName(), student.getProfilePicUrl(),
                    r.getScoreAchieved(), r.getTotalMarks(), percentage(r), me));
        }

        int podiumSize = Math.min(3, rows.size());
        model.addAttribute("exam", exam);
        model.addAttribute("podium", rows.subList(0, podiumSize));              // ranks 1 to 3
        model.addAttribute("others", rows.subList(podiumSize, rows.size()));    // rank 4 and below
        model.addAttribute("myRank", myRank);                                   // null if you have not taken it
        model.addAttribute("participants", rows.size());
        return "leaderboard";
    }

    private double percentage(ExamResult r) {
        return r.getTotalMarks() == 0 ? 0 : (r.getScoreAchieved() * 100.0) / r.getTotalMarks();
    }
}