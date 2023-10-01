package com.vid.videoprocessing.video.platformadapter.data;


import com.vid.videoprocessing.video.platformadapter.data.models.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Supported video properties formats
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Data
@Document
@Getter
@Builder
public class SocialNetworkVideoSpecs {
    @Id
    private String id;

    /**you can check namings in socialNetwork.properties*/
    @Indexed(unique = true)
    private String socialNetwork;

    /**
     * Video (frame) aspect ratio - 9:16, 1:1, 16:9, 4:5 ...
     */
    private VideoDimensions videoDimensions;

    /**
     * Supported video formats - like mp4, mov...
     */
    private String[] videoFormats;

    /**
     * Audio bit rate range - typically from 96kbps to 120kbps
     */
    private AudioBitRateRange audioBitRateRange;

    /**
     * in Kbps
     */
    private VideoBitRateRange videoBitRateRange;

    /**
     * supported aspect ratios
     */
    private VideoAspectRatio[] videoAspectRatios;

    /**
     * All accepted audio codecs
     */
    private String[] audioCodecs;

    /**
     * All accepted video codecs
     */
    private String[] videoCodecs;

    /**
     * Min and Max FPS values
     */
    private FrameRateRange frameRateRange;

    /**
     * duration - Seconds
     */
    private VideoDuration videoDurationRange;

    /**
     * maxFileSize - in MegaBytes
     */
    private Long maxFileSize;

    /**
     * Audio channels count - typically 2
     */
    private Integer audioChannels;

    /**
     * Common video resolutions (like 1280X720; 1920X1080;)
     */
    private CommonResolutions[] commonResolutions;

    /**
     * Video bit rate common
     */
    private int audioBitRate;

    /**
     * Audio bit rate common
     */
    private int videoBitRate;
}
