package com.micro.projectService.exception;

import java.util.UUID;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(UUID id) {
        super("Department not found: " + id);
    }
}

