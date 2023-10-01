package com.vid.videoprocessing.video.videoconvertor.models;

import lombok.Builder;
import ws.schild.jave.VideoSize;

/**
 * This object contains common video metadata properties
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Builder
public class VideoMetadata {
    /**video codec*/
    public String videoDecoder;

    /**Video bit rate (in bytes)*/
    public Integer videoBitRate;

    /**FPS*/
    public Float frameRate;

    /**video file size*/
    public VideoSize videoSize;

    /**Duration - ms*/
    public Long duration;

    /**Video format (such as .mp4, .mov...)*/
    public String format;

    /**audio bitrate*/
    public Integer audioBitRate;

    /**audio channel count (typically 2)*/
    public Integer channels;

    /**audio decoder*/
    public String audioDecoder;

    /**sampling rate*/
    public Integer samplingRate;

    /**video file size in MB*/
    public Double sizeInMegaBytes;
}