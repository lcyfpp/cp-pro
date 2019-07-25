package com.linkedaim.admin.system.mapper;

import com.linkedaim.admin.common.mapper.BaseMapper;
import com.linkedaim.admin.project.model.entity.Project;

public interface ProjectMapper extends BaseMapper<Project> {
    void save(Project project);
}
