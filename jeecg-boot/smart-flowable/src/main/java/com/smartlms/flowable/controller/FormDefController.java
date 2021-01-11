package com.smartlms.flowable.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import com.smartlms.flowable.entity.FormDef;
import com.smartlms.flowable.service.IFormDefService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 流程表单
 * @Author: jeecg-boot
 * @Date:   2021-01-11
 * @Version: V1.0
 */
@Api(tags="流程表单")
@RestController
@RequestMapping("/flowable/formDef")
@Slf4j
public class FormDefController extends JeecgController<FormDef, IFormDefService> {
	@Autowired
	private IFormDefService formDefService;
	
	/**
	 * 分页列表查询
	 *
	 * @param formDef
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "流程表单-分页列表查询")
	@ApiOperation(value="流程表单-分页列表查询", notes="流程表单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FormDef formDef,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<FormDef> queryWrapper = QueryGenerator.initQueryWrapper(formDef, req.getParameterMap());
		Page<FormDef> page = new Page<FormDef>(pageNo, pageSize);
		IPage<FormDef> pageList = formDefService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param formDef
	 * @return
	 */
	@AutoLog(value = "流程表单-添加")
	@ApiOperation(value="流程表单-添加", notes="流程表单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FormDef formDef) {
		formDefService.save(formDef);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param formDef
	 * @return
	 */
	@AutoLog(value = "流程表单-编辑")
	@ApiOperation(value="流程表单-编辑", notes="流程表单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FormDef formDef) {
		formDefService.updateById(formDef);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "流程表单-通过id删除")
	@ApiOperation(value="流程表单-通过id删除", notes="流程表单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		formDefService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "流程表单-批量删除")
	@ApiOperation(value="流程表单-批量删除", notes="流程表单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.formDefService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "流程表单-通过id查询")
	@ApiOperation(value="流程表单-通过id查询", notes="流程表单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FormDef formDef = formDefService.getById(id);
		if(formDef==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(formDef);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param formDef
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FormDef formDef) {
        return super.exportXls(request, formDef, FormDef.class, "流程表单");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, FormDef.class);
    }

}
