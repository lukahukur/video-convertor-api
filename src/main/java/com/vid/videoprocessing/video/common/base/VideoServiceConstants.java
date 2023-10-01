package com.vid.videoprocessing.video.common.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Constants for video service.
 * Will be implemented by Bean
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Component
public abstract class VideoServiceConstants {

    /**
     * file where we put frames
     */
    @Value("${convertor.out_filename}")
    public String OUTPUT_FILE_NAME;


    /**
     * file where we put videos
     */
    @Value("${convertor.video_folder_name}")
    public String FILE_WHERE_WE_SAVE_VIDEOS;


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
