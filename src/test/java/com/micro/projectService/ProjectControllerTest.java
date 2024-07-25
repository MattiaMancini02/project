package com.micro.projectService;

import com.micro.projectService.controller.ProjectController;
import com.micro.projectService.exception.ProjectNotFoundException;
import com.micro.projectService.filter.AuthenticationFilter;
import com.micro.projectService.model.Project;
import com.micro.projectService.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;
    @Mock
    private AuthenticationFilter authenticationFilter;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project(UUID.randomUUID(), "Project 1", "Description 1", null);
        when(projectService.createProject(any(Project.class))).thenReturn(project);

        ResponseEntity<Project> responseEntity = projectController.createProject(project);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(project, responseEntity.getBody());
    }

    @Test
    public void testGetAllProjects() {
        Project project1 = new Project(UUID.randomUUID(), "Project 1", "Description 1", null);
        Project project2 = new Project(UUID.randomUUID(), "Project 2", "Description 2", null);
        List<Project> projects = Arrays.asList(project1, project2);
        when(projectService.getAllProjects()).thenReturn(projects);

        ResponseEntity<List<Project>> responseEntity = projectController.getAllProjects();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(projects, responseEntity.getBody());
    }

    @Test
    public void testGetProjectById_ValidToken() {
        UUID projectId = UUID.randomUUID();
        Project project = new Project(projectId, "Project 1", "Description 1", null);
        String token = "valid-token";

        when(authenticationFilter.validateToken(token)).thenReturn(true);
        when(projectService.getProjectById(projectId)).thenReturn(project);

        ResponseEntity<Project> responseEntity = projectController.getProjectById(projectId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(project, responseEntity.getBody());
    }

    @Test
    public void testGetProjectById_ProjectNotFound() {
        UUID projectId = UUID.randomUUID();
        String token = "valid-token";
        when(authenticationFilter.validateToken(token)).thenReturn(true);
        when(projectService.getProjectById(projectId)).thenThrow(new ProjectNotFoundException(projectId));

        assertThrows(ProjectNotFoundException.class, () -> projectController.getProjectById(projectId));
    }

    @Test
    public void testDeleteProject() {
        UUID projectId = UUID.randomUUID();

        ResponseEntity<Void> responseEntity = projectController.deleteProject(projectId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(projectService, times(1)).deleteProject(projectId);
    }
    @Test
    public void testAddDepartmentToProject() {
        UUID projectId = UUID.randomUUID();
        UUID id_department = UUID.randomUUID();
        Project updatedProject = new Project(projectId, "Project 1", "Description 1", id_department);
        when(projectService.addDepartmentToProject(eq(projectId), eq(id_department))).thenReturn(updatedProject);

        ResponseEntity<Project> responseEntity = projectController.addDepartmentToProject(projectId, id_department);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedProject, responseEntity.getBody());
    }

    @Test
    public void testRemoveDepartmentFromProject() {
        UUID projectId = UUID.randomUUID();
        Project updatedProject = new Project(projectId, "Project 1", "Description 1", null);
        when(projectService.removeDepartmentFromProject(eq(projectId))).thenReturn(updatedProject);
        ResponseEntity<Project> responseEntity = projectController.removeDepartmentFromProject(projectId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedProject, responseEntity.getBody());
   }

    @Test
    public void testGetProjectByName() {
        String projectName = "Project 1";
        Project project = new Project(UUID.randomUUID(), projectName, "Description 1", null);
        when(projectService.getProjectByName(eq(projectName))).thenReturn(project);

        ResponseEntity<Project> responseEntity = projectController.getProjectByName(projectName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(project, responseEntity.getBody());
    }
}
