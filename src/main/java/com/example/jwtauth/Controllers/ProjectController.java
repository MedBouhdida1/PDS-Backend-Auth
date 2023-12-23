package com.example.jwtauth.Controllers;

import com.example.jwtauth.DTO.stageDTO;
import com.example.jwtauth.Service.ProjectService;
import com.example.jwtauth.models.Comment;
import com.example.jwtauth.models.Project;
import com.example.jwtauth.models.Stage;
import com.example.jwtauth.models.Task;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/projects")
public class    ProjectController {
    private final ProjectService projectService;


    @Operation(summary = "Get All Projects ")
    @GetMapping()
    @PreAuthorize("hasRole('Supervisor')")
    public List<Project> getProjects(){return projectService.getAllProjects();}

    @Operation(summary = "Get Project By Id")
    @GetMapping(value ="/{projectId}")
    @PreAuthorize("hasRole('Admin') OR hasRole('Supervisor') OR hasRole('Student')")
    public Project getProject(@PathVariable String projectId){return projectService.getProject(projectId);}

    //get stages by project
    @Operation(summary = "Get Stages by Project Id")
    @GetMapping(value ="/{projectId}/stages")
    @PreAuthorize("hasRole('Supervisor') OR hasRole('Student')")
    public List<Stage> getStagesProject(@PathVariable String projectId){return projectService.getStageByProject(projectId);}

    @Operation(summary = "Get Stages by Stage Id")
    @GetMapping(value ="/stages/{stagesId}")
    @PreAuthorize("hasRole('Supervisor') OR hasRole('Student')")
    public Stage getStage(@PathVariable String stageId){return projectService.getStage(stageId);}

//
    //get tasks by stage
    @Operation(summary = "Get Tasks by Stage Id")
    @GetMapping(value ="/stages/{stageId}/tasks")
    @PreAuthorize("hasRole('Supervisor') OR hasRole('Student')")
    public List<Task> getTasksStage(@PathVariable String stageId){return projectService.getTasksByStage(stageId);}

    //get tasks by project
    @Operation(summary = "Get Tasks by Project Id")
    @GetMapping(value ="/{projectId}/tasks")
    @PreAuthorize("hasRole('Supervisor') OR hasRole('Student')")
    public List<Task> getTasksProject(@PathVariable String projectId){return projectService.getTasksByproject(projectId);}

//
    @Operation(summary = "Get Comments by Stage Id")
    @GetMapping(value ="/stages/{stageId}/comments")
    @PreAuthorize("hasRole('Supervisor') OR hasRole('Student')")
    public List<Comment> getCommentsStage(@PathVariable String stageId){return projectService.getCommentsByStage(stageId);}

//
    // Add stage to project
    @Operation(summary = "Add stage to a Project")
    @PostMapping(value = "/{projectId}/stages")
    @PreAuthorize("hasRole('Student')")
    public String addStage(@PathVariable String projectId,@RequestBody stageDTO stageDto){return projectService.createStage(projectId,stageDto);}

//
    //Add a document
    @Operation(summary = "Upload specification book PDF")
    @PostMapping(value ="/{projectId}/document")
    @PreAuthorize("hasRole('Student')")
    public String uploadDoc(@PathVariable String projectId, @RequestParam("pdf") MultipartFile pdf) throws IOException {return projectService.uploadDoc(projectId,pdf);}

    @Operation(summary = "download specification book PDF")
    @GetMapping(value ="/{projectId}/document")
    @PreAuthorize("hasRole('Supervisor') OR hasRole('Student')")
    public InputStream downloadDoc(@PathVariable String projectId) throws IOException {return projectService.downloadDoc(projectId);}

//
    @Operation(summary = "Delete Stage")
    @DeleteMapping (value = "/stages/{stageId}")
    @PreAuthorize("hasRole('Student')")
    public String deleteStage(@PathVariable String stageId){return projectService.deleteStage(stageId);}

    // remove task by stageId
    @Operation(summary = "Remove a task with stageId ")
    @DeleteMapping(value ="/stages/{stageId}/tasks/{taskId}")
    @PreAuthorize("hasRole('Student')")
    public String removeTaskStageById(@PathVariable String stageId,@PathVariable String taskId){return projectService.removeTaskById(stageId,taskId);}

    // remove task by projectId
    @Operation(summary = "Remove a task with projectId ")
    @DeleteMapping(value ="/{projectId}/tasks/{taskId}")
    @PreAuthorize("hasRole('Student')")
    public String removeTaskProjectById(@PathVariable String projectId,@PathVariable String taskId){return projectService.removeTaskById2(projectId,taskId);}

    @Operation(summary = "Update task state")
    @PutMapping(value ="/{projectId}/tasks/{taskId}")
    @PreAuthorize("hasRole('Student')")
    public String updateTaskState(@PathVariable String projectId,@PathVariable String taskId){return projectService.updateTask(projectId,taskId);}


    // Delete a project
    @Operation(summary = "Delete Project")
    @DeleteMapping (value = "/{projectId}")
    @PreAuthorize("hasRole('Admin') OR hasRole('Supervisor')")
    public String deleteProject(@PathVariable String projectId){return projectService.deleteProject(projectId);}

}
