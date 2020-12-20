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
 * @Description: 物料信息
 * @Author: jeecg-boot
 * @Date:   2020-12-20
 * @Version: V1.0
 */
@Data
@TableName("lms_materiel")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="lms_materiel对象", description="物料信息")
public class Materiel implements Serializable {
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
	/**物料名称*/
	@Excel(name = "物料名称", width = 15)
    @ApiModelProperty(value = "物料名称")
    private java.lang.String name;
	/**物料编码*/
	@Excel(name = "物料编码", width = 15)
    @ApiModelProperty(value = "物料编码")
    private java.lang.String code;
	/**业务类型*/
	@Excel(name = "业务类型", width = 15, dicCode = "BASIC_MATERIEL_BIZ_TYPE")
	@Dict(dicCode = "BASIC_MATERIEL_BIZ_TYPE")
    @ApiModelProperty(value = "业务类型")
    private java.lang.String bizType;
	/**包装类型*/
	@Excel(name = "包装类型", width = 15, dicCode = "BASIC_MATERIEL_PKG_TYPE")
	@Dict(dicCode = "BASIC_MATERIEL_PKG_TYPE")
    @ApiModelProperty(value = "包装类型")
    private java.lang.String packageType;
	/**物料分组*/
	@Excel(name = "物料分组", width = 15, dictTable = "lms_materiel_group", dicText = "name", dicCode = "code")
	@Dict(dictTable = "lms_materiel_group", dicText = "name", dicCode = "code")
    @ApiModelProperty(value = "物料分组")
    private java.lang.String materielGroup;
	/**物料标识*/
	@Excel(name = "物料标识", width = 15)
    @ApiModelProperty(value = "物料标识")
    private java.lang.String identification;
	/**物料规格*/
	@Excel(name = "物料规格", width = 15)
    @ApiModelProperty(value = "物料规格")
    private java.lang.String specification;
	/**物料描述*/
	@Excel(name = "物料描述", width = 15)
    @ApiModelProperty(value = "物料描述")
    private java.lang.String description;
	/**需要采样*/
	@Excel(name = "需要采样", width = 15)
    @ApiModelProperty(value = "需要采样")
    private java.lang.String izSampling;
	/**需要确认*/
	@Excel(name = "需要确认", width = 15)
    @ApiModelProperty(value = "需要确认")
    private java.lang.String izConfirm;
	/**需要扣杂*/
	@Excel(name = "需要扣杂", width = 15)
    @ApiModelProperty(value = "需要扣杂")
    private java.lang.String izDeduction;
}
