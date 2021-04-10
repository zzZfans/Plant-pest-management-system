package com.zfans.ppms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zfans.ppms.entity.Pests;
import com.zfans.ppms.entity.Pests;
import com.zfans.ppms.mapper.PestsMapper;
import com.zfans.ppms.service.PestsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 虫害 服务实现类
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@Service
public class PestsServiceImpl extends ServiceImpl<PestsMapper, Pests> implements PestsService {

    @Override
    public IPage<Pests> selectPage(Long page, Long limit, String query) {
        QueryWrapper<Pests> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");

        Page<Pests> pageParam = new Page<>(page, limit);
        //放入分页参数和查询条件参数，mp会自动组装
        List<Pests> records = baseMapper.selectPageByQuery(pageParam, queryWrapper);
        pageParam.setRecords(records);
        return pageParam;
        
    }
}
