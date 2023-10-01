package com.vid.videoprocessing.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Commonly supported video specifications
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Component
public class CommonlySupportedSpecs {

    /**
     *  Video formats such as mp4, mov
     */
    @Value("#{'${convertor.supported_video_formats}'.split(',')}")
    public List<String> commonVideoFormats;

    /**
     * Audio codecs i.e. aac, mp3
     */
    @Value("#{'${convertor.supported-audio-codec-list}'.split(',')}")
    public List<String> audioCodecs;

    /**
     * Video codec list i.e. hevc, libx264
     */
    @Value("#{'${convertor.supported-video-codec-list}'.split(',')}")
    public List<String> videoCodecs;

    /**
     * Supported video bitrate values
     */
    @Value("#{'${convertor.supported-video-bitrates}'.split(',')}")
    public List<String> videoBitrates;

    /**
     * Supported values of audio bitrate
     */
    @Value("#{'${convertor.supported-audio-bitrates}'.split(',')}")
    public List<String> audioBitrates;

    /**
     * Supported video sizes
     */
    public int[][] sizes = new int[][]{{120, 120}, {480, 480}, {540, 960}, {640, 480}, {640, 640}, {960, 540}, {1280, 720}, {1280, 1280}, {1920, 1080}, {2048, 1080}, {3840, 2160}};
}
