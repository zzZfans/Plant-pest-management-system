package com.zfans.ppms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zfans.ppms.entity.Plant;
import com.zfans.ppms.entity.PlantAcre;
import com.zfans.ppms.entity.vo.PlantVo;
import com.zfans.ppms.exception.PPMSException;
import com.zfans.ppms.mapper.PlantMapper;
import com.zfans.ppms.ret.ResultCodeEnum;
import com.zfans.ppms.service.PlantAcreService;
import com.zfans.ppms.service.PlantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 植物 服务实现类
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@Service
public class PlantServiceImpl extends ServiceImpl<PlantMapper, Plant> implements PlantService {

    @Autowired
    PlantAcreService plantAcreService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long savePlant(PlantVo plantVo) {

        Plant plant = new Plant();
        BeanUtils.copyProperties(plantVo, plant);

        baseMapper.insert(plant);
        try {
            for (String acre : plantVo.getAcreList()) {
                plantAcreService.save(new PlantAcre(plant.getId(), acre));
            }
        } catch (Exception e) {
            throw new PPMSException(ResultCodeEnum.REPEAT_ACRE);
        }


        return plant.getId();
    }

    @Override
    public IPage<Plant> selectPage(Long page, Long limit, String query) {

        QueryWrapper<Plant> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");

        Page<Plant> pageParam = new Page<>(page, limit);
        //放入分页参数和查询条件参数，mp会自动组装
        List<Plant> records = baseMapper.selectPageByQuery(pageParam, queryWrapper);
        pageParam.setRecords(records);
        return pageParam;
    }
}
