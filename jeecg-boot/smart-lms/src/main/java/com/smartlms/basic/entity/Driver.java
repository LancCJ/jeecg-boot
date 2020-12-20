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
 * @Description: 驾驶员管理
 * @Author: jeecg-boot
 * @Date:   2020-12-20
 * @Version: V1.0
 */
@Data
@TableName("lms_driver")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="lms_driver对象", description="驾驶员管理")
public class Driver implements Serializable {
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
	/**运输单位*/
	@Excel(name = "运输单位", width = 15, dictTable = "lms_transportation_company", dicText = "name", dicCode = "code")
	@Dict(dictTable = "lms_transportation_company", dicText = "name", dicCode = "code")
    @ApiModelProperty(value = "运输单位")
    private java.lang.String factory;
	/**准驾车型*/
	@Excel(name = "准驾车型", width = 15, dicCode = "BASIC_DRIVER_TYPE")
	@Dict(dicCode = "BASIC_DRIVER_TYPE")
    @ApiModelProperty(value = "准驾车型")
    private java.lang.String driverType;
	/**驾驶员*/
	@Excel(name = "驾驶员", width = 15)
    @ApiModelProperty(value = "驾驶员")
    private java.lang.String driverName;
	/**身份证号*/
	@Excel(name = "身份证号", width = 15)
    @ApiModelProperty(value = "身份证号")
    private java.lang.String cardNo;
	/**手机号*/
	@Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private java.lang.String phoneNo;
	/**联系地址*/
	@Excel(name = "联系地址", width = 15)
    @ApiModelProperty(value = "联系地址")
    private java.lang.String contactAddress;
	/**驾驶证号*/
	@Excel(name = "驾驶证号", width = 15)
    @ApiModelProperty(value = "驾驶证号")
    private java.lang.String driverNo;
	/**驾驶证有效期*/
	@Excel(name = "驾驶证有效期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "驾驶证有效期")
    private java.util.Date driverPeriodTime;
	/**驾驶员从业资格证号*/
	@Excel(name = "驾驶员从业资格证号", width = 15)
    @ApiModelProperty(value = "驾驶员从业资格证号")
    private java.lang.String driverLicenceNo;
	/**从业资格证有效期*/
	@Excel(name = "从业资格证有效期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "从业资格证有效期")
    private java.util.Date driverLicenceTime;
}
