package com.micro.projectService.service;


import com.micro.projectService.exception.DepartmentNotFoundException;
import com.micro.projectService.exception.ProjectNotFoundException;
import com.micro.projectService.exception.ProjectNotFoundExceptionByName;
import com.micro.projectService.model.Project;
import com.micro.projectService.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final DepartmentService departmentService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, DepartmentService departmentService) {
        this.projectRepository = projectRepository;
        this.departmentService = departmentService;
    }
    // Metodo per creare un nuovo progetto
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // Metodo per recuperare tutti i progetti
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Metodo per recuperare un progetto per ID
    public Project getProjectById(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    // Metodo per aggiornare un progetto
    public Project updateProject(UUID id, String name, String description, UUID id_department) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));

        project.setName(name);
        project.setDescription(description);
        departmentService.existDepartmentById(id_department);
        project.setIdDepartment(id_department);
        return projectRepository.save(project);
    }

    // Metodo per eliminare un progetto
    public void deleteProject(UUID id) {
        projectRepository.deleteById(id);
    }

    // Metodo per aggiungere un dipartimento a un progetto
    public Project addDepartmentToProject(UUID id, UUID id_department) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        if (!departmentService.existDepartmentById(id_department)) {
            throw new DepartmentNotFoundException(id_department);
        }

        project.setIdDepartment(id_department);

        return projectRepository.save(project);
    }


    // Metodo per rimuovere un dipartimento da un progetto
    public Project removeDepartmentFromProject(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        // Rimuovi il dipartimento dalla lista dei dipartimenti del progetto
        project.setIdDepartment(null);
        return projectRepository.save(project);
    }
    // Metodo per recuperare un progetto per nome
    public Project getProjectByName(String name) {
        return projectRepository.findByName(name)
                .orElseThrow(() -> new ProjectNotFoundExceptionByName(name));
    }
    public List<Project> findProjectByIdDepartment(UUID id_department) {
        return projectRepository.findByIdDepartment(id_department);
    }
}


