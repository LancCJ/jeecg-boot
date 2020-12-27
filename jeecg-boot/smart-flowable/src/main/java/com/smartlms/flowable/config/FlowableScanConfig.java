package com.smartlms.flowable.config;

import org.springframework.context.annotation.ComponentScan;

/**
 * TODO
 *
 * @author chenjian
 * @version 1.0
 * @date 2020/12/27 1:16 下午
 */
@ComponentScan(basePackages = {
        "org.flowable.ui.modeler.rest.app",
        "org.flowable.ui.common",
})
public class FlowableScanConfig {

}
