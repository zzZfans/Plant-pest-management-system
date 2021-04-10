package com.zfans.ppms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zfans.ppms.entity.Plant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zfans.ppms.entity.vo.PlantVo;

/**
 * <p>
 * 植物 服务类
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
public interface PlantService extends IService<Plant> {

    Long savePlant(PlantVo plantVo);

    IPage<Plant> selectPage(Long page, Long limit, String query);
}
