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
 * 病害
 * </p>
 *
 * @author Zfans
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Disease对象", description = "病害")
public class Disease implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "病害ID", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "受灾品种ID", example = "1")
    private Long plantId;

    @ApiModelProperty(value = "受灾地块")
    private String acre;

    @ApiModelProperty(value = "病害名称")
    private String name;

    @ApiModelProperty(value = "拉丁文名称")
    private String latin;

    @ApiModelProperty(value = "病害图片")
    private String picture;

    @ApiModelProperty(value = "防治措施")
    private String preventiveMethod;

    @ApiModelProperty(value = "症状")
    private String symptom;

    @ApiModelProperty(value = "病症原因")
    private String causeOfDisease;

    @ApiModelProperty(value = "受灾状况")
    private String disasterSituation;


}
