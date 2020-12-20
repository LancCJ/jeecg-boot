package com.smartlms.basic.service.impl;

import com.smartlms.basic.entity.Customer;
import com.smartlms.basic.mapper.CustomerMapper;
import com.smartlms.basic.service.ICustomerService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 客户
 * @Author: jeecg-boot
 * @Date:   2020-12-20
 * @Version: V1.0
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
