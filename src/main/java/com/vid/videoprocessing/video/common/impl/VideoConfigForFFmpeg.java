package com.vid.videoprocessing.video.common.impl;

import com.vid.videoprocessing.video.common.base.VideoConfig;
import com.vid.videoprocessing.video.common.interfaces.FFmpegConfig;

/**
 * Used to pass video config to FFmpeg constructor
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
public class VideoConfigForFFmpeg implements FFmpegConfig {

    private final VideoConfig videoConfig;
    public VideoConfigForFFmpeg(VideoConfig videoConfig){
            this.videoConfig = videoConfig;
    }

    /**
     * @return Video Config i.e. libx264
     */
    @Override
    public String getVideoCodec() {
        return videoConfig.getVideoCodec();
    }

    /**
     * @return Audio codec i.e aac
     */
    @Override
    public String getAudioCodec() {
        return videoConfig.getAudioCodec();
    }

    /**
     * @return Video format i.e. mp4
     */
    @Override
    public String getVideoFormat() {
        return videoConfig.getVideoFormat();
    }

    /**
     * @return Video duration in seconds
     */
    @Override
    public long getMaxVideoDuration() {
        return videoConfig.getMaxDuration();
    }

    /**
     * @return Video bit rate (in kb) i.e. 2000, 8000 kb
     */
    @Override
    public int getVideoBitRate() {
        return videoConfig.getVideoBitRate();
    }

    /**
     * @return Audio bit rate (in kb) i.e. 96, 128 kb
     */
    @Override
    public int getAudioBitRate() {
        return videoConfig.getAudioBitRate();
    }
}
