package com.vid.videoprocessing.video.common.base;


import com.vid.videoprocessing.video.platformadapter.data.models.VideoAspectRatio;

/**
 * This abstract class forces to implement
 * getters for video specification
 *
 * @author Lukacho Donadze
 * @version 1.1
 */
public abstract class VideoConfig extends ImageConfig {

    /**
     * @return Video codec
     */
    public abstract String getVideoCodec();

    /**
     * @return Audio codec
     */
    public abstract String getAudioCodec();

    /**
     * @return video format (typically mp4)
     */
    public abstract String getVideoFormat();

    /**
     * @return width to height ratio {@link VideoAspectRatio}
     */
    public abstract VideoAspectRatio[] getAspectRatios();

    /**
     * @return max duration
     */
    public abstract long getMaxDuration();

    /**
     *
     * @return common audio bitrate
     */
    public abstract int getAudioBitRate();

    /**
     *
     * @return common video bitrate
     */
    public abstract int getVideoBitRate();

}
