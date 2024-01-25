package com.example.jwtauth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
public class Project {

    @Id
    private String id;

    private String title;
    private String description;
    private String timeline;
    @JsonIgnoreProperties({"doc"})
    private Doccument document;


    @JsonIgnoreProperties({"projects"})
    private Supervisor supervisorId; // Reference to Supervisor

    private Team team;

    private List<Stage> stages;

    public Project(String title, String description, String timeline, Team team) {
        this.title = title;
        this.description = description;
        this.timeline = timeline;
        this.team = team;
        this.stages = new ArrayList<>();
    }

    public void addStage(Stage stage){
        stages.add(stage);
    }



}
