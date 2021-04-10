package com.zfans.ppms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zfans.ppms.entity.Plant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 植物 Mapper 接口
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
public interface PlantMapper extends BaseMapper<Plant> {

    List<Plant> selectPageByQuery(
            Page<Plant> pageParam,
            @Param(Constants.WRAPPER) QueryWrapper<Plant> queryWrapper);
}
