package com.zfans.ppms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 植物地块
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "PlantAcre对象", description = "植物地块")
public class PlantAcre implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "植物ID", example = "1")
    @TableId(value = "plant_id", type = IdType.INPUT)
    private Long plantId;

    @ApiModelProperty(value = "地块")
    private String acre;
}
