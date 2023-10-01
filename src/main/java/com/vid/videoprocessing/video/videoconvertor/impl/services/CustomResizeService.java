package com.vid.videoprocessing.video.videoconvertor.impl.services;

import com.vid.videoprocessing.video.common.base.VideoParameters;
import com.vid.videoprocessing.video.common.base.VideoServiceConstants;
import com.vid.videoprocessing.video.common.services.FFmpeg;
import com.vid.videoprocessing.video.videoconvertor.models.VideoMetadata;
import com.vid.videoprocessing.video.videoconvertor.utils.VideoUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Gives ability to set video size directly, without involving optimal video size calculation algo
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
public class CustomResizeService {

    public static final Logger LOGGER = LogManager.getLogger(CustomResizeService.class);

    /**
     * Resize requested message
     */
    public static final String CUSTOM_RESIZING_REQUESTED = "Custom resizing requested";

    /**
     * user did not resize image
     */
    public static final String USER_DID_NOT_RESIZE_IMAGE = "User did not resize image";

    /**
     * Requested video parameters
     */
    VideoParameters videoParameters;

    /**
     * Video to convert
     */
    final File originalVideo;

    /**
     * messages, filenames, formats
     */
    final VideoServiceConstants constants;

    /**
     * @param videoParameters video parameters
     * @param video           original video
     * @param constants       error messages, filenames, namings
     * @throws EncoderException if unable to read file
     */
    public CustomResizeService(VideoParameters videoParameters, File video, VideoServiceConstants constants) throws EncoderException {
        if (!VideoUtils.isFileVideo(video)) {
            VideoUtils.cleanUpFiles(new File[]{video});
            throw new IllegalArgumentException(constants.FILE_ISNT_VIDEO_MESSAGE);
        }

        VideoMetadata metadata = VideoUtils.getVideoMetadata(video);

        final int MIN_DURATION = 1;

        if (metadata.duration < MIN_DURATION) {
            throw new IllegalArgumentException(constants.VIDEO_IS_TOO_SHORT);
        }

        this.videoParameters = videoParameters;
        this.constants = constants;
        originalVideo = video;
    }

    /**
     * @return converted video
     * @throws EncoderException     if unable to get metadata
     * @throws IOException          if unable to perform reading/writing
     * @throws InterruptedException process error
     */
    public File convert() throws EncoderException, IOException, InterruptedException {
        LOGGER.info(CUSTOM_RESIZING_REQUESTED);

        FFmpeg ffmpeg = new FFmpeg(videoParameters);

        VideoMetadata videoMetadata = VideoUtils.getVideoMetadata(originalVideo);

        LOGGER.info("Original width - " + videoMetadata.videoSize.getWidth() + " Height - " + videoMetadata.videoSize.getHeight());

        if (videoParameters.WIDTH == null || videoParameters.HEIGHT == null) {
            LOGGER.info(USER_DID_NOT_RESIZE_IMAGE);
            return ffmpeg.convertVideoWithoutResizing(
                    Paths.get(originalVideo.getPath()),
                    Paths.get(getVideoOutputDirectory())
            );
        }

        LOGGER.info("Output width - " + videoParameters.WIDTH + " Height - " + videoParameters.HEIGHT);

        return ffmpeg.resizeVideo(
                Paths.get(originalVideo.getPath()),
                Paths.get(getVideoOutputDirectory()),
                videoParameters.WIDTH,
                videoParameters.HEIGHT
        );
    }

    /**
     * @return Folder where we put videos
     */
    public String getVideoOutputDirectory() {
        return getOutputFilePath() + constants.FILE_WHERE_WE_SAVE_VIDEOS + "\\";
    }


    /**
     * @return video frames folders path
     */
    protected String getOutputFilePath() {
        return Paths.get("")
                .toAbsolutePath() + "\\" + constants.OUTPUT_FILE_NAME + "\\";
    }
}
