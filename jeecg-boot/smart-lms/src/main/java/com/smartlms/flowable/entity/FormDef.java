package com.smartlms.flowable.entity;

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
 * @Description: 流程表单
 * @Author: jeecg-boot
 * @Date:   2021-01-11
 * @Version: V1.0
 */
@Data
@TableName("flowable_form_def")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="flowable_form_def对象", description="流程表单")
public class FormDef implements Serializable {
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
	/**表单Key*/
	@Excel(name = "表单Key", width = 15)
    @ApiModelProperty(value = "表单Key")
    private java.lang.String formKey;
	/**表单名称*/
	@Excel(name = "表单名称", width = 15)
    @ApiModelProperty(value = "表单名称")
    private java.lang.String formName;
	/**表单版本*/
	@Excel(name = "表单版本", width = 15)
    @ApiModelProperty(value = "表单版本")
    private java.math.BigDecimal formVersion;
	/**表单内容*/
	@Excel(name = "表单内容", width = 15)
    private transient java.lang.String formJsonString;

    private byte[] formJson;

    public byte[] getFormJson(){
        if(formJsonString==null){
            return null;
        }
        try {
            return formJsonString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFormJsonString(){
        if(formJson==null || formJson.length==0){
            return "";
        }
        try {
            return new String(formJson,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
