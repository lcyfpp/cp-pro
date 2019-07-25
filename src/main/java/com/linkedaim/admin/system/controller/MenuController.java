package com.linkedaim.admin.system.controller;

import com.linkedaim.admin.common.aop.WriteLog;
import com.linkedaim.admin.common.constant.LinkedaimResultEnum;
import com.linkedaim.admin.common.constant.ResultEnum;
import com.linkedaim.admin.common.constant.ResultResponse;
import com.linkedaim.admin.common.entity.LoginUser;
import com.linkedaim.admin.common.exception.CustomException;
import com.linkedaim.admin.common.filter.UserContext;
import com.linkedaim.admin.system.model.domain.request.MenuEditReq;
import com.linkedaim.admin.system.model.domain.response.MenuVO;
import com.linkedaim.admin.system.service.MenuService;
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

@Api(tags = "菜单管理接口")
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @WriteLog
    @ApiOperation(value = "用户菜单列表", notes = "")
    @PostMapping("/getUserMenuList")
    public ResultResponse<List<MenuVO>> getUserMenuList() {
        Long userId = UserContext.getCurrentUser().getUserId();
        try {
            List<MenuVO> menus = this.menuService.getUserMenuList(userId);
            return ResultResponse.ok(menus);
        } catch (CustomException e) {
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取菜单失败", e);
            return ResultResponse.fail(LinkedaimResultEnum.MENU_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value = "获取全部菜单列表", notes = "")
    @PostMapping("/getMenuAllList")
    //@RequiresPermissions("menu:list")
    public ResultResponse<List<MenuVO>> getMenuAllList(@RequestBody @Valid String status) {
        try {
            List<MenuVO> menus = this.menuService.getMenuAllList(status);
            return ResultResponse.ok(menus);
        } catch (CustomException e) {
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取菜单集合失败", e);
            return ResultResponse.fail(LinkedaimResultEnum.MENU_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="检验菜单名称", notes="true:存在 false:不存在")
    @PostMapping("/checkMenuname")
    public ResultResponse<Boolean> checkMenuname(@RequestBody @Valid MenuEditReq req){
        try{
            return ResultResponse.ok(ResultEnum.SUCCESS,menuService.checkMenuname(req));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("checkMenuname exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }


    @WriteLog
    @ApiOperation(value="菜单新增", notes="")
    @PostMapping("/addMenu")
    public ResultResponse addMenu(@RequestBody @Valid MenuEditReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,menuService.addMenu(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("addUser exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

    @WriteLog
    @ApiOperation(value="菜单修改", notes="")
    @PutMapping("/editMenu")
    public ResultResponse editMenu(@RequestBody @Valid MenuEditReq req){
        try{
            LoginUser loginUser = UserContext.getCurrentUser();
            return ResultResponse.ok(ResultEnum.SUCCESS,menuService.editMenu(req,loginUser));
        } catch (CustomException e){
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception ex){
            log.error("editRole exception: ",ex);
            return ResultResponse.fail(ResultEnum.CALL_FAIL);
        }
    }

}
