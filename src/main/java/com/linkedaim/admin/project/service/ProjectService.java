package com.linkedaim.admin.project.service;

import com.linkedaim.admin.common.service.impl.BaseServiceImpl;
import com.linkedaim.admin.project.model.entity.Project;
import com.linkedaim.admin.system.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("projectService")
public class ProjectService extends BaseServiceImpl<Project> {

    @Autowired
    private ProjectMapper projectMapper;

//    public int insert(Project project){
//        return projectMapper.insert(project);
//    }
}
