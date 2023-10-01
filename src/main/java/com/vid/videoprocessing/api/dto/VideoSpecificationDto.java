package com.vid.videoprocessing.api.dto;

import com.vid.videoprocessing.video.common.base.VideoConfig;
import com.vid.videoprocessing.video.platformadapter.data.models.VideoAspectRatio;

/**
 * This DTO is used to transfer video specifications
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
public class VideoSpecificationDto {

    /**
     * Video bitrate
     */
    public int VIDEO_BITRATE;

    /**
     * Audio bitrate
     */
    public int AUDIO_BITRATE;

    /**
     * Video Codec
     */
    public String VIDEO_CODEC;

    /**
     * Audio Codec
     */
    public String AUDIO_CODEC;

    /**
     * Min width
     */
    public int MIN_WIDTH;

    /**
     * Min height
     */
    public int MIN_HEIGHT;

    /**
     * Max video duration
     */
    public long MAX_VIDEO_DURATION;

    /**
     * Video format i.e. mp4
     */
    public String VIDEO_FORMAT;

    /**
     * Max width
     */
    public int MAX_WIDTH;

    /**
     * List of supported aspect ratios
     */
    public VideoAspectRatio[] SUPPORTED_ASPECT_RATIOS;

    /**
     *
     * @param config Video Config
     */
    public VideoSpecificationDto(VideoConfig config) {
        VIDEO_BITRATE = config.getVideoBitRate();
        AUDIO_BITRATE = config.getAudioBitRate();
        VIDEO_CODEC = config.getVideoCodec();
        AUDIO_CODEC = config.getAudioCodec();
        MIN_WIDTH = config.getImageMinWidth();
        MIN_HEIGHT = config.getImageMinHeight();
        MAX_VIDEO_DURATION = config.getMaxDuration();
        SUPPORTED_ASPECT_RATIOS = config.getAspectRatios();
        VIDEO_FORMAT = config.getVideoFormat();
        MAX_WIDTH = config.getImageMaxWidth();
    }

    /**
     * @param config Video config
     * @return instance of {@link VideoSpecificationDto}
     */
    public static VideoSpecificationDto factory(VideoConfig config) {
        return new VideoSpecificationDto(config);
    }
}
