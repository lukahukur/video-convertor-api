package com.vid.videoprocessing.api.controllers;


import com.vid.videoprocessing.api.config.CommonlySupportedSpecs;
import com.vid.videoprocessing.api.dto.VideoSpecificationDto;
import com.vid.videoprocessing.video.common.base.VideoConfig;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is responsible for sending social network specifications
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@RestController
@RequestMapping(value = "api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class SocialNetworkVideoSpecificationsController {
    public static final Logger LOGGER =
            LogManager.getLogger(SocialNetworkVideoSpecificationsController.class);

    public final VideoConfig getFacebookConfig;
    public final VideoConfig getTikTokConfig;
    public final VideoConfig getTwitterConfig;
    public final CommonlySupportedSpecs supportedSpecs;

    /**
     * @return Facebook ad video supported specifications
     */
    @GetMapping("/facebookConfig")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<VideoSpecificationDto> facebookConfig() {
        return ResponseEntity.ok().body(
                VideoSpecificationDto.factory(getFacebookConfig)
        );
    }


    /**
     * @return Twitter ad video supported specifications
     */
    @GetMapping("/twitterConfig")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<VideoSpecificationDto> twitterConfig() {
        return ResponseEntity.ok().body(
                VideoSpecificationDto.factory(getTwitterConfig)
        );
    }

    /**
     * @return TikTok ad video supported specifications
     */
    @GetMapping("/tiktokConfig")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<VideoSpecificationDto> tiktokConfig() {
        return ResponseEntity.ok().body(
                VideoSpecificationDto.factory(getTikTokConfig)
        );
    }

    /**
     *
     * @return supported video specs
     */
    @GetMapping("/supportedSpecs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<CommonlySupportedSpecs> supportedSpecs() {
        return ResponseEntity.ok().body(supportedSpecs);
    }
}
