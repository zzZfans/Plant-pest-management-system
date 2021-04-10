package com.zfans.ppms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zfans.ppms.entity.Disease;
import com.zfans.ppms.entity.Plant;
import com.zfans.ppms.mapper.DiseaseMapper;
import com.zfans.ppms.service.DiseaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 病害 服务实现类
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease> implements DiseaseService {

    @Override
    public IPage<Disease> selectPage(Long page, Long limit, String query) {
        QueryWrapper<Disease> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");

        Page<Disease> pageParam = new Page<>(page, limit);
        //放入分页参数和查询条件参数，mp会自动组装
        List<Disease> records = baseMapper.selectPageByQuery(pageParam, queryWrapper);
        pageParam.setRecords(records);
        return pageParam;
    }
}
