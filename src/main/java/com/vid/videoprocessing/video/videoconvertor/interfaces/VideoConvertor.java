package com.vid.videoprocessing.video.videoconvertor.interfaces;

import com.vid.videoprocessing.video.videoconvertor.models.VideoSpecBuilder;


import java.io.File;

/**
 * This interface forces user to implement convert method
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
public interface VideoConvertor {
    /**
     *
     * @return edited video file
     * @throws Exception if unable to process video
     */
    File convert() throws Exception;

}
