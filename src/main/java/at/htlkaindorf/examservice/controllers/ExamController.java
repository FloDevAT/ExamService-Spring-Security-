package at.htlkaindorf.examservice.controllers;

import at.htlkaindorf.examservice.entities.ExamResult;
import at.htlkaindorf.examservice.services.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


/**
 * a controller with routes that provide the user with exam information
 * this route cannot be accessed without proper authorization (SecurityConfiguration.java:41)
 */
@RestController
@RequestMapping("exams")
public class ExamController {

    private ExamService examService;

    public ExamController(
            ExamService examService
    ) {
        this.examService = examService;
    }

    /**
     * Endpoint to prove the best result for an exam
     * @param examId the id of the exam
     * @return the exam result with the highest score
     */
    @GetMapping("getBestResult/{examId}")
    public ResponseEntity<ExamResult> test(
            @PathVariable("examId") int examId
    ) {
        ExamResult result = this.examService.getBestResultOfExam(examId);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Endpoint to get the titles of all exam names
     * @return list of all exam titles
     */
    @GetMapping("getExamTitles")
    public ResponseEntity<String> getExamTitles() {
        List<String> titles = this.examService.getAllExamTitles();

        return ResponseEntity.ok(
                titles.stream().collect(Collectors.joining("\n")) // display them with a '\n' between them
        );
    }

}
