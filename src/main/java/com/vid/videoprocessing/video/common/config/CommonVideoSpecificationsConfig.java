package com.vid.videoprocessing.video.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class gets specifications from properties files
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Component
public class CommonVideoSpecificationsConfig {

    @Value("${convertor.common-video-format}")
    public String VIDEO_FORMAT;

    @Value("#{'${convertor.supported_video_formats}'.split(',')}")
    public List<String> commonVideoFormats;

    @Value("${convertor.common-frame-rate.min}")
    public int MIN_FRAME_RATE;

    @Value("${convertor.common-frame-rate.max}")
    public int MAX_FRAME_RATE;

    @Value("${convertor.image-blur}")
    public int IMAGE_BLUR;

    @Value("${convertor.audio-channels}")
    public int AUDIO_CHANNELS;

    @Value("${convertor.common-video-encoding}")
    public String VIDEO_ENCODING;

    @Value("${convertor.common-pixel-format}")
    public String PIXEL_FORMAT;

    @Value("${convertor.image-format}")
    public String IMAGE_FORMAT;

    @Value("${convertor.audio-codec}")
    public String AUDIO_CODEC;

    @Value("${convertor.audio-format}")
    public String AUDIO_FILE_FORMAT;
}
