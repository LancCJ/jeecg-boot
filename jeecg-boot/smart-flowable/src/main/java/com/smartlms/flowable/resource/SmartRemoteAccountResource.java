package com.smartlms.flowable.resource;

import com.google.common.collect.Lists;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.security.DefaultPrivileges;
import org.flowable.ui.common.security.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * TODO
 *
 * @author chenjian
 * @version 1.0
 * @date 2020/12/27 4:58 下午
 */
@RestController
@RequestMapping("/app")
public class SmartRemoteAccountResource {

    /**
     * GET /rest/account -> get the current user.
     * 做了2件事情，见下注释
     */
    @GetMapping(value = "/rest/account", produces = "application/json")
    public UserRepresentation getAccount() {
        //解决在创建模型的时候报错问题,构造一个假的对象
        User user = new UserEntityImpl();
        user.setId("SmartLMS");
        SecurityUtils.assumeUser(user);
        //解决接口/rest/account访问问题，直接本地构造最高权限用户
        UserRepresentation userRepresentation = new UserRepresentation();
        //账户基础信息设置
        userRepresentation.setId("SmartLMS");
        userRepresentation.setFirstName("SmartLMS");
        //权限设置
        List<String> privileges = Lists.newArrayList();
        privileges.add(DefaultPrivileges.ACCESS_MODELER);
        privileges.add(DefaultPrivileges.ACCESS_IDM);
        privileges.add(DefaultPrivileges.ACCESS_ADMIN);
        privileges.add(DefaultPrivileges.ACCESS_TASK);
        privileges.add(DefaultPrivileges.ACCESS_REST_API);
        userRepresentation.setPrivileges(privileges);
        return userRepresentation;
    }

}
