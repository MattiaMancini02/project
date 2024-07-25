package com.micro.projectService.controller;

import com.micro.projectService.filter.AuthenticationFilter;
import com.micro.projectService.model.Project;
import com.micro.projectService.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;


    @Autowired
    public ProjectController(ProjectService projectService, AuthenticationFilter authenticationFilter) {
        this.projectService = projectService;
    }

    // Endpoint per creare un nuovo progetto
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.ok(createdProject);
    }

    // Endpoint per recuperare tutti i progetti
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // Endpoint per recuperare un progetto per ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable UUID id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    // Endpoint per aggiornare un progetto
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable UUID id, @RequestBody Project projectDetails) {
        Project updatedProject = projectService.updateProject(id, projectDetails.getName(), projectDetails.getDescription(), (projectDetails.getIdDepartment()));
        return ResponseEntity.ok(updatedProject);
    }

    // Endpoint per eliminare un progetto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint per aggiungere un dipartimento a un progetto
    @PostMapping("/{id_project}/departments")
    public ResponseEntity<Project> addDepartmentToProject(@PathVariable UUID id, @RequestBody UUID id_department) {
        Project updatedProject = projectService.addDepartmentToProject(id, id_department);
        return ResponseEntity.ok(updatedProject);
    }

    // Endpoint per rimuovere un dipartimento da un progetto
    @DeleteMapping("/{id}/departments/{departmentId}")
    public ResponseEntity<Project> removeDepartmentFromProject(@PathVariable UUID id) {
        Project updatedProject = projectService.removeDepartmentFromProject(id);
        return ResponseEntity.ok(updatedProject);
    }
    // Endpoint per recuperare un progetto per nome
    @GetMapping("/by-name")
    public ResponseEntity<Project> getProjectByName(@RequestParam String name) {
        Project project = projectService.getProjectByName(name);
        return ResponseEntity.ok(project);
    }
    @GetMapping("/by-department/{id_department}")
    public ResponseEntity<List<Project>> findProjectByIdDepartment(@PathVariable UUID id_department) {
        List<Project> projects = projectService.findProjectByIdDepartment(id_department);
        return ResponseEntity.ok(projects);
    }
}
