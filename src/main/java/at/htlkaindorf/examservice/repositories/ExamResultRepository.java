package at.htlkaindorf.examservice.repositories;

import at.htlkaindorf.examservice.entities.Exam;
import at.htlkaindorf.examservice.entities.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to access the exam_result table from the database
 */
@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Integer> {
    List<ExamResult> getExamResultsByExam(Exam exam);

}
