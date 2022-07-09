package com.example.demo.service;


import com.example.demo.domain.Project;
import com.example.demo.exceptions.ProjectIdException;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }
        catch (Exception e){
            throw new ProjectIdException("Project ID '"+ project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    public Project findByProjectIdentifier(String projectId){
        return projectRepository.findByProjectIdentifier(projectId);
    }

    public List<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId);

        if(project == null)
            throw new ProjectIdException(projectId+ " project is not exists");

        projectRepository.delete(project);
    }
}
