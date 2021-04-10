package com.zfans.ppms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zfans.ppms.entity.Disease;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 病害 服务类
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
public interface DiseaseService extends IService<Disease> {

    IPage<Disease> selectPage(Long page, Long limit, String query);
}
