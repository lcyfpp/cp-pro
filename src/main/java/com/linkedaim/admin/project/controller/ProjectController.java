package com.linkedaim.admin.project.controller;

import com.linkedaim.admin.common.aop.WriteLog;
import com.linkedaim.admin.common.constant.LinkedaimResultEnum;
import com.linkedaim.admin.common.constant.ResultEnum;
import com.linkedaim.admin.common.constant.ResultResponse;
import com.linkedaim.admin.common.exception.CustomException;
import com.linkedaim.admin.common.filter.UserContext;
import com.linkedaim.admin.project.model.entity.Project;
import com.linkedaim.admin.project.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.UUID;

@Controller
@Api(tags = "项目工程管理")
@Slf4j
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @WriteLog
    @ApiOperation(value = "持久化工程实体", notes = "")
    @RequestMapping("save")
    public ResultResponse save(Project project){
        try {
//            Long userId = UserContext.getCurrentUser().getUserId();
            if(StringUtils.isBlank(project.getId())) {
                project.setId(UUID.randomUUID().toString());
                project.setCreateTime(new Date());
                project.setStatus(0);
//            project.setCreateUserId(String.valueOf(userId));
                projectService.insert(project);
            }else{
                Project pro = projectService.selectByPrimaryKey(project.getId());
                pro.setStatus(project.getStatus());
                pro.setProjectName(project.getProjectName());
                pro.setInvestUnit(project.getInvestUnit());
                pro.setInvestMoney(project.getInvestMoney());
                pro.setStartTime(project.getStartTime());
                pro.setEndTime(project.getEndTime());
                pro.setPosition(project.getPosition());
                pro.setUpdateTime(new Date());
//                pro.setUpdateUserId(userId);
                projectService.updateByPrimaryKey(pro);
            }
            return ResultResponse.ok("success");
        } catch (CustomException e) {
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取菜单失败", e);
            return ResultResponse.fail(LinkedaimResultEnum.MENU_FAIL);
        }
    }



}
