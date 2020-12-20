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
 * @Description: 车型管理
 * @Author: jeecg-boot
 * @Date:   2020-12-20
 * @Version: V1.0
 */
@Data
@TableName("lms_vehicle_model")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="lms_vehicle_model对象", description="车型管理")
public class VehicleModel implements Serializable {
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
	/**车型编号*/
	@Excel(name = "车型编号", width = 15)
    @ApiModelProperty(value = "车型编号")
    private java.lang.String code;
	/**车型名称*/
	@Excel(name = "车型名称", width = 15)
    @ApiModelProperty(value = "车型名称")
    private java.lang.String name;
	/**荷载重量*/
	@Excel(name = "荷载重量", width = 15)
    @ApiModelProperty(value = "荷载重量")
    private java.math.BigDecimal loadWeight;
	/**超限重量*/
	@Excel(name = "超限重量", width = 15)
    @ApiModelProperty(value = "超限重量")
    private java.math.BigDecimal overWeight;
	/**轴型*/
	@Excel(name = "轴型", width = 15)
    @ApiModelProperty(value = "轴型")
    private java.lang.String axialType;
	/**轴数*/
	@Excel(name = "轴数", width = 15)
    @ApiModelProperty(value = "轴数")
    private java.lang.Integer axialNum;
}
