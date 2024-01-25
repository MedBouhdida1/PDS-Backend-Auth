package com.example.jwtauth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stage {

    @Id
    private String id;

    private String name;

    private List<Task> tasks;

    private List<Comment> comments;


    @JsonIgnoreProperties("stages")
    private Project project;

    public Stage(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public boolean checkTask(String title){
        for(Task task : this.tasks){
            if (task.getTitle().equals(title))
                    return true;
        }
        return false;
    }

    public void removeTask(String title){
        this.tasks.removeIf(task -> task.getTitle().equals(title));
    }

    public boolean checkTaskId(String Id){
        for(Task task : this.tasks){
            if (task.getId().equals(Id))
                return true;
        }
        return false;
    }
    public void removeTaskById(String Id){
        this.tasks.removeIf(task -> task.getId().equals(Id));
    }


    public void addComments(Comment comment){
        comments.add(comment);
    }

    public boolean checkCommentId(String commentId) {
        for(Comment comment : this.comments){
            if (comment.getId().equals(commentId))
                return true;
        }
        return false;
    }

    public void removeCommentById(String commentId) {
        this.comments.removeIf(task -> task.getId().equals(commentId));
    }
}