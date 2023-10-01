package com.vid.videoprocessing.api.services;

import com.vid.videoprocessing.amazon.interfaces.Upload;
import com.vid.videoprocessing.api.config.CommonlySupportedSpecs;
import com.vid.videoprocessing.video.common.base.VideoParameters;
import com.vid.videoprocessing.video.common.base.VideoServiceConstants;
import com.vid.videoprocessing.video.platformadapter.impl.services.VideoSpecificationAdapter;
import com.vid.videoprocessing.video.videoconvertor.utils.VideoUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ws.schild.jave.EncoderException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;


/**
 * This service handles video conversion and file upload
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class VideoConvertorService {
    public static final Logger LOGGER =
            LogManager.getLogger(VideoConvertorService.class);

    final Upload awsService;
    final VideoSpecificationAdapter videoSpecificationAdapter;
    final VideoServiceConstants videoServiceConstants;
    final CommonlySupportedSpecs commonlySupportedSpecs;

    /**
     * unsupported video sizes message
     */
    public static final String VIDEO_SIZES_ARE_INVALID = "Video sizes are invalid";

    /**
     * unsupported video codec message
     */
    public static final String UNSUPPORTED_VIDEO_CODEC = "Unsupported video codec";

    /**
     * unsupported audio bitrate message
     */
    public static final String UNSUPPORTED_AUDIO_BITRATE_VALUE = "Unsupported audio bitrate value";

    /**
     * unsupported video bitrate message
     */
    public static final String UNSUPPORTED_VIDEO_BITRATE_VALUE = "Unsupported video bitrate value";

    /**
     * unsupported audio codec message
     */
    public static final String UNSUPPORTED_AUDIO_CODEC = "Unsupported audio codec";

    /**
     * unsupported video format message
     */
    public static final String UNSUPPORTED_VIDEO_FORMAT = "Unsupported video format";

    private static final int BUFFER_SIZE = 1024;

    /**
     * download started message
     */
    public static final String DOWNLOADING_STARTED = "downloading started";

    /**
     * download ended message
     */
    public static final String DOWNLOADING_ENDED = "downloading ended";

    /**
     * max supported file size
     */
    private static final int MAX_FILE_SIZE_IN_MB = 4096;

    /**
     * file where we save temporary data
     */
    @Value("${convertor.out_filename}")
    private String OUTPUT_FILE_NAME;

    /**
     * file were we temporary save videos
     */
    @Value("${convertor.video_folder_name}")
    private String VIDEO_OUTPUT_FILE;

    /**
     * audio output file
     */
    @Value("${convertor.audio_folder_name}")
    public String AUDIO_OUTPUT_FILE;

    /**
     * info message - success
     */
    public static final String OK = "User successfully converted a video";

    /**
     * @param videoUrl url of the video you want to convert to twitter specs that are
     *                 required by Facebook
     * @return s3bucket url of converted video
     * @throws IOException      if unable to process video
     * @throws EncoderException if unable to read video metadata
     */
    public String convertToFacebookFormat(String videoUrl) throws IOException, EncoderException, IllegalArgumentException, InterruptedException {
        File videoFile = download(new URL(videoUrl));

        File editedVideo = videoSpecificationAdapter.toFacebookSupportedAdVideoFormat(videoFile);

        final String url = awsService.upload(editedVideo.getPath());

        VideoUtils.cleanUpFiles(new File[]{videoFile, editedVideo});

        LOGGER.info(OK);

        return url;
    }

    /**
     * @param videoUrl url of the video you want to convert to twitter specs that are
     *                 required by Twitter
     * @return s3bucket url of converted video
     * @throws IOException      if unable to process video
     * @throws EncoderException if unable to read video metadata
     */
    public String convertToTwitterFormat(String videoUrl) throws IOException, EncoderException, IllegalArgumentException, InterruptedException {
        File videoFile = download(new URL(videoUrl));

        File editedVideo = videoSpecificationAdapter.toTwitterSupportedAdVideoFormat(videoFile);

        final String url = awsService.upload(editedVideo.getPath());

        VideoUtils.cleanUpFiles(new File[]{videoFile, editedVideo});

        LOGGER.info(OK);

        return url;
    }

    /**
     * @param videoUrl url of the video you want to convert to twitter specs that are
     *                 required by TikTok
     * @return s3bucket url of converted video
     * @throws IOException      if unable to process video
     * @throws EncoderException if unable to read video metadata
     */
    public String convertToTikTokFormat(String videoUrl) throws IOException, EncoderException, IllegalArgumentException, InterruptedException {
        File videoFile = download(new URL(videoUrl));

        File editedVideo = videoSpecificationAdapter.toTikTokSupportedAdVideoFormat(videoFile);

        final String url = awsService.upload(editedVideo.getPath());

        VideoUtils.cleanUpFiles(new File[]{videoFile, editedVideo});

        LOGGER.info(OK);

        return url;
    }

    /**
     *
     * @param videoUrl video url
     * @param videoSpecs video specifications
     * @return converted video
     */
    public String convertToCustomFormat(String videoUrl, VideoParameters videoSpecs) throws IOException, EncoderException, IllegalArgumentException, InterruptedException {
        File videoFile = download(new URL(videoUrl));

        validate(videoSpecs);

        File editedVideo = videoSpecificationAdapter.toCustomFormat(videoFile, videoSpecs);

        final String url = awsService.upload(editedVideo.getPath());

        VideoUtils.cleanUpFiles(new File[]{videoFile, editedVideo});

        LOGGER.info(OK);

        return url;
    }

    /**
     * This code downloads file from external resource and saves it locally (in our case temporarily).
     * it's memory efficient and effective
     *
     * @param url url of the file you want to download
     * @return downloaded file
     * @throws IOException in case if unable to write/read file (file is corrupted)
     */
    private File download(URL url) throws IOException, IllegalArgumentException {
        new File(getOutputFilePath()).mkdirs();

        LOGGER.info(DOWNLOADING_STARTED);

        String filename = url.getPath().substring(url.getPath().lastIndexOf('/') + 1);
        File out = new File(getOutputFilePath() + fillStringWithSillyData(filename));

        BufferedInputStream in = new BufferedInputStream(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(out);
        byte dataBuffer[] = new byte[BUFFER_SIZE];
        int bytesRead;

        while ((bytesRead = in.read(dataBuffer, 0, BUFFER_SIZE)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
        }

        fileOutputStream.close();
        LOGGER.info(DOWNLOADING_ENDED);
        return out;
    }

    /**
     *
     * @param videoParameters user-given video parameters
     * @throws IllegalArgumentException if parameters are unsupported
     */
    private void validate(VideoParameters videoParameters) throws IllegalArgumentException {
        boolean isAudioBitrateSupported = commonlySupportedSpecs.audioBitrates.stream().anyMatch(e -> (Objects.equals(e, Integer.toString(videoParameters.AUDIO_BITRATE))));
        boolean isVideoBitrateSupported = commonlySupportedSpecs.videoBitrates.stream().anyMatch(e -> (Objects.equals(e, Integer.toString(videoParameters.VIDEO_BITRATE))));
        boolean isVideoFormatSupported = commonlySupportedSpecs.commonVideoFormats.stream().anyMatch(e -> (Objects.equals(e, videoParameters.VIDEO_FORMAT)));
        boolean isVideoCodecSupported = commonlySupportedSpecs.videoCodecs.stream().anyMatch(e -> (Objects.equals(e, videoParameters.VIDEO_CODEC)));
        boolean isAudioCodecSupported = commonlySupportedSpecs.audioCodecs.stream().anyMatch(e -> (Objects.equals(e, videoParameters.AUDIO_CODEC)));


        if (videoParameters.WIDTH != null && videoParameters.HEIGHT != null) {
            boolean areDimensionsSupported = Arrays.stream(commonlySupportedSpecs.sizes).anyMatch((e) -> (e[0] == videoParameters.WIDTH && e[1] == videoParameters.HEIGHT));
            if (!areDimensionsSupported) throw new IllegalArgumentException(VIDEO_SIZES_ARE_INVALID);
        }

        if (!isVideoCodecSupported) throw new IllegalArgumentException(UNSUPPORTED_VIDEO_CODEC);

        if (!isAudioBitrateSupported) throw new IllegalArgumentException(UNSUPPORTED_AUDIO_BITRATE_VALUE);

        if (!isVideoBitrateSupported) throw new IllegalArgumentException(UNSUPPORTED_VIDEO_BITRATE_VALUE);

        if (!isVideoFormatSupported) throw new IllegalArgumentException(UNSUPPORTED_VIDEO_FORMAT);

        if (!isAudioCodecSupported) throw new IllegalArgumentException(UNSUPPORTED_AUDIO_CODEC);

    }

    /**
     * @return path to folder where we temporarily put videos
     */
    private @NonNull String getOutputFilePath() {
        return Paths.get("")
                .toAbsolutePath() + "\\" + OUTPUT_FILE_NAME + "\\" + VIDEO_OUTPUT_FILE + "\\";
    }

    /**
     * this method is used to insert some dumb data before
     * filename to prevent files from overwriting
     *
     * @param string mainly filename
     * @return string filled with numbers (in this case with current milliseconds)
     */
    private @NonNull String fillStringWithSillyData(String string) {
        return System.currentTimeMillis() + string;
    }
}
