package com.zfans.ppms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zfans.ppms.entity.Pests;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 虫害 Mapper 接口
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
public interface PestsMapper extends BaseMapper<Pests> {

    List<Pests> selectPageByQuery(Page<Pests> pageParam,
                                  @Param(Constants.WRAPPER) QueryWrapper<Pests> queryWrapper);
}
