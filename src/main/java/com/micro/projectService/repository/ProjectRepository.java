package com.micro.projectService.repository;

import com.micro.projectService.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findByName(String name);


    List<Project> findByIdDepartment(UUID id_department);

}
