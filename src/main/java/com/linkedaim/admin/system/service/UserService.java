package com.linkedaim.admin.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.linkedaim.admin.common.constant.LinkedaimResultEnum;
import com.linkedaim.admin.common.constant.SystemConstant;
import com.linkedaim.admin.common.entity.LoginUser;
import com.linkedaim.admin.common.entity.PageResBean;
import com.linkedaim.admin.common.exception.CustomException;
import com.linkedaim.admin.common.service.impl.BaseServiceImpl;
import com.linkedaim.admin.common.util.IdGeneratorUtils;
import com.linkedaim.admin.system.constants.BuinessEnum;
import com.linkedaim.admin.system.mapper.RoleMapper;
import com.linkedaim.admin.system.mapper.UserMapper;
import com.linkedaim.admin.system.mapper.UserRoleMapper;
import com.linkedaim.admin.system.model.domain.convent.UserConvert;
import com.linkedaim.admin.system.model.domain.request.AuthorizeRoleReq;
import com.linkedaim.admin.system.model.domain.request.UserEditReq;
import com.linkedaim.admin.system.model.domain.request.UserListReq;
import com.linkedaim.admin.system.model.domain.request.UserUpPasswdReq;
import com.linkedaim.admin.system.model.domain.response.UserVO;
import com.linkedaim.admin.system.model.entity.Role;
import com.linkedaim.admin.system.model.entity.User;
import com.linkedaim.admin.system.model.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserService extends BaseServiceImpl<User> {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 用户列表查询
     * @param req
     * @param userId
     * @return
     */
    public PageResBean<UserVO> getPageUserList(UserListReq req, Long userId) {
        PageResBean<UserVO> pageResBean = new PageResBean<>();
        PageHelper.startPage(req.getPageNo(),req.getPageSize());
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(req.getStatus())) {
            criteria.andEqualTo("status", req.getStatus());
        }
        if (StringUtils.isNotEmpty(req.getAccount())) {
            criteria.andLike("account", req.getAccount());
        }
        if (StringUtils.isNotEmpty(req.getMobile())) {
            criteria.andLike("mobile", req.getMobile());
        }
        if (StringUtils.isNotEmpty(req.getUsername())) {
            criteria.andLike("usernmae", req.getUsername());
        }
        if (req.getUserId()!=null) {
            criteria.andEqualTo("id", req.getUserId());
        }
        example.setOrderByClause("id desc");
        List<User> userList = userMapper.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo(userList);
        List<UserVO> userVOList = UserConvert.userToUserVOList(userList);
        pageResBean.setPage(req.getPageNo());
        pageResBean.setPageSize(req.getPageSize());
        pageResBean.setTotal((int)pageInfo.getTotal());
        pageResBean.setList(userVOList);
        return pageResBean;
    }

    /**
     * 用户注册
     * @param req
     * @return
     */
    public User registerUser(UserEditReq req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setAccount(IdGeneratorUtils.getId());
        String md5password = DigestUtils.md5DigestAsHex(req.getPassword().getBytes());
        user.setPassword(md5password);
        user.setMobile(req.getMobile());
        user.setStatus(BuinessEnum.userLockStatusEnum.ENABLE.getCode());
        user.setAvatar(req.getAvatar());
        user.setEmail(req.getEmail());
        user.setSex(req.getSex());
        user.setLastLoginTime(new Date());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insertSelective(user);
        //获取默认角色
        Role role = new Role();
        role.setRoleCode(SystemConstant.DEFALT_ROLE_CODE);
        role = roleMapper.selectOne(role);
        //添加默认用户角色关系
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRole.setCreateTime(new Date());
        userRole.setUpdateTime(new Date());
        userRoleMapper.insertSelective(userRole);
        return user;
    }

    /**
     * 检验用户名
     * @param req
     * @return
     */
    public Boolean checkUsername(UserEditReq req) {
        User user = new User();
        user.setUsername(req.getUsername());
        return userMapper.selectOne(user)!=null?true:false;
    }

    /**
     * 用户编辑
     * @param req
     * @param loginUser
     * @return
     */
    public int editUser(UserEditReq req, LoginUser loginUser) {
        User user = new User();
        BeanUtils.copyProperties(req,user);
        user.setUpdateTime(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 用户新增
     * @param req
     * @param loginUser
     * @return
     */
    public int addUser(UserEditReq req, LoginUser loginUser){
        User user = new User();
        user.setUsername(req.getUsername());
        user.setAccount(IdGeneratorUtils.getId());
        String md5password = DigestUtils.md5DigestAsHex(req.getPassword().getBytes());
        user.setPassword(md5password);
        user.setMobile(req.getMobile());
        user.setStatus(BuinessEnum.userLockStatusEnum.ENABLE.getCode());
        user.setAvatar(req.getAvatar());
        user.setEmail(req.getEmail());
        user.setSex(req.getSex());
        user.setLastLoginTime(new Date());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userMapper.insertSelective(user);
    }

    /**
     * 修改密码
     * @param req
     * @param loginUser
     * @return
     */
    public int editPassword(UserUpPasswdReq req, LoginUser loginUser) {
        User user = userMapper.selectByPrimaryKey(loginUser.getUserId());
        if (user == null) {
            throw new CustomException(LinkedaimResultEnum.USER_NOTEXIST.getMessage());
        }
        String oldPassword = DigestUtils.md5DigestAsHex(req.getOldpassword().getBytes());
        if (!user.getPassword().equals(oldPassword)) {
            throw new CustomException(LinkedaimResultEnum.OLDPASSWD_FAIL.getMessage());
        }
        String newPassword = DigestUtils.md5DigestAsHex(req.getNewpassword().getBytes());
        user.setPassword(newPassword);
        user.setUpdateTime(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 重置密码
     * @param req
     * @return
     */
    public int forgetPassword(UserUpPasswdReq req, LoginUser loginUser) {
        User user = new User();
        user.setId(req.getId());
        User userDto = userMapper.selectOne(user);
        if (userDto == null) {
            throw new CustomException(LinkedaimResultEnum.USER_NOTEXIST.getMessage());
        }
        String newPassword = DigestUtils.md5DigestAsHex(SystemConstant.DEFAULT_PASSWORD.getBytes());
        user.setPassword(newPassword);
        user.setUpdateTime(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 用户删除
     * @param req
     * @param loginUser
     * @return
     */
    public int delUser(UserEditReq req, LoginUser loginUser) {
        return userMapper.deleteByPrimaryKey(req.getId());
    }

    /**
     * 用户授权角色
     * @param req
     * @param loginUser
     * @return
     */
    public int authorizeRole(AuthorizeRoleReq req, LoginUser loginUser){
        int result = 0;
        List<Long> roleIds = req.getRoleIds();
        if (!CollectionUtils.isEmpty(roleIds)){
            //删除旧角色
            Example example = new Example(UserRole.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("user_id", req.getUserId());
            userRoleMapper.deleteByExample(example);
            List<UserRole> userRoleList = Lists.newLinkedList();
            roleIds.forEach(aLong -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(req.getUserId());
                userRole.setRoleId(aLong);
                userRole.setCreateTime(new Date());
                userRole.setUpdateTime(new Date());
                userRoleList.add(userRole);
            });
            result = userRoleMapper.insertList(userRoleList);
        }
        return result;
    }
}
