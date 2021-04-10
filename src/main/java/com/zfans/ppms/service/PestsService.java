package com.zfans.ppms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zfans.ppms.entity.Pests;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 虫害 服务类
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
public interface PestsService extends IService<Pests> {

    IPage<Pests> selectPage(Long page, Long limit, String query);
}
