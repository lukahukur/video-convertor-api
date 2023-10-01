package com.vid.videoprocessing.video.common.interfaces;


/**
 * FFmpeg config interface
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
public interface FFmpegConfig {
    /**
     *
     * @return Video Config i.e. libx264
     */
    String getVideoCodec();

    /**
     *
     * @return Audio codec i.e aac
     */
    String getAudioCodec();

    /**
     *
     * @return Video format i.e. mp4
     */
    String getVideoFormat();

    /**
     *
     * @return Video duration in seconds
     */
    long getMaxVideoDuration();

    /**
     *
     * @return Video bit rate (in kb) i.e. 2000, 8000 kb
     */
    int getVideoBitRate();

    /**
     *
     * @return Audio bit rate (in kb) i.e. 96, 128 kb
     */
    int getAudioBitRate();
}
