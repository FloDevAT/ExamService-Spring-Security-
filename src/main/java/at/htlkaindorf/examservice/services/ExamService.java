package at.htlkaindorf.examservice.services;

import at.htlkaindorf.examservice.entities.Exam;
import at.htlkaindorf.examservice.entities.ExamResult;
import at.htlkaindorf.examservice.repositories.ExamRepository;
import at.htlkaindorf.examservice.repositories.ExamResultRepository;
import at.htlkaindorf.examservice.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    private ExamRepository examRepository;
    private StudentRepository studentRepository;
    private ExamResultRepository examResultRepository;

    public ExamService(
            ExamRepository examRepository,
            StudentRepository studentRepository,
            ExamResultRepository examResultRepository
    ) {
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
        this.examResultRepository = examResultRepository;
    }

    /**
     * Loads the best result of an exam
     * @param examId the exam to check for
     * @return the best result
     */
    public ExamResult getBestResultOfExam(
            int examId
    ) {
        Exam exam = this.examRepository.getReferenceById(examId);

        if (exam == null) {
            return null;
        }

        Optional<ExamResult> bestResult =
                this.examResultRepository.getExamResultsByExam(exam)
                        .stream()
                        .max(Comparator.comparingInt(ExamResult::getReachedPoints)); // take the one with the most points

        return bestResult.orElse(null);
    }

    /**
     * Loads all titles of the exams
     * @return list of titles
     */
    public List<String> getAllExamTitles() {
        List<String> examNames = this.examRepository
                .findAll()
                .stream()
                .map(e -> e.getTitle())
                .toList();

        return examNames;
    }

}
