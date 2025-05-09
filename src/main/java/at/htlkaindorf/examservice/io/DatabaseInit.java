package at.htlkaindorf.examservice.io;

import at.htlkaindorf.examservice.entities.Exam;
import at.htlkaindorf.examservice.entities.Student;
import at.htlkaindorf.examservice.repositories.ExamRepository;
import at.htlkaindorf.examservice.repositories.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class DatabaseInit implements ApplicationRunner  {

    // syntax: classpath:PATH_IN_RESOURCES_DIRECTORY
    @Value("classpath:data/students.json")
    private Resource studentResource;

    @Value("classpath:data/exams.json")
    private Resource examResource;

    private ObjectMapper mapper;
    private PasswordEncoder passwordEncoder;
    private StudentRepository studentRepository;
    private ExamRepository examRepository;

    public DatabaseInit(
            ObjectMapper mapper,
            PasswordEncoder passwordEncoder,
            StudentRepository studentRepository,
            ExamRepository examRepository
    ) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
    }

    @Override
    // loads all the json files and saves them to the database
    // after each bean has been initialized
    public void run(ApplicationArguments args) throws Exception {
        List<Student> students = this.loadAllStudents();
        List<Student> savedStudents = this.studentRepository.saveAll(students);

        List<Exam> exams = this.loadAllExams();

        // replace the student attribute which only holds the name of the student
        // with a database "approved" student
        exams.forEach(e -> {
            e.getResults().forEach(r -> {
                Optional<Student> student = savedStudents
                        .stream()
                        .filter(s -> s.getName().equals(r.getStudent().getName()))
                        .findFirst();

                student.ifPresent(r::setStudent);
            });
        });

        this.examRepository.saveAll(exams);
    }

    /**
     * Function to load all the students from the students.json file.
     * Also replaces the password attribute with a corresponding bcrypt hash
     * @return list of students
     * @throws IOException
     */
    private List<Student> loadAllStudents() throws IOException {
        File file = this.studentResource.getFile();

        List<Student> students = this.mapper
                .readerForListOf(Student.class)
                .readValue(file);

        students.forEach(s -> {
            s.setPassword(
                    this.passwordEncoder.encode(s.getPassword())
            );
        });

        return students;
    }

    /**
     * Function to load all the exams (+ results) from the exams.json file.
     * @return list of exams
     * @throws IOException
     */
    private List<Exam> loadAllExams() throws IOException {
        File file = this.examResource.getFile();

        List<Exam> exams = this.mapper
                .readerForListOf(Exam.class)
                .readValue(file);

        return exams;
    }

}
