package com.micro.projectService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class DepartmentService {
    private final RestTemplate restTemplate;
    private static final String MICROSERVICE_DEPARTMENT_URL = "http://localhost:3002/api/department";

    @Autowired
    public DepartmentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean existDepartmentById(UUID id) {
        return Boolean.TRUE.equals(restTemplate.getForObject(MICROSERVICE_DEPARTMENT_URL + "/" + id, boolean.class));
    }
}
