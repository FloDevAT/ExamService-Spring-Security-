package at.htlkaindorf.examservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Class to represent an exam in the database
 */
@Data
@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private int maximumPoints;

    @OneToMany(
            mappedBy = "exam", // the relation should be visible using the 'exam' property of the ExamResult
            cascade = CascadeType.ALL, // required for the initilization
            targetEntity = ExamResult.class // not required but a nice practice
    )
    @JsonManagedReference // required for loading arrays form the json file
    private List<ExamResult> results;
}
