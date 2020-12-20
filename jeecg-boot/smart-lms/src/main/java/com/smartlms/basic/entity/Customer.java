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
 * @Description: 客户
 * @Author: jeecg-boot
 * @Date:   2020-12-20
 * @Version: V1.0
 */
@Data
@TableName("lms_customer")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="lms_customer对象", description="客户")
public class Customer implements Serializable {
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
	/**客户编号*/
	@Excel(name = "客户编号", width = 15)
    @ApiModelProperty(value = "客户编号")
    private java.lang.String code;
	/**客户名称*/
	@Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private java.lang.String name;
	/**等级*/
	@Excel(name = "等级", width = 15, dicCode = "BASIC_CUSTOMER_GRADE")
	@Dict(dicCode = "BASIC_CUSTOMER_GRADE")
    @ApiModelProperty(value = "等级")
    private java.lang.String grade;
	/**客户联系人*/
	@Excel(name = "客户联系人", width = 15)
    @ApiModelProperty(value = "客户联系人")
    private java.lang.String contact;
	/**业务员*/
	@Excel(name = "业务员", width = 15)
    @ApiModelProperty(value = "业务员")
    private java.lang.String salesman;
	/**所属区域1*/
	@Excel(name = "所属区域1", width = 15)
    @ApiModelProperty(value = "所属区域1")
    private java.lang.String parentArea1;
	/**所属区域2*/
	@Excel(name = "所属区域2", width = 15)
    @ApiModelProperty(value = "所属区域2")
    private java.lang.String parentArea2;
	/**所属区域3*/
	@Excel(name = "所属区域3", width = 15)
    @ApiModelProperty(value = "所属区域3")
    private java.lang.String parentArea3;
	/**所属区域4*/
	@Excel(name = "所属区域4", width = 15)
    @ApiModelProperty(value = "所属区域4")
    private java.lang.String parentArea4;
}
