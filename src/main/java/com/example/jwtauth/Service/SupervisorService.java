package com.example.jwtauth.Service;

import com.example.jwtauth.DAO.UserDAO;
import com.example.jwtauth.DTO.TeamDTO;
import com.example.jwtauth.DTO.commentDTO;
import com.example.jwtauth.DTO.projectDTO;
import com.example.jwtauth.DTO.userDTO;
import com.example.jwtauth.Entity.User;
import com.example.jwtauth.Exceptions.ObjectNotFoundException;
import com.example.jwtauth.models.Project;
import com.example.jwtauth.models.Supervisor;
import com.example.jwtauth.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class SupervisorService {

    private RestTemplate restTemplate;
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Environment environment;

    public SupervisorService() {
        this.restTemplate = new RestTemplate();
    }

    public Supervisor getSupervisor(String id) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/supervisors/"+id;
        Supervisor supervisor =restTemplate.getForObject(URI,Supervisor.class);
        return supervisor;
    }

    public Supervisor getSupervisorByName(String name) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/supervisors/"+name+"/name";
        Supervisor supervisor =restTemplate.getForObject(URI,Supervisor.class);
        return supervisor;
    }


    public List<Supervisor> getAllSupervisors() {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/supervisors";
        ResponseEntity<List<Supervisor>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Supervisor>>() {
                }
        );

        // Extract the list of students from the response entity
        List<Supervisor> supervisors = responseEntity.getBody();
        return supervisors;
    }


    public String AddProject(String Id, projectDTO projectDto) {
        String apiUrl = "http://"+environment.getProperty("ip.address")+":8080/api/v1/supervisors/"+Id+"/projects";
        String response = restTemplate.postForObject(apiUrl, projectDto ,String.class);
        return response;
    }

    public String deleteSupervisor(String id) {
        String apiUrl = "http://"+environment.getProperty("ip.address")+":8080/api/v1/supervisors/id/"+id;
        userDAO.delete(userDAO.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Supervisor not found")));
        return restTemplate.exchange(
                apiUrl,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }
    public String updateSupervisor(String supervisorId, userDTO userDto) {
        String apiUrl = "http://localhost:8080/api/v1/supervisors/"+supervisorId;

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
    public String addComment(String supervisorId, String stageId, commentDTO commentDto) {
        String apiUrl = "http://"+environment.getProperty("ip.address")+":8080/api/v1/supervisors/"+supervisorId+"/stages/"+stageId+"/comments";
        String response = restTemplate.postForObject(apiUrl, commentDto ,String.class);
        return response;
    }

    public List<TeamDTO> getMyTeams(String Id) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/supervisors/"+Id+"/teams";
        ResponseEntity<List<TeamDTO>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TeamDTO>>() {
                }
        );

        // Extract the list of students from the response entity
        List<TeamDTO> teams = responseEntity.getBody();
        return teams;
    }

    public List<Project> getMyProjects(String Id) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/supervisors/"+Id+"/projects";
        ResponseEntity<List<Project>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Project>>() {
                }
        );

        // Extract the list of students from the response entity
        List<Project> projects = responseEntity.getBody();
        return projects;
    }

    public String removeComment(String stageId, String commentId) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/supervisors/stages/"+stageId+"/comments/"+commentId;
        return restTemplate.exchange(
                URI,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }

    public String removeProject(String supervisorId, String projectId) {
        String URI = "http://"+environment.getProperty("ip.address")+":8080/api/v1/supervisors/"+supervisorId+"/projects/"+projectId;
        return restTemplate.exchange(
                URI,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }


}
