package com.micro.projectService.exception;

public class ProjectNotFoundExceptionByName extends RuntimeException {
    public ProjectNotFoundExceptionByName(String name) {
        super("Project not found: " + name);
    }
}
