package com.vid.videoprocessing.video.platformadapter.config;

import com.vid.videoprocessing.video.common.config.CommonVideoSpecificationsConfig;
import com.vid.videoprocessing.video.platformadapter.impl.services.RequiredVideoSpecsFromDatabase;
import com.vid.videoprocessing.video.platformadapter.repository.SocialNetworkVideoSpecsRepository;
import com.vid.videoprocessing.video.common.base.VideoConfig;
import com.vid.videoprocessing.video.common.config.RequiredVideoSpecs;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This configuration class implements {@link VideoConfig}
 * beans filled with video specifications
 * for each supported social network (platform)
 * from database (mongoDB) and configuration files
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class SocialNetworkConfigs {

    /**
     * this is
     * {@link SocialNetworkVideoSpecsRepository} wrapper class
     * that makes easier to get social network specifications from database
     */
    final RequiredVideoSpecsFromDatabase requiredSpecs;

    /**
     * Common specifications from properties files
     */
    final CommonVideoSpecificationsConfig commonSpecs;

    /**
     * Facebook video specs
     *
     * @return {@link RequiredVideoSpecs} filled with specs from database and property files
     */
    @Bean
    public VideoConfig getFacebookConfig() {
        return new RequiredVideoSpecs(requiredSpecs.getFacebookVideoRequiredSpecs(), commonSpecs);
    }

    /**
     * TikTok video specs
     *
     * @return {@link RequiredVideoSpecs} filled with specs from database and property files
     */
    @Bean
    public VideoConfig getTikTokConfig() {
        return new RequiredVideoSpecs(requiredSpecs.getTikTokVideoRequiredSpecs(), commonSpecs);
    }

    /**
     * Twitter video specs
     *
     * @return {@link RequiredVideoSpecs} filled with specs from database and property files
     */
    @Bean
    public VideoConfig getTwitterConfig() {
        return new RequiredVideoSpecs(requiredSpecs.getTwitterVideoRequiredSpecs(), commonSpecs);
    }
}
