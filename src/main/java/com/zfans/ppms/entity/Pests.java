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
 * 虫害
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Pests对象", description = "虫害")
public class Pests implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "虫害ID", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "受灾品种ID", example = "1")
    private Long plantId;

    @ApiModelProperty(value = "受灾地块")
    private String acre;

    @ApiModelProperty(value = "虫害名称")
    private String name;

    @ApiModelProperty(value = "拉丁文名称")
    private String latin;

    @ApiModelProperty(value = "虫害图片")
    private String picture;

    @ApiModelProperty(value = "科属")
    private String subject;

    @ApiModelProperty(value = "形态特征")
    private String morphologicalCharacter;

    @ApiModelProperty(value = "生活习性")
    private String lifeHabit;

    @ApiModelProperty(value = "受灾状况")
    private String disasterSituation;

    @ApiModelProperty(value = "整治方式")
    private String regulationMode;


}
