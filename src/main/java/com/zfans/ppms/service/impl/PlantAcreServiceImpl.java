package com.zfans.ppms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zfans.ppms.entity.Disease;
import com.zfans.ppms.entity.Pests;
import com.zfans.ppms.entity.PlantAcre;
import com.zfans.ppms.exception.PPMSException;
import com.zfans.ppms.mapper.PlantAcreMapper;
import com.zfans.ppms.ret.ResultCodeEnum;
import com.zfans.ppms.service.DiseaseService;
import com.zfans.ppms.service.PestsService;
import com.zfans.ppms.service.PlantAcreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 植物地块 服务实现类
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@Service
public class PlantAcreServiceImpl extends ServiceImpl<PlantAcreMapper, PlantAcre> implements PlantAcreService {

    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private PestsService pestsService;

    @Override
    public void removeByPlantId(String id) {

        List<PlantAcre> plantAcreList = baseMapper.selectList(new QueryWrapper<PlantAcre>().eq("plant_id", id));

        for (PlantAcre plantAcre : plantAcreList) {
            if (diseaseService.count(new QueryWrapper<Disease>().eq("acre", plantAcre.getAcre())) != 0) {
                throw new PPMSException(ResultCodeEnum.DISEASE_EXIST_ACRE);
            }

            if (pestsService.count(new QueryWrapper<Pests>().eq("acre", plantAcre.getAcre())) != 0) {
                throw new PPMSException(ResultCodeEnum.DISEASE_EXIST_ACRE);
            }
        }

        baseMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateByPlantIdAndAcreList(Long id, List<String> acreList) {
        List<PlantAcre> plantAcreList = baseMapper.selectList(new QueryWrapper<PlantAcre>().eq("plant_id", id));

        for (String acre : acreList) {
            PlantAcre plantAcre = new PlantAcre(id, acre);
            if (!plantAcreList.contains(plantAcre)) {
                baseMapper.insert(plantAcre);
            }
        }

        for (PlantAcre plantAcre : plantAcreList) {
            if (!acreList.contains(plantAcre.getAcre())) {

                if (diseaseService.count(new QueryWrapper<Disease>().eq("acre", plantAcre.getAcre())) != 0) {
                    throw new PPMSException(ResultCodeEnum.DISEASE_EXIST_ACRE);
                }

                if (pestsService.count(new QueryWrapper<Pests>().eq("acre", plantAcre.getAcre())) != 0) {
                    throw new PPMSException(ResultCodeEnum.DISEASE_EXIST_ACRE);
                }

                baseMapper.delete(new QueryWrapper<PlantAcre>()
                        .eq("plant_id", plantAcre.getPlantId())
                        .eq("acre", plantAcre.getAcre()));
            }
        }
    }
}
