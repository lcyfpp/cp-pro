package com.linkedaim.admin.system.controller;

import com.linkedaim.admin.common.aop.WriteLog;
import com.linkedaim.admin.common.constant.LinkedaimResultEnum;
import com.linkedaim.admin.common.constant.ResultEnum;
import com.linkedaim.admin.common.constant.ResultResponse;
import com.linkedaim.admin.common.entity.LoginUser;
import com.linkedaim.admin.common.entity.PageResBean;
import com.linkedaim.admin.common.exception.CustomException;
import com.linkedaim.admin.common.filter.UserContext;
import com.linkedaim.admin.system.model.domain.convent.UserConvert;
import com.linkedaim.admin.system.model.domain.request.AuthorizeRoleReq;
import com.linkedaim.admin.system.model.domain.request.UserEditReq;
import com.linkedaim.admin.system.model.domain.request.UserListReq;
import com.linkedaim.admin.system.model.domain.request.UserUpPasswdReq;
import com.linkedaim.admin.system.model.domain.response.UserVO;
import com.linkedaim.admin.system.model.entity.User;
import com.linkedaim.admin.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "用户管理接口")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @WriteLog
    @ApiOperation(value = "获取用户列表", notes = "")
    @PostMapping("/getPageUserList")
    public ResultResponse<PageResBean<UserVO>> getPageUserList(@RequestBody @Valid UserListReq req) {
        Long userId = UserContext.getCurrentUser().getUserId();
        try {
            PageResBean<UserVO> userVOList = this.userService.getPageUserList(req,userId);
            return ResultResponse.ok(userVOList);
        } catch (CustomException e) {
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取用户列表失败", e);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value = "查询用户信息", notes = "")
    @PostMapping("/getUserInfo")
    public ResultResponse<UserVO> getUser() {
        Long userId = UserContext.getCurrentUser().getUserId();
        try {
            User user = this.userService.selectByPrimaryKey(userId);
            UserVO userVO = UserConvert.userToUserVO(user);
            return ResultResponse.ok(userVO);
        } catch (CustomException e) {
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取用户失败", e);
            return ResultResponse.fail(LinkedaimResultEnum.USER_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="检验用户名", notes="true:存在 false:不存在")
    @PostMapping("/checkUsername")
    public ResultResponse<Boolean> checkUsername(@RequestBody @Valid UserEditReq req){
        try{
            return ResultResponse.ok(ResultEnum.SUCCESS,userService.checkUsername(req));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("checkUsername exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="用户注册", notes="")
    @PostMapping("/registerUser")
    public ResultResponse registerUser(@RequestBody @Valid UserEditReq req){
        try{
            return ResultResponse.ok(ResultEnum.SUCCESS,userService.registerUser(req));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("registerUser exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="用户新增", notes="")
    @PostMapping("/addUser")
    public ResultResponse addUser(@RequestBody @Valid UserEditReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,userService.addUser(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("addUser exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="用户修改", notes="注：传用户ID和修改的属性")
    @PutMapping("/editUser")
    public ResultResponse editUser(@RequestBody @Valid UserEditReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,userService.editUser(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("editUser exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }


    @WriteLog
    @ApiOperation(value="修改密码", notes="注：传用户ID旧密码和新密码")
    @PutMapping("/editPassword")
    public ResultResponse editPassword(@RequestBody @Valid UserUpPasswdReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,userService.editPassword(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("editPassword exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="重置密码", notes="注：传用户ID")
    @PutMapping("/forgetPassword")
    public ResultResponse forgetPassword(@RequestBody @Valid UserUpPasswdReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,userService.forgetPassword(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("forgetPassword exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="用户删除", notes="")
    @DeleteMapping("/delUser")
    public ResultResponse delUser(@RequestBody @Valid UserEditReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,userService.delUser(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("delUser exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="用户授权角色", notes="")
    @PostMapping("/authorizeRole")
    public ResultResponse authorizeRole(@RequestBody @Valid AuthorizeRoleReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,userService.authorizeRole(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("authorizeRole exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

}
