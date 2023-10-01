package com.vid.videoprocessing.amazon.interfaces;

import java.io.IOException;

/**
 * this interface forces to implement upload method
 *
 * @author Lukacho Donadze
 */
public interface Upload {

    /**
     *
     * @param path file path
     * @return url/path of uploaded file
     */
    public String upload(String path) throws IOException;
}
