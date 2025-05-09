package at.htlkaindorf.examservice.repositories;

import at.htlkaindorf.examservice.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository to access the student table from the database
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findStudentByName(String name);
}
