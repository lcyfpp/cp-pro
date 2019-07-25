package com.linkedaim.admin.system.controller;

import com.linkedaim.admin.common.aop.WriteLog;
import com.linkedaim.admin.common.constant.ResultEnum;
import com.linkedaim.admin.common.constant.ResultResponse;
import com.linkedaim.admin.common.entity.LoginUser;
import com.linkedaim.admin.common.entity.PageResBean;
import com.linkedaim.admin.common.exception.CustomException;
import com.linkedaim.admin.common.filter.UserContext;
import com.linkedaim.admin.system.model.domain.convent.RoleConvert;
import com.linkedaim.admin.system.model.domain.request.AuthorizeMenuReq;
import com.linkedaim.admin.system.model.domain.request.RoleEditReq;
import com.linkedaim.admin.system.model.domain.request.RoleListReq;
import com.linkedaim.admin.system.model.domain.response.RoleVO;
import com.linkedaim.admin.system.model.entity.Role;
import com.linkedaim.admin.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "角色管理接口")
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @WriteLog
    @ApiOperation(value = "获取角色列表", notes = "")
    @PostMapping("/getPageRoleList")
    public ResultResponse<PageResBean<RoleVO>> getPageRoleList(@RequestBody @Valid RoleListReq req) {
        Long userId = UserContext.getCurrentUser().getUserId();
        try {
            PageResBean<RoleVO> roleVOList = this.roleService.getPageRoleList(req,userId);
            return ResultResponse.ok(roleVOList);
        } catch (CustomException e) {
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取角色列表", e);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value = "获取可用角色列表", notes = "")
    @PostMapping("/getRoleList")
    public ResultResponse<RoleVO> getRoleList() {
        Long userId = UserContext.getCurrentUser().getUserId();
        try {
            List<RoleVO> roleVOList = this.roleService.getRoleList(userId);
            return ResultResponse.ok(roleVOList);
        } catch (CustomException e) {
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取可用角色列表", e);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value = "获取用户角色列表", notes = "")
    @PostMapping("/getUserRoleList")
    public ResultResponse<RoleVO> getUserRoleList(@RequestBody @Valid Long userId) {
        try {
            List<Role> roleList = this.roleService.getUserRoleList(userId);
            List<RoleVO> roleVOList = RoleConvert.roleToRoleVOList(roleList);
            return ResultResponse.ok(roleVOList);
        } catch (CustomException e) {
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取用户角色列表", e);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }


    @WriteLog
    @ApiOperation(value="检验角色名称", notes="true:存在 false:不存在")
    @PostMapping("/checkRolename")
    public ResultResponse<Boolean> checkRolename(@RequestBody @Valid RoleEditReq req){
        try{
            return ResultResponse.ok(ResultEnum.SUCCESS,roleService.checkRolename(req));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("checkRolename exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }


    @WriteLog
    @ApiOperation(value="角色新增", notes="")
    @PostMapping("/addRole")
    public ResultResponse addRole(@RequestBody @Valid RoleEditReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,roleService.addRole(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("addUser exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="角色修改", notes="")
    @PutMapping("/editRole")
    public ResultResponse editRole(@RequestBody @Valid RoleEditReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,roleService.editRole(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("editRole exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="角色授权菜单", notes="")
    @PostMapping("/authorizeMenu")
    public ResultResponse authorizeMenu(@RequestBody @Valid AuthorizeMenuReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,roleService.authorizeMenu(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("authorizeMenu exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

}
