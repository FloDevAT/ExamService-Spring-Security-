package at.htlkaindorf.examservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Class to represent an ExamResult in the database
 */
@Data
@Entity
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int reachedPoints;

    @ManyToOne(
            targetEntity = Student.class // not required but a nice practice
    )
    private Student student;

    @ManyToOne(
            targetEntity = Exam.class // not required but a nice practice
    )
    @JsonBackReference // required for loading arrays form the json file
    private Exam exam;
}


