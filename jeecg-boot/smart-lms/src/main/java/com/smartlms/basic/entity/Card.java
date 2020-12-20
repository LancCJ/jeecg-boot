package com.smartlms.basic.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 卡号管理
 * @Author: jeecg-boot
 * @Date:   2020-12-20
 * @Version: V1.0
 */
@Data
@TableName("lms_card")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="lms_card对象", description="卡号管理")
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**卡类型*/
	@Excel(name = "卡类型", width = 15, dicCode = "BASIC_CARD_TYPE")
	@Dict(dicCode = "BASIC_CARD_TYPE")
    @ApiModelProperty(value = "卡类型")
    private java.lang.String type;
	/**印刷号*/
	@Excel(name = "印刷号", width = 15)
    @ApiModelProperty(value = "印刷号")
    private java.lang.String printingNo;
	/**ID卡*/
	@Excel(name = "ID卡", width = 15)
    @ApiModelProperty(value = "ID卡")
    private java.lang.String idNo;
	/**IC卡*/
	@Excel(name = "IC卡", width = 15)
    @ApiModelProperty(value = "IC卡")
    private java.lang.String icNo;
	/**启用状态*/
	@Excel(name = "启用状态", width = 15)
    @ApiModelProperty(value = "启用状态")
    private java.lang.String izEnable;
}
