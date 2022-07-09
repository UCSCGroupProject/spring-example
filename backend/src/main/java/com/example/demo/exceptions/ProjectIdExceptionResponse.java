package com.example.demo.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectIdExceptionResponse {
    private String projectIdentifier;

    public ProjectIdExceptionResponse(String projectIdentifier){
        this.projectIdentifier = projectIdentifier;
    }
}
