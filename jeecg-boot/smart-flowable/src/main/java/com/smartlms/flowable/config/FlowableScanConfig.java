package com.smartlms.flowable.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
})
public class FlowableScanConfig {

}
