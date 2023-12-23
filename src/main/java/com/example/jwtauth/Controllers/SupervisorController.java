package com.example.jwtauth.Controllers;

import com.example.jwtauth.DTO.TeamDTO;
import com.example.jwtauth.DTO.commentDTO;
import com.example.jwtauth.DTO.projectDTO;
import com.example.jwtauth.DTO.userDTO;
import com.example.jwtauth.Service.SupervisorService;
import com.example.jwtauth.models.Project;
import com.example.jwtauth.models.Supervisor;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/supervisors")
public class SupervisorController {
    private final SupervisorService supervisorService;

    @Operation(summary = "Get All Supervisors")
    @GetMapping()
    @PreAuthorize("hasRole('Admin')")
    public List<Supervisor> getAllSupervisors(){return supervisorService.getAllSupervisors();}

    @Operation(summary = "Get Supervisor By Id")
    @GetMapping(value ="/{Id}")
    @PreAuthorize("hasRole('Supervisor')")
    public Supervisor getSupervisor(@PathVariable String Id){return supervisorService.getSupervisor(Id);}


    @Operation(summary = "Get Supervisor By name")
    @GetMapping(value ="/{name}/name")
    @PreAuthorize("hasRole('Supervisor')")
    public Supervisor getSupervisorByName(@PathVariable String name){return supervisorService.getSupervisorByName(name);}

    @Operation(summary = "Update Supervisor")
    @PutMapping(value = "/{supervisorId}")
    @PreAuthorize("hasRole('Supervisor')")
    public String updateSupervisor(@PathVariable String supervisorId,@RequestBody userDTO userDto){return supervisorService.updateSupervisor(supervisorId,userDto);}

    @Operation(summary = "Delete Supervisor By Name")
    @DeleteMapping(value ="/{name}/name")
    @PreAuthorize("hasRole('Supervisor')")
    public String removeSupervisor(@PathVariable String name){return supervisorService.deleteSupervisor(name);}

    //create project
    @Operation(summary = "Add Project")
    @PostMapping(value ="/{Id}/projects")
    @PreAuthorize("hasRole('Supervisor')")
    public String addProject(@PathVariable String Id,@RequestBody projectDTO projectDto){return supervisorService.AddProject(Id,projectDto);}

    // add comment to stage /only a supervisor can add comment
    @Operation(summary = "Add comment to stage / only a supervisor can add comments")
    @PostMapping(value ="/{supervisorId}/stages/{stageId}/comments")
    @PreAuthorize("hasRole('Supervisor')")
    public String addComment(@PathVariable String supervisorId,@PathVariable String stageId, @RequestBody commentDTO commentDto){return supervisorService.addComment(supervisorId,stageId,commentDto);}

    // view his teams
    @Operation(summary = "View My teams")
    @GetMapping(value ="/{Id}/teams")
    @PreAuthorize("hasRole('Supervisor')")
    public List<TeamDTO> getSupervisorTeams(@PathVariable String Id){return supervisorService.getMyTeams(Id);}

    // view his projects
    @Operation(summary = "View My Projects")
    @GetMapping(value ="/{Id}/projects")
    @PreAuthorize("hasRole('Supervisor')")
    public List<Project> getSupervisorProjects(@PathVariable String Id){return supervisorService.getMyProjects(Id);}

    // Delete comment
    @Operation(summary = "Delete comment with Id")
    @DeleteMapping(value ="/stages/{stageId}/comments/{commentId}")
    @PreAuthorize("hasRole('Admin') OR hasRole('Supervisor')")
    public String removeComment(@PathVariable String stageId, @PathVariable String commentId){return supervisorService.removeComment(stageId,commentId);}

    @Operation(summary = "Delete project with Id")
    @DeleteMapping(value ="/{supervisorId}/projects/{projectId}")
    @PreAuthorize("hasRole('Admin') OR hasRole('Supervisor')")
    public String removeProject(@PathVariable String supervisorId, @PathVariable String projectId){return supervisorService.removeProject(supervisorId,projectId);}

}
