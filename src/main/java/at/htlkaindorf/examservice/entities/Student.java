package at.htlkaindorf.examservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Class to represent a student in the database
 */
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    // this line stops the password from being leaked in the output written by an ObjectMapper
    // there is a workaround by just using a DTO, however, I am way too lazy for that ;)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd") // the date defined in the json file
    private LocalDate birthdate;
}
