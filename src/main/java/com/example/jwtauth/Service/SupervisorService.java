package com.example.jwtauth.Service;

import com.example.jwtauth.DTO.TeamDTO;
import com.example.jwtauth.DTO.commentDTO;
import com.example.jwtauth.DTO.projectDTO;
import com.example.jwtauth.models.Project;
import com.example.jwtauth.models.Supervisor;
import com.example.jwtauth.models.Task;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class SupervisorService {

    private RestTemplate restTemplate;

    public SupervisorService() {
        this.restTemplate = new RestTemplate();
    }

    public Supervisor getSupervisor(String id) {
        String URI = "http://localhost:8080/api/v1/supervisors/"+id;
        Supervisor supervisor =restTemplate.getForObject(URI,Supervisor.class);
        return supervisor;
    }

    public List<Supervisor> getAllSupervisors() {
        String URI = "http://localhost:8080/api/v1/supervisors";
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
        String apiUrl = "http://localhost:8080/api/v1/supervisors/"+Id+"/projects";
        String response = restTemplate.postForObject(apiUrl, projectDto ,String.class);
        return response;
    }

    public String deleteSupervisor(String Id) {
        String apiUrl = "http://localhost:8080/api/v1/supervisors/"+Id;
        return restTemplate.exchange(
                apiUrl,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }

    public String addComment(String supervisorId, String stageId, commentDTO commentDto) {
        String apiUrl = "http://localhost:8080/api/v1/supervisors/"+supervisorId+"/stages/"+stageId+"/comments";
        String response = restTemplate.postForObject(apiUrl, commentDto ,String.class);
        return response;
    }

    public List<TeamDTO> getMyTeams(String Id) {
        String URI = "http://localhost:8080/api/v1/supervisors/"+Id+"/teams";
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
        String URI = "http://localhost:8080/api/v1/supervisors/"+Id+"/projects";
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
        String URI = "http://localhost:8080/api/v1/supervisors/stages/"+stageId+"/comments/"+commentId;
        return restTemplate.exchange(
                URI,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }

    public String removeProject(String supervisorId, String projectId) {
        String URI = "http://localhost:8080/api/v1/supervisors/"+supervisorId+"/projects/"+projectId;
        return restTemplate.exchange(
                URI,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }
}