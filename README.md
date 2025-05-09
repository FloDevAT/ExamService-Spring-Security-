# ExamService

## Disclaimer

This project only serves the purpose to prepare me and other students of HTBLA Kaindorf for the upcoming "Schriftliche Reifeprüfung Fachklausur".
I am certainly aware that this code does not provide a reference for state-of-the-art Spring Security implementations and includes several
questionable design decisions!

## Description

This application manages several exams and their results. It loads the students (with their passwords) from a json file. 
Also, the exams (+ their results) can be found in a json file. They get loaded into the database (if you want to try it out you can just use the database configuration which can be found in the docker-compose). 
Also the app implement JWT authentication using Spring Security. 

## Endpoint Description

The application provides a REST-Api with several endpoints which are defined as follows:

`POST /public/sigin`

Expected Payload:
```json
{
    "username": "Anna Schmidt",
    "password": "Anna5184!"
}
```

Example Output:
```
eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJodHRwczovL3d3dy5odGwta2FpbmRvcmYuYWMuYXQiLCJzdWIiOiJBbm5hIFNjaG1pZHQiLCJpYXQiOjE3NDY4MTk5NjIsImV4cCI6MTc0NjgyMTc2Mn0.uTg7f3ktEsjinUKCsAS76P6cOsUlEzalgeg8dtsXyjqHU42TEIjNqEy9Dp4YHqDDjurq6lM7elqUU_3yT4k2Mg
```
----------

`GET getBestResult/{examId}`

Example Output:

```json
{
    "id": 4,
    "reachedPoints": 50,
    "student": {
        "id": 17,
        "name": "Julia Fischer",
        "birthdate": "2005-05-19"
    }
}
```

-----

`GET /exams/getExamTitles`

Example Output:

```
Final Maths Test
Physics Midterm
History Quiz
Biology Final
Chemistry Lab Report
English Oral Exam
Computer Science Project
```



