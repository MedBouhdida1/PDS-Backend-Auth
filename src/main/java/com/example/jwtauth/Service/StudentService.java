package com.example.jwtauth.Service;

import com.example.jwtauth.DAO.UserDAO;
import com.example.jwtauth.DTO.taskDTO;
import com.example.jwtauth.DTO.userDTO;
import com.example.jwtauth.Entity.User;
import com.example.jwtauth.Exceptions.ObjectNotFoundException;
import com.example.jwtauth.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class StudentService {

    private RestTemplate restTemplate;
    @Autowired
    private Environment environment;
    @Autowired
    private UserDAO userDAO;

    public StudentService() {
        this.restTemplate = new RestTemplate();
    }

    public Student getStudent(String id) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students/"+id;
        Student student =restTemplate.getForObject(URI,Student.class);
        return student;
    }

    public Student getStudentByName(String name) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students/"+name+"/name";
        Student student =restTemplate.getForObject(URI,Student.class);
        return student;
    }


    public List<Student> getAllStudent() {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students";
        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );

        // Extract the list of students from the response entity
        List<Student> students = responseEntity.getBody();
        return students;
    }
    public String updateStudent(String studentId, userDTO userDto) {
        String apiUrl = "http://localhost:8080/api/v1/students/"+studentId;

        User updatedUser = userDAO.findById(userDto.name)
                .orElseThrow(() -> new ObjectNotFoundException("Student not found"));
        updatedUser.setUserName(userDto.name);
        updatedUser.setUserEmail(userDto.email);
        userDAO.save(updatedUser);
        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HttpEntity with headers and the userDto as the request body
        HttpEntity<userDTO> requestEntity = new HttpEntity<>(userDto, headers);
        // Make the PUT request with exchange
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.PUT,
                requestEntity,
                String.class
        );

        // Extract the response body from the ResponseEntity
        String response = responseEntity.getBody();
        return response;
    }
    public String deleteStudent(String id) {
        String apiUrl = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students/id/"+id;
       userDAO.delete(userDAO.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Student not found")));
       userDAO.deleteById((id));
        return restTemplate.exchange(
                apiUrl,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }

    public String EnrollProject(String studentId, String projectId) {
        String apiUrl = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students/"+studentId+"/projects/"+projectId;
        String response = restTemplate.postForObject(apiUrl,null ,String.class);
        return response;
    }

    public String createTask(String studentId, String stageId, taskDTO taskDto) {
        String apiUrl = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students/"+studentId+"/stages/"+stageId+"/tasks";
        String response = restTemplate.postForObject(apiUrl, taskDto ,String.class);
        return response;
    }

    public Project getMyProject(String studentId) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students/"+studentId+"/project";
        Project project =restTemplate.getForObject(URI,Project.class);
        return project;
    }

    public Team getMyTeam(String studentId) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students/"+studentId+"/team";
        Team team =restTemplate.getForObject(URI,Team.class);
        return team;
    }

    public List<Task> getMyTasks(String studentId) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students/"+studentId+"/tasks";
        ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Task>>() {
                }
        );

        // Extract the list of students from the response entity
        List<Task> Tasks = responseEntity.getBody();
        return Tasks;
    }

    public List<Stage> getStages(String studentId) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students/"+studentId+"/stages";
        ResponseEntity<List<Stage>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Stage>>() {
                }
        );

        // Extract the list of students from the response entity
        List<Stage> stages = responseEntity.getBody();
        return stages;
    }

    public String leaveProject(String studentId, String projectId) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/students/"+studentId+"/projects/"+projectId;
        return restTemplate.exchange(
                URI,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }


}
