package com.sinker.lookworld.config;


import cn.dev33.satoken.config.SaCookieConfig;
import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.dao.SaTokenDaoRedis;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * BelongsProject: lookworld
 * BelongsPackage: com.sinker.lookworld.config
 * Author: sinker
 * CreateTime: 2025-03-12  11:38
 * Description: 我亦无他,唯手熟耳
 * Version: 1.0
 */

@Configuration
public class SaTokenConfigs {

    @Bean
    public StpLogic getStpLogicJwt(){
        return new StpLogicJwtForSimple();
    }

}
