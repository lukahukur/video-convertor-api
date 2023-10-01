package com.vid.videoprocessing.video.platformadapter.impl.services;


import com.vid.videoprocessing.video.common.base.VideoConfig;
import com.vid.videoprocessing.video.common.base.VideoParameters;
import com.vid.videoprocessing.video.common.base.VideoServiceConstants;
import com.vid.videoprocessing.video.platformadapter.interfaces.VideoAdapter;
import com.vid.videoprocessing.video.videoconvertor.impl.services.CustomResizeService;
import com.vid.videoprocessing.video.videoconvertor.impl.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.IOException;

/**
 * This class implements {@link VideoAdapter} and
 * encapsulates the logic of video conversion
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class VideoSpecificationAdapter implements VideoAdapter {

    public final VideoServiceConstants videoServiceConstants;
    private final VideoConfig getFacebookConfig;
    private final VideoConfig getTikTokConfig;
    private final VideoConfig getTwitterConfig;

    /**
     * @param video Facebook ad video
     * @return Edited video
     */

    @Override
    public File
    toFacebookSupportedAdVideoFormat(File video) throws EncoderException, IOException, IllegalArgumentException, InterruptedException {
        VideoService videoService = new VideoService(video, getFacebookConfig, videoServiceConstants);

        return videoService.convert();
    }

    /**
     * @param video twitter ad video
     * @return Edited video
     */
    @Override
    public File
    toTwitterSupportedAdVideoFormat(File video) throws EncoderException, IOException, IllegalArgumentException, InterruptedException {
        VideoService videoService = new VideoService(video, getTwitterConfig, videoServiceConstants);

        return videoService.convert();
    }

    /**
     * @param video TikTok ad video
     * @return Edited video
     */
    @Override
    public File
    toTikTokSupportedAdVideoFormat(File video) throws EncoderException, IOException, IllegalArgumentException, InterruptedException {
        VideoService videoService = new VideoService(video, getTikTokConfig, videoServiceConstants);

        return videoService.convert();
    }

    /**
     * Converts video to any custom format
     *
     * @param video Video to edit
     * @return Edited video
     */
    public File
    toCustomFormat(File video, VideoParameters customConfig) throws EncoderException, IOException, IllegalArgumentException, InterruptedException {
        CustomResizeService videoService = new CustomResizeService(customConfig, video, videoServiceConstants);

        return videoService.convert();
    }
}

