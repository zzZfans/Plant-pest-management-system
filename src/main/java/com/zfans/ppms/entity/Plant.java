package com.zfans.ppms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 植物
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Plant对象", description = "植物")
public class Plant implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "植物ID", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "植物名称")
    private String name;

    @ApiModelProperty(value = "拉丁文名称")
    private String latin;

    @ApiModelProperty(value = "植物图片")
    private String picture;

    @ApiModelProperty(value = "植物习性")
    private String habit;

    @ApiModelProperty(value = "植物数量", example = "1")
    private Long number;

    @ApiModelProperty(value = "规格（没标的就是胸径CM）")
    private String specifications;

    @ApiModelProperty(value = "行株距")
    private String rowSpacing;

    @ApiModelProperty(value = "科")
    private String subject;

    @ApiModelProperty(value = "属")
    private String genus;


}
