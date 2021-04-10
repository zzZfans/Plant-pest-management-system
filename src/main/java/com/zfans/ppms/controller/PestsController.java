package com.zfans.ppms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zfans.ppms.entity.Disease;
import com.zfans.ppms.entity.Pests;
import com.zfans.ppms.entity.Pests;
import com.zfans.ppms.ret.R;
import com.zfans.ppms.service.FileService;
import com.zfans.ppms.service.PestsService;
import com.zfans.ppms.service.PlantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.ProgressBarUI;
import java.util.List;

/**
 * <p>
 * 虫害 前端控制器
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@CrossOrigin
@Api(tags = "虫害")
@RestController
@RequestMapping("/pests")
public class PestsController {

    @Autowired
    private PestsService pestsService;
    @Autowired
    private FileService fileService;
    @Autowired
    private PlantService plantService;

    @ApiOperation("新增虫害")
    @PostMapping("save-pests-info")
    public R savePestsInfo(
            @ApiParam(value = "虫害信息", required = true)
            @RequestBody Pests pests) {
        if (pestsService.save(pests)) {
            return R.ok().message("保存成功！");
        } else {
            return R.error().message("保存失败！");
        }
    }

    @ApiOperation("根据ID删除虫害信息")
    @DeleteMapping("remove/{id}")
    public R removeById(
            @ApiParam(value = "虫害ID", required = true)
            @PathVariable String id) {

        //删除虫害图片：OSS
        String picture = pestsService.getById(id).getPicture();
        if (!"".equals(picture)) {
            fileService.removeFile(picture);
        }

        //删除虫害
        if (pestsService.removeById(id)) {
            return R.ok().message("删除成功！");
        } else {
            return R.error().message("数据不存在！");
        }
    }

    @ApiOperation("更新虫害")
    @PutMapping("update-pests-info")
    public R updatePestsInfoById(
            @ApiParam(value = "虫害基本信息", required = true)
            @RequestBody Pests pests) {

        if (pestsService.updateById(pests)) {
            return R.ok().message("更新成功！");
        } else {
            return R.error().message("更新失败！");
        }
    }


    @ApiOperation("根据ID查询虫害")
    @GetMapping("pests-info/{id}")
    public R getById(
            @ApiParam(value = "虫害ID", required = true)
            @PathVariable String id) {

        Pests pests = pestsService.getById(id);
        if (pests != null) {
            return R.ok().data("item", pests);
        } else {
            return R.error().message("虫害信息不存在！");
        }

    }

    @ApiOperation("分页虫害列表")
    @GetMapping("list/{page}/{limit}")
    public R index(
            @ApiParam(value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(value = "查询对象")
                    String query) {
        IPage<Pests> pageModel = pestsService.selectPage(page, limit, query);
        List<Pests> records = pageModel.getRecords();

        for (Pests pests : records) {
            String plantName = plantService.getById(pests.getPlantId()).getName();
            pests.setAcre(plantName + "（" + pests.getAcre() + "）");
        }
        
        long total = pageModel.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }
}

