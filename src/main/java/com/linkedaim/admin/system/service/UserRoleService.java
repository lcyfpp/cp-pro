package com.linkedaim.admin.system.service;

import com.linkedaim.admin.common.service.impl.BaseServiceImpl;
import com.linkedaim.admin.system.model.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("userRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRoleService extends BaseServiceImpl<UserRole> {

}
