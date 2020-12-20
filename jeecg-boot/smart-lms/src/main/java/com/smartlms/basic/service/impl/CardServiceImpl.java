package com.smartlms.basic.service.impl;

import com.smartlms.basic.entity.Card;
import com.smartlms.basic.mapper.CardMapper;
import com.smartlms.basic.service.ICardService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 卡号管理
 * @Author: jeecg-boot
 * @Date:   2020-12-20
 * @Version: V1.0
 */
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements ICardService {

}
