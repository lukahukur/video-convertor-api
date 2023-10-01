package com.vid.videoprocessing.video.videoconvertor.impl.services;

import com.vid.videoprocessing.video.common.base.VideoConfig;
import com.vid.videoprocessing.video.common.base.VideoServiceConstants;
import com.vid.videoprocessing.video.common.impl.VideoConfigForFFmpeg;
import com.vid.videoprocessing.video.common.services.FFmpeg;
import com.vid.videoprocessing.video.videoconvertor.interfaces.VideoConvertor;
import com.vid.videoprocessing.video.videoconvertor.models.VideoMetadata;
import com.vid.videoprocessing.video.videoconvertor.utils.VideoUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ws.schild.jave.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * Used to convert video to specific formats.
 * <p>
 * In the first version resizing is not supported yet.
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
public class VideoService extends VideoProcessor implements VideoConvertor {

    public static final Logger LOGGER = LogManager.getLogger(VideoService.class);

    /**
     * Original video needs resize message
     */
    public static final String USER_VIDEO_NEEDS_RESIZE_MSG = "User video needs resize";

    /**
     *  Original video does not need resize message
     */
    public static final String USER_VIDEO_DOES_NOT_NEED_RESIZE_MSG = "User video does not need resize";

    /**
     * Video properties
     */
    private final VideoConfig VIDEO_CONFIG;

    public VideoService(File video, VideoConfig videoSpecs, VideoServiceConstants constants) throws EncoderException, IllegalArgumentException {
        super(video, videoSpecs, constants);
        this.VIDEO_CONFIG = videoSpecs;
    }

    /**
     * decides which conversion strategy is better for conversion - <br>
     * fast without resizing, or relatively slow (but still fast) - with resize
     *
     * @return Edited video file
     */
    @Override
    public File convert() throws EncoderException, IOException, InterruptedException {
        int width = videoMetadata.videoSize.getWidth();
        int height = videoMetadata.videoSize.getHeight();

        if (!isCorrectRatio() ||
                width < MIN_WIDTH ||
                height < MIN_HEIGHT
        ) return convertAndResize();


        return convertWithoutResizing();
    }


    /**
     * This method resizes video and blurs background
     * <p>
     * Uses ffmpeg under the hood
     *
     * @return edited video file
     */
    private File convertAndResize() throws EncoderException, IOException, InterruptedException {
        LOGGER.info(USER_VIDEO_NEEDS_RESIZE_MSG);
        FFmpeg ffmpeg = new FFmpeg(new VideoConfigForFFmpeg(VIDEO_CONFIG));
        VideoMetadata videoMetadata = VideoUtils.getVideoMetadata(originalVideo);
        int[] sizes = imageService.calculateOptimalImageSize(videoMetadata.videoSize.getWidth(), videoMetadata.videoSize.getHeight());

        LOGGER.info("Original width - " + videoMetadata.videoSize.getWidth() + " Height - " + videoMetadata.videoSize.getHeight());
        LOGGER.info("Optimal width - " + sizes[0] + " Height - " + sizes[1]);

        return ffmpeg.resizeVideo(
                Paths.get(originalVideo.getPath()),
                Paths.get(getVideoOutputDirectory()),
                sizes[0],
                sizes[1]
        );
    }

    /**
     * This method is used in situation when aspect ratio and image size correction
     * are not needed.
     *
     * @return edited video file
     */
    private File convertWithoutResizing() throws EncoderException, IOException, InterruptedException {
        LOGGER.info(USER_VIDEO_DOES_NOT_NEED_RESIZE_MSG);
        FFmpeg ffmpeg = new FFmpeg(new VideoConfigForFFmpeg(VIDEO_CONFIG));
        VideoMetadata videoMetadata = VideoUtils.getVideoMetadata(originalVideo);

        LOGGER.info("Original width - " + videoMetadata.videoSize.getWidth() + " Height - " + videoMetadata.videoSize.getHeight());

        return ffmpeg.convertVideoWithoutResizing(
                Paths.get(originalVideo.getPath()),
                Paths.get(getVideoOutputDirectory())
        );
    }
}
