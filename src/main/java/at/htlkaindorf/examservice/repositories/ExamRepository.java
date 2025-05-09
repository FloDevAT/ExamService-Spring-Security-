package at.htlkaindorf.examservice.repositories;

import at.htlkaindorf.examservice.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to access the exam table from the database
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

}
