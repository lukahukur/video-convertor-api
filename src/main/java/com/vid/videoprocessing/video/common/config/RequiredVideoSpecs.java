package com.vid.videoprocessing.video.common.config;

import com.vid.videoprocessing.video.common.base.VideoConfig;
import com.vid.videoprocessing.video.platformadapter.data.SocialNetworkVideoSpecs;
import com.vid.videoprocessing.video.platformadapter.data.models.VideoAspectRatio;


/**
 * This class combines video Specifications from database and from properties file
 * read description for getters in {@link VideoConfig}
 *
 * @author Lukacho Donadze
 */
public class RequiredVideoSpecs extends VideoConfig {

    SocialNetworkVideoSpecs socialNetworkVideoSpecs;

    private final String COMMON_VIDEO_FORMAT;
    private final String VIDEO_ENCODING;

    private final String AUDIO_ENCODING;

    public RequiredVideoSpecs(SocialNetworkVideoSpecs socialNetworkVideoSpecs, CommonVideoSpecificationsConfig config) {
        this.socialNetworkVideoSpecs = socialNetworkVideoSpecs;

        COMMON_VIDEO_FORMAT = config.VIDEO_FORMAT;
        VIDEO_ENCODING = config.VIDEO_ENCODING;
        AUDIO_ENCODING = config.AUDIO_CODEC;
    }


    @Override
    public int getImageMinWidth() {
        return socialNetworkVideoSpecs.getVideoDimensions().width.min;
    }

    @Override
    public int getImageMinHeight() {
        return socialNetworkVideoSpecs.getVideoDimensions().height.min;
    }

    @Override
    public int getImageMaxWidth() {
        return socialNetworkVideoSpecs.getVideoDimensions().width.max;
    }


    @Override
    public String getVideoCodec() {
        return VIDEO_ENCODING;
    }

    @Override
    public String getAudioCodec() {
        return AUDIO_ENCODING;
    }

    @Override
    public String getVideoFormat() {
        return COMMON_VIDEO_FORMAT;
    }

    @Override
    public long getMaxDuration() {
        return socialNetworkVideoSpecs.getVideoDurationRange().max;
    }

    @Override
    public int getAudioBitRate() {
        return socialNetworkVideoSpecs.getAudioBitRate();
    }

    @Override
    public int getVideoBitRate() {
        return socialNetworkVideoSpecs.getVideoBitRate();
    }

    @Override
    public VideoAspectRatio[] getAspectRatios() {
        return socialNetworkVideoSpecs.getVideoAspectRatios();
    }
}
