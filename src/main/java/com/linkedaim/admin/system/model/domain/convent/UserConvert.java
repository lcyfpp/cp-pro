package com.linkedaim.admin.system.model.domain.convent;

import com.google.common.collect.Lists;
import com.linkedaim.admin.system.model.domain.response.UserVO;
import com.linkedaim.admin.system.model.entity.User;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyangyang
 * @title 用户对象装换
 * @date 2019-07-02
 */

public class UserConvert implements Serializable {

    /**
     * 用户对象装换
     * @param user
     * @return
     */
    public static UserVO userToUserVO(User user) {
        UserVO userVO = new UserVO();
        if (user != null) {
            BeanUtils.copyProperties(user, userVO);
        }
        return userVO;
    }

    /**
     * 用户集合装换
     * @param userList
     * @return
     */
    public static List<UserVO> userToUserVOList(List<User> userList) {
        List<UserVO> userVOList = Lists.newLinkedList();
        userList.forEach(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        });
        return userVOList;
    }

}
