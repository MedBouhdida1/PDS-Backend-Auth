package com.example.jwtauth.Controllers;

import com.example.jwtauth.DTO.taskDTO;
import com.example.jwtauth.DTO.userDTO;
import com.example.jwtauth.Service.StudentService;
import com.example.jwtauth.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @Operation(summary = "Get Student By Id")
    @GetMapping(value ="/{studentId}")
    @PreAuthorize("hasRole('Student')")
    public Student getStudent(@PathVariable String studentId){return studentService.getStudent(studentId);}

    @Operation(summary = "Get Student By name")
    @GetMapping(value ="/{name}/name")
    @PreAuthorize("hasRole('Student')")
    public Student getStudentByName(@PathVariable String name){return studentService.getStudentByName(name);}

    @Operation(summary = "Get All Students")
    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public List<Student> listStudents(){return studentService.getAllStudent();}

    @Operation(summary = "Delete a student By Id")
    @DeleteMapping(value ="{id}")
    @PreAuthorize("hasRole('Student') OR hasRole('Admin')")
    public String removeStudent(@PathVariable String id){return studentService.deleteStudent(id);}

    @Operation(summary = "Update Student")
    @PutMapping(value = "/{studentId}")
    @PreAuthorize("hasRole('Student')")
    public String updateStudent(@PathVariable String studentId,@RequestBody userDTO userDto){return studentService.updateStudent(studentId,userDto);}

    // Enroll in project
    @Operation(summary = "Enroll in Project")
    @PostMapping(value ="/{studentId}/projects/{projectId}")
    @PreAuthorize("hasRole('Student')")
    public String enrollInProject(@PathVariable String studentId,@PathVariable String projectId) {return studentService.EnrollProject(studentId,projectId);}

    // Add task to stage
    @Operation(summary = "Add Task to stage / only students can add tasks")
    @PostMapping(value ="/{studentId}/stages/{stageId}/tasks")
    @PreAuthorize("hasRole('Student')")
    public String addTaskStage(@PathVariable String studentId,@PathVariable String stageId,@RequestBody taskDTO taskDto) {return studentService.createTask(studentId,stageId,taskDto);}

//

    @Operation(summary = "View My Project")
    @GetMapping(value ="/{studentId}/project")
    @PreAuthorize("hasRole('Student')")
    public Project getStudentProject(@PathVariable String studentId){return studentService.getMyProject(studentId);}

    @Operation(summary = "View My Team")
    @GetMapping(value ="/{studentId}/team")
    @PreAuthorize("hasRole('Student')")
    public Team getStudentTeam(@PathVariable String studentId){return studentService.getMyTeam(studentId);}

    @Operation(summary = "View My Tasks")
    @GetMapping(value ="/{studentId}/tasks")
    @PreAuthorize("hasRole('Student')")
    public List<Task> getStudentTasks(@PathVariable String studentId){return studentService.getMyTasks(studentId);}

    @Operation(summary = "View The Stages")
    @GetMapping(value ="/{studentId}/stages")
    @PreAuthorize("hasRole('Student')")
    public List<Stage> getStudentStages(@PathVariable String studentId){return studentService.getStages(studentId);}

//
    // leave team-project
    @Operation(summary = "Leaves the project or team")
    @DeleteMapping(value = "/{studentId}/projects/{projectId}")
    @PreAuthorize("hasRole('Student')")
    public String leaveMyProject(@PathVariable String studentId,@PathVariable String projectId) {return studentService.leaveProject(studentId,projectId);}




}
