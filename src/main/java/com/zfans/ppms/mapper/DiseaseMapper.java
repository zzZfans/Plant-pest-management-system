package com.zfans.ppms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zfans.ppms.entity.Disease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 病害 Mapper 接口
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
public interface DiseaseMapper extends BaseMapper<Disease> {

    List<Disease> selectPageByQuery(Page<Disease> pageParam,
                                    @Param(Constants.WRAPPER) QueryWrapper<Disease> queryWrapper);
}
