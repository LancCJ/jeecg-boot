package com.smartlms.basic.service.impl;

import com.smartlms.basic.entity.Driver;
import com.smartlms.basic.mapper.DriverMapper;
import com.smartlms.basic.service.IDriverService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 驾驶员管理
 * @Author: jeecg-boot
 * @Date:   2020-12-20
 * @Version: V1.0
 */
@Service
public class DriverServiceImpl extends ServiceImpl<DriverMapper, Driver> implements IDriverService {

}
