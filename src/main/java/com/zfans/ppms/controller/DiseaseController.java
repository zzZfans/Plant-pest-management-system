package com.zfans.ppms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zfans.ppms.entity.Disease;
import com.zfans.ppms.entity.Plant;
import com.zfans.ppms.entity.PlantAcre;
import com.zfans.ppms.entity.vo.PlantVo;
import com.zfans.ppms.ret.R;
import com.zfans.ppms.service.DiseaseService;
import com.zfans.ppms.service.FileService;
import com.zfans.ppms.service.PlantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 病害 前端控制器
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@CrossOrigin
@Api(tags = "病害")
@RestController
@RequestMapping("/disease")
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private FileService fileService;
    @Autowired
    private PlantService plantService;

    @ApiOperation("新增病害")
    @PostMapping("save-disease-info")
    public R savePlantInfo(
            @ApiParam(value = "病害信息", required = true)
            @RequestBody Disease disease) {
        if (diseaseService.save(disease)) {
            return R.ok().message("保存成功！");
        } else {
            return R.error().message("保存失败！");
        }
    }

    @ApiOperation("根据ID删除病害信息")
    @DeleteMapping("remove/{id}")
    public R removeById(
            @ApiParam(value = "病害ID", required = true)
            @PathVariable String id) {

        //删除病害图片：OSS
        String picture = diseaseService.getById(id).getPicture();
        if (!"".equals(picture)) {
            fileService.removeFile(picture);
        }

        //删除病害
        if (diseaseService.removeById(id)) {
            return R.ok().message("删除成功！");
        } else {
            return R.error().message("数据不存在！");
        }
    }

    @ApiOperation("更新病害")
    @PutMapping("update-disease-info")
    public R updateDiseaseInfoById(
            @ApiParam(value = "病害基本信息", required = true)
            @RequestBody Disease disease) {

        if (diseaseService.updateById(disease)) {
            return R.ok().message("更新成功！");
        } else {
            return R.error().message("更新失败！");
        }
    }


    @ApiOperation("根据ID查询病害")
    @GetMapping("disease-info/{id}")
    public R getById(
            @ApiParam(value = "病害ID", required = true)
            @PathVariable String id) {

        Disease disease = diseaseService.getById(id);
        if (disease != null) {
            return R.ok().data("item", disease);
        } else {
            return R.error().message("病害信息不存在！");
        }

    }

    @ApiOperation("分页病害列表")
    @GetMapping("list/{page}/{limit}")
    public R index(
            @ApiParam(value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(value = "查询对象")
                    String query) {
        IPage<Disease> pageModel = diseaseService.selectPage(page, limit, query);
        List<Disease> records = pageModel.getRecords();

        for (Disease disease : records) {
            String plantName = plantService.getById(disease.getPlantId()).getName();
            disease.setAcre(plantName + "（" + disease.getAcre() + "）");
        }

        long total = pageModel.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }
}

