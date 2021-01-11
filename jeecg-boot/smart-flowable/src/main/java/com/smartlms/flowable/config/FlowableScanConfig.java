package com.smartlms.flowable.config;

import org.flowable.ui.common.rest.idm.remote.RemoteAccountResource;
import org.flowable.ui.common.service.idm.RemoteIdmService;
import org.flowable.ui.idm.rest.app.AccountResource;
import org.flowable.ui.modeler.rest.app.EditorGroupsResource;
import org.flowable.ui.modeler.rest.app.EditorUsersResource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * TODO
 *
 * @author chenjian
 * @version 1.0
 * @date 2020/12/27 1:16 下午
 */
@Configuration
@ComponentScan(basePackages = {
        "org.flowable.ui.modeler",
        "org.flowable.ui.common",
        "org.flowable.ui.idm",
        "org.flowable.idm",
},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AccountResource.class),//屏蔽IDM账户
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RemoteAccountResource.class),//屏蔽官方登录用户
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value =
                        EditorUsersResource.class),//屏蔽官方用户
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value =
                        EditorGroupsResource.class),//屏蔽官方用户组
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value =
                        RemoteIdmService.class)
        })
public class FlowableScanConfig {

}
