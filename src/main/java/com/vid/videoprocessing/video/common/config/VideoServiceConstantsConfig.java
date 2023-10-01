package com.vid.videoprocessing.video.common.config;

import com.vid.videoprocessing.video.common.base.VideoServiceConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Implementation of {@link VideoServiceConstants}
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Component
public class VideoServiceConstantsConfig extends VideoServiceConstants {

    /**
     * file where we put frames
     */
    @Value("${convertor.out_filename}")
    public String OUTPUT_FILE_NAME;

    /**
     * file where we put frames
     */
    @Value("${convertor.frame_folder_name}")
    public String FILE_WHERE_WE_SAVE_VIDEO_FRAMES;

    /**
     * file where we put videos
     */

    @Value("${convertor.video_folder_name}")
    public String FILE_WHERE_WE_SAVE_VIDEO;

    /**
     * audio output file
     */
    @Value("${convertor.audio_folder_name}")
    public String AUDIO_OUTPUT_FOLDER_PATH;

    /**
     * is used when any of required parameters is null
     */
    @Value("${convertor.no_required_parameters_provided}")
    public String NO_REQUIRED_PARAMETERS_PROVIDED_MESSAGE;

    /**
     * is used when provided file isn't video
     */
    @Value("${convertor.file_isnt_video}")
    public String FILE_ISNT_VIDEO_MESSAGE;

    /**
     * Format of video frames
     */
    @Value("${convertor.image-format}")
    public String IMAGE_FORMAT;

    /**
     * File is too large message
     */
    @Value("${convertor.file_is_too_large}")
    public String FILE_IS_TOO_LARGE;

    /**
     * Message - video is too short
     */
    @Value("${convertor.file_is_too_short}")
    public String VIDEO_IS_TOO_SHORT;

}
