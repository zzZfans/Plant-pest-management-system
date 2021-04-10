package com.zfans.ppms.service;

import com.zfans.ppms.entity.PlantAcre;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 植物地块 服务类
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
public interface PlantAcreService extends IService<PlantAcre> {

    void removeByPlantId(String id);

    void updateByPlantIdAndAcreList(Long id, List<String> acreList);
}
