package com.vid.videoprocessing.video.videoconvertor.impl.services;


import com.vid.videoprocessing.video.common.base.VideoConfig;
import com.vid.videoprocessing.video.common.base.VideoServiceConstants;
import com.vid.videoprocessing.video.platformadapter.data.models.VideoAspectRatio;
import com.vid.videoprocessing.video.videoconvertor.models.VideoMetadata;
import com.vid.videoprocessing.video.videoconvertor.utils.VideoUtils;
import ws.schild.jave.EncoderException;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;


/**
 * This class processes video in case if video aspect ratio and sizes are
 * not supported by social network
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
public class VideoProcessor {

    /**
     * most popular aspect ratio
     */
    public static final int MOST_COMMON_ASPECT_RATIO_SUPPORTED_BY_EVERY_PLATFORM = 1;


    /**
     * {@link ImageService} class instance
     */
    protected final ImageService imageService;

    /**
     * metadata of a video
     * f.e. audio bitrate, frames per second, encoding
     */
    protected final VideoMetadata videoMetadata;

    /**
     * non-edited user video
     */
    protected final File originalVideo;

    /**
     * constants
     */
    protected final VideoServiceConstants CONSTANTS;

    /**
     * read {@link #getVideoOptimalRatio(float)}
     */
    protected final float OPTIMAL_IMAGE_RATIO;

    /**
     * Min width
     */
    protected final int MIN_WIDTH;

    /**
     * Min height
     */
    protected final int MIN_HEIGHT;

    /**
     * supported aspect ratios
     */
    protected final VideoAspectRatio[] ASPECT_RATIOS;

    //TODO - handle tooo long videos #getVideoFrameCount
    public VideoProcessor(File video, VideoConfig videoSpecs, VideoServiceConstants CONSTANTS) throws EncoderException, IllegalArgumentException {
        if (!VideoUtils.isFileVideo(video)) {
            VideoUtils.cleanUpFiles(new File[]{video});
            throw new IllegalArgumentException(CONSTANTS.FILE_ISNT_VIDEO_MESSAGE);
        }

        VideoMetadata metadata = VideoUtils.getVideoMetadata(video);

        final int MIN_DURATION = 1;

        if (metadata.duration < MIN_DURATION) {
            throw new IllegalArgumentException(CONSTANTS.VIDEO_IS_TOO_SHORT);
        }

        float originalImageRatio = (float) metadata.videoSize.getWidth() / metadata.videoSize.getHeight();

        this.CONSTANTS = CONSTANTS;
        originalVideo = video;
        videoMetadata = metadata;
        ASPECT_RATIOS = videoSpecs.getAspectRatios();
        MIN_WIDTH = videoSpecs.getImageMinWidth();
        MIN_HEIGHT = videoSpecs.getImageMinHeight();
        OPTIMAL_IMAGE_RATIO = getVideoOptimalRatio(originalImageRatio);
        imageService = new ImageService(videoSpecs, OPTIMAL_IMAGE_RATIO);
    }

    /**
     * checks if video aspect ratio matches video specifications
     *
     * @return boolean
     */
    protected boolean isCorrectRatio() {
        return imageService.isCorrectRatio(videoMetadata.videoSize.getWidth(),
                videoMetadata.videoSize.getHeight(),
                OPTIMAL_IMAGE_RATIO
        );
    }


    /**
     * This method calculates the most optimal aspect ratio of every supported one
     *
     * @param imageRatio original image ratio
     * @return most optimal aspect ratio
     */
    public float getVideoOptimalRatio(float imageRatio) {
        if (imageRatio > highestAspectRatio(ASPECT_RATIOS)) return MOST_COMMON_ASPECT_RATIO_SUPPORTED_BY_EVERY_PLATFORM;

        float distanceNearest = Math.abs(ratioInFloat(ASPECT_RATIOS[0]) - imageRatio);
        float nearestRatio = ratioInFloat(ASPECT_RATIOS[0]);

        for (VideoAspectRatio ratio : ASPECT_RATIOS) {
            assert ratio != null;
            float currentDistance = Math.abs(ratioInFloat(ratio) - imageRatio);


            if (currentDistance < distanceNearest) {
                distanceNearest = currentDistance;
                nearestRatio = ratioInFloat(ratio);
            }

        }

        return nearestRatio;
    }

    /**
     * @param aspectRatio video aspect ratio object
     * @return aspect ratio in float
     */
    private static float ratioInFloat(VideoAspectRatio aspectRatio) {
        return (float) aspectRatio.width / aspectRatio.height;
    }

    /**
     * @param aspectRatios array of supported aspect ratios
     * @return highest supported video aspect ratio (w/h)
     */
    private static float highestAspectRatio(VideoAspectRatio[] aspectRatios) {
        Optional<VideoAspectRatio> aspectRatio = Arrays.stream(aspectRatios).max(Comparator.comparingDouble(a -> ((double) a.width / a.height)));

        return (float) aspectRatio.get().width / aspectRatio.get().height;
    }


    /**
     * @return Folder where we put videos
     */
    public String getVideoOutputDirectory() {
        return getOutputFilePath() + CONSTANTS.FILE_WHERE_WE_SAVE_VIDEOS + "\\";
    }


    /**
     * @return video frames folders path
     */
    protected String getOutputFilePath() {
        return Paths.get("")
                .toAbsolutePath() + "\\" + CONSTANTS.OUTPUT_FILE_NAME + "\\";
    }

}