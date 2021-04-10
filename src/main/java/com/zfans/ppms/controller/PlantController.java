package com.zfans.ppms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zfans.ppms.controller.oss.FileController;
import com.zfans.ppms.entity.Plant;
import com.zfans.ppms.entity.PlantAcre;
import com.zfans.ppms.entity.vo.PlantVo;
import com.zfans.ppms.ret.R;
import com.zfans.ppms.service.FileService;
import com.zfans.ppms.service.PlantAcreService;
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
 * 植物 前端控制器
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@CrossOrigin
@Api(tags = "植物")
@RestController
@RequestMapping("/plant")
public class PlantController {
    @Autowired
    private PlantService plantService;
    @Autowired
    private FileService fileService;
    @Autowired
    private PlantAcreService plantAcreService;

    @ApiOperation("新增植物")
    @PostMapping("save-plant-info")
    public R savePlantInfo(
            @ApiParam(value = "植物基本信息", required = true)
            @RequestBody PlantVo plantVo) {
        Long plantId = plantService.savePlant(plantVo);
        return R.ok().data("plantId", plantId).message("保存成功！");
    }

    @ApiOperation("根据ID删除植物")
    @DeleteMapping("remove/{id}")
    public R removeById(
            @ApiParam(value = "植物ID", required = true)
            @PathVariable String id) {

        plantAcreService.removeByPlantId(id);

        //删除植物图片：OSS
        String picture = plantService.getById(id).getPicture();
        if (!"".equals(picture)) {
            fileService.removeFile(picture);
        }

        //删除植物
        boolean result = plantService.removeById(id);
        if (result) {
            return R.ok().message("删除成功！");
        } else {
            return R.error().message("数据不存在！");
        }
    }

    @ApiOperation("更新植物")
    @PutMapping("update-plant-info")
    public R updatePlantInfoById(
            @ApiParam(value = "植物基本信息", required = true)
            @RequestBody PlantVo plantVo) {

        plantAcreService.updateByPlantIdAndAcreList(plantVo.getId(), plantVo.getAcreList());

        Plant plant = new Plant();
        BeanUtils.copyProperties(plantVo, plant);

        plantService.updateById(plant);
        return R.ok().message("修改成功！");
    }


    @ApiOperation("根据ID查询植物")
    @GetMapping("plant-info/{id}")
    public R getById(
            @ApiParam(value = "植物ID", required = true)
            @PathVariable String id) {

        Plant plant = plantService.getById(id);

        if (plant == null) {
            return R.error().message("数据不存在！");
        }

        PlantVo plantVo = new PlantVo();

        BeanUtils.copyProperties(plant, plantVo);

        List<PlantAcre> plantAcreList = plantAcreService.list(new QueryWrapper<PlantAcre>().eq("plant_id", id));

        List<String> acreList = new ArrayList<>();

        for (PlantAcre plantAcre : plantAcreList) {
            acreList.add(plantAcre.getAcre());
        }

        plantVo.setAcreList(acreList);

        return R.ok().data("item", plantVo);
    }

    @ApiOperation("植物列表")
    @GetMapping("plant-info/list")
    public R plantList() {
        List<Plant> plantList = plantService.list();

        if (plantList.isEmpty()) {
            return R.error().message("数据不存在！");
        }

        List<PlantVo> plantVoList = new ArrayList<>();

        plantList.forEach(plant -> {
            PlantVo plantVo = new PlantVo();

            BeanUtils.copyProperties(plant, plantVo);

            List<PlantAcre> plantAcreList = plantAcreService.list(new QueryWrapper<PlantAcre>().eq("plant_id", plant.getId()));

            List<String> acreList = new ArrayList<>();

            for (PlantAcre plantAcre : plantAcreList) {
                acreList.add(plantAcre.getAcre());
            }

            plantVo.setAcreList(acreList);

            plantVoList.add(plantVo);
        });


        return R.ok().data("items", plantVoList);
    }

    @ApiOperation("分页植物列表")
    @GetMapping("list/{page}/{limit}")
    public R index(
            @ApiParam(value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(value = "查询对象")
                    String query) {
        IPage<Plant> pageModel = plantService.selectPage(page, limit, query);
        List<Plant> records = pageModel.getRecords();
        long total = pageModel.getTotal();

        List<PlantVo> plantVoList = new ArrayList<>();

        for (Plant plant : records) {
            PlantVo plantVo = new PlantVo();
            BeanUtils.copyProperties(plant, plantVo);

            List<PlantAcre> plantAcreList = plantAcreService.list(new QueryWrapper<PlantAcre>().eq("plant_id", plant.getId()));

            List<String> acreList = new ArrayList<>();

            for (PlantAcre plantAcre : plantAcreList) {
                acreList.add(plantAcre.getAcre());
            }

            plantVo.setAcreList(acreList);
            plantVoList.add(plantVo);
        }

        return R.ok().data("total", total).data("rows", plantVoList);
    }

}

