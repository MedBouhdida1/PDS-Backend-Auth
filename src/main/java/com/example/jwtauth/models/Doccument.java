package com.example.jwtauth.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Doccument {
    @Id
    private String Id;
    private String status="pending";
    private String title;
    private Binary doc;

    public Doccument() {
        this.Id = UUID.randomUUID().toString();
    }
}
