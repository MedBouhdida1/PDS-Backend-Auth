package com.example.jwtauth.Service;

import com.example.jwtauth.DTO.stageDTO;
import com.example.jwtauth.models.Comment;
import com.example.jwtauth.models.Project;
import com.example.jwtauth.models.Stage;
import com.example.jwtauth.models.Task;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Service
public class ProjectService {

    private RestTemplate restTemplate;

    public ProjectService() {
        this.restTemplate = new RestTemplate();
    }


    public List<Project> getAllProjects() {
        String URI = "http://localhost:8080/api/v1/projects";
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

    public Project getProject(String projectId) {
        String URI = "http://localhost:8080/api/v1/projects/"+projectId;
        Project project =restTemplate.getForObject(URI,Project.class);
        return project;
    }

    public List<Stage> getStageByProject(String projectId) {
        String URI = "http://localhost:8080/api/v1/projects/"+projectId+"/stages";
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

    public Stage getStage(String stageId) {
        String URI = "http://localhost:8080/api/v1/projects/stages/"+stageId;
        Stage stage =restTemplate.getForObject(URI,Stage.class);
        return stage;
    }

    public List<Task> getTasksByStage(String stageId) {
        String URI = "http://localhost:8080/api/v1/projects/stages/"+stageId+"/tasks";
        ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Task>>() {
                }
        );

        // Extract the list of students from the response entity
        List<Task> tasks = responseEntity.getBody();
        return tasks;

    }

    public List<Task> getTasksByproject(String projectId) {
        String URI = "http://localhost:8080/api/v1/projects/"+projectId+"/tasks";
        ResponseEntity<List<Task>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Task>>() {
                }
        );

        // Extract the list of students from the response entity
        List<Task> tasks = responseEntity.getBody();
        return tasks;
    }

    public List<Comment> getCommentsByStage(String stageId) {
        String URI = "http://localhost:8080/api/v1/projects/stages/"+stageId+"/comments";
        ResponseEntity<List<Comment>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Comment>>() {
                }
        );

        // Extract the list of students from the response entity
        List<Comment> comments = responseEntity.getBody();
        return comments;
    }

    public String createStage(String projectId, stageDTO stageDto) {
        String apiUrl = "http://localhost:8080/api/v1/projects/"+projectId+"/stages";
        String response = restTemplate.postForObject(apiUrl, stageDto ,String.class);
        return response;
    }




    public String uploadDoc(String projectId, MultipartFile pdf) throws IOException {
        System.out.println("Received request for projectId: " + projectId);
        System.out.println("Received file with original name: " + pdf.getOriginalFilename());

        String apiUrl = "http://localhost:8080/api/v1/projects/" + projectId + "/document";

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Set up the request body as a MultiValueMap
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("pdf", pdf.getResource());

        // Create the request entity with headers and body
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Make the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        // Retrieve the response body
        String response = responseEntity.getBody();

        return response;
    }


    public InputStream downloadDoc(String projectId) {
        String apiUrl = "http://localhost:8080/api/v1/projects/"+projectId+"/document";
        // Set up headers
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        // Make the GET request with exchange
        ResponseEntity<InputStream> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<InputStream>() {},
                headers
        );

        // Extract the InputStream from the ResponseEntity
        InputStream inputStream = responseEntity.getBody();

        return inputStream;
    }

    public String deleteStage(String stageId) {
        String apiUrl = "http://localhost:8080/api/v1/projects/stages/"+stageId;
        return restTemplate.exchange(
                apiUrl,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }

    public String removeTaskById(String stageId, String taskId) {
        String apiUrl = "http://localhost:8080/api/v1/projects/stages/"+stageId+"/tasks/"+taskId;
        return restTemplate.exchange(
                apiUrl,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }

    public String removeTaskById2(String projectId, String taskId) {
        String apiUrl = "http://localhost:8080/api/v1/projects/"+projectId+"/tasks/"+taskId;
        return restTemplate.exchange(
                apiUrl,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }

    public String updateTask(String projectId, String taskId) {
        String apiUrl = "http://localhost:8080/api/v1/projects/"+projectId+"/tasks/"+taskId;
        // Make the PUT request with exchange
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.PUT,
                null,
                String.class
        );

        // Extract the response body from the ResponseEntity
        String response = responseEntity.getBody();
        return response;
    }

    public String validateDocument(String projectId) {
        String apiUrl = "http://localhost:8080/api/v1/projects/document/"+projectId;
        // Make the PUT request with exchange
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.PUT,
                null,
                String.class
        );

        // Extract the response body from the ResponseEntity
        String response = responseEntity.getBody();
        return response;
    }

    public String deleteProject(String projectId) {
        String apiUrl = "http://localhost:8080/api/v1/projects/"+projectId;
        return restTemplate.exchange(
                apiUrl,
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        ).getBody();
    }
}
