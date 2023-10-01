package com.vid.videoprocessing.video.videoconvertor.models;

import lombok.Builder;

import java.io.File;


/**
 * VideoSpecs class contains common video file properties which are used by video converting methods
 * This class also contains target video file
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Builder
public class VideoSpecBuilder {

    /**
     * Video type
     */
    @Builder.Default
    public String fileType = "mp4";

    /**
     * Video audio channel count
     */
    @Builder.Default
    public Integer channels = 2;

    /**
     * With of the video
     */
    public Integer width;

    /**
     * Height of the video
     */
    public Integer height;

    /**
     * bitrate - in bites
     */
    public Integer bitrate;

    /**
     * duration - in seconds
     */
    public Float duration;

    /**
     * Vide frame rate
     */
    public Integer fps;

    /**
     * Video itself
     */
    public File video;

    /**
     * audioCodec (should be in lowercase)
     */
    public String audioCodec;

    /**
     * Video codec (should be in lowercase)
     */
    public String videoCodec;
}