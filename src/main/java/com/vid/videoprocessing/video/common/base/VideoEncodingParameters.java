package com.vid.videoprocessing.video.common.base;

public abstract class VideoEncodingParameters {

    /**
     * Video codec in KBps
     */
    public String VIDEO_CODEC;

    /**
     * Audio codec in KBps
     */
    public String AUDIO_CODEC;

    /**
     * Video format i.e. mp4
     */
    public String VIDEO_FORMAT;

    /**
     * max video duration
     */
    public long MAX_VIDEO_DURATION;

    /**
     * video bitrate
     */
    public int VIDEO_BITRATE;

    /**
     * audio bitrate
     */
    public int AUDIO_BITRATE;

}