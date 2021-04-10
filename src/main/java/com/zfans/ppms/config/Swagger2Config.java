package com.zfans.ppms.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @ClassName Swagger2Config
 * @Author Zfans
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket apiConfig() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .apiInfo(apiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
                .paths(Predicates.and(PathSelectors.regex("/.*")))
                .build();

    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("植物病虫害管理系统-API文档")
                .description("本文档描述了植物病虫害管理系统服务接口定义")
                .version("1.0")
                .build();
    }
}
