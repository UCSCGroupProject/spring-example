package com.example.demo.web;

import com.example.demo.domain.Project;
import com.example.demo.exceptions.ProjectIdException;
import com.example.demo.service.MapValidationErrorService;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        // Check if already exists
        Project prj = projectService.findByProjectIdentifier(project.getProjectIdentifier());
        if(prj != null)
            return new ResponseEntity<String>(" project already exists", HttpStatus.BAD_GATEWAY);

        Project newProject = projectService.saveOrUpdateProject(project);

        return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(@PathVariable String projectId){
        Project project = projectService.findByProjectIdentifier(projectId);

        if(project == null)
            throw new ProjectIdException("Project ID '"+ project.getProjectIdentifier().toUpperCase()+"' does not exists");

        return project;
    }

    @GetMapping("/all")
    public List<Project> getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
        projectService.deleteProjectByIdentifier(projectId);

        return new ResponseEntity<String>(projectId+ " was deleted", HttpStatus.OK);
    }
}
