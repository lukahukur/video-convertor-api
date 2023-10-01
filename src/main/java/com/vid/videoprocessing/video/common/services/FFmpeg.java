package com.vid.videoprocessing.video.common.services;

import com.vid.videoprocessing.video.common.base.VideoEncodingParameters;
import com.vid.videoprocessing.video.common.interfaces.FFmpegConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Class that implements video processing methods that uses ffmpeg under the hood
 *
 * @author Lukacho Donadze
 * @version 1.1
 */
public class FFmpeg {
    public static final Logger LOGGER =
            LogManager.getLogger(FFmpeg.class);

    /**
     * KB to BYTE multiplier
     */
    private static final int KB_TO_BYTE = 1000;

    /**
     * process started message
     */
    private static final String PROCESS_STARTED = "Process started";

    /**
     * success message (video composer)
     */
    private static final String SUCCESS_MSG_COMPOSE_VIDEO = "Video converted successfully";

    /**
     * error message (video composer)
     */
    public static final String ERROR_MSG_COMPOSE_VIDEO = "Error occurred during video creation";


    /**
     * Video codec
     */
    private final String VIDEO_CODEC;

    /**
     * audio codec
     */
    private final String AUDIO_CODEC;

    /**
     * video format (i.e. mp4)
     */
    private final String VIDEO_FORMAT;

    /**
     * max video duration
     */
    private final long MAX_VIDEO_DURATION;

    /**
     * maximum thread count <br>
     * used to prevent server from exploding
     */
    private final byte MAX_THREAD_COUNT = 1;

    /**
     * Max video bitrate
     */
    private final int VIDEO_BITRATE;

    /**
     * Max audio bitrate
     */
    private final int AUDIO_BITRATE;

    /**
     * @param config parameters that would be applied to video
     */
    public FFmpeg(FFmpegConfig config) {
        VIDEO_CODEC = config.getVideoCodec();
        AUDIO_CODEC = config.getAudioCodec();
        VIDEO_FORMAT = config.getVideoFormat();
        MAX_VIDEO_DURATION = config.getMaxVideoDuration();
        VIDEO_BITRATE = config.getVideoBitRate();
        AUDIO_BITRATE = config.getAudioBitRate();
    }

    /**
     *
     * @param videoParameters parameters that would be applied to video
     */
    public FFmpeg(VideoEncodingParameters videoParameters){
        VIDEO_CODEC = videoParameters.VIDEO_CODEC;
        AUDIO_CODEC =  videoParameters.AUDIO_CODEC;
        VIDEO_FORMAT =  videoParameters.VIDEO_FORMAT;
        MAX_VIDEO_DURATION =  videoParameters.MAX_VIDEO_DURATION;
        VIDEO_BITRATE =  videoParameters.VIDEO_BITRATE;
        AUDIO_BITRATE =  videoParameters.AUDIO_BITRATE;
    }

    /**
     * This method handles video resize by upscaling and blurring background and then gluing original video on top of that blurred background video.
     *
     * @param videoPath video path
     * @param outPath   output path
     * @param width     new width
     * @param height    new height
     * @return edited video
     * @throws IOException          if unable to perform reading/writing
     * @throws InterruptedException if the current thread is interrupted by another thread while it is waiting
     */
    public File resizeVideo(Path videoPath, Path outPath, int width, int height) throws IOException, InterruptedException {

        File outFile = new File(outPath.toString() + "/" + System.currentTimeMillis() + "." + VIDEO_FORMAT);

        final long StartTime = System.currentTimeMillis();

        LOGGER.info("Filename - " + outFile.getName() +
                ", threads - " + MAX_THREAD_COUNT +
                ", Video Bitrate - " + VIDEO_BITRATE +
                "k, Audio Bitrate - " + AUDIO_BITRATE +
                "k, Audio Codec - " + AUDIO_CODEC +
                ", Video Codec - " + VIDEO_CODEC +
                ", Dimensions - " + width + "/" + height +
                ", Max Duration " + convertSecondsToTime(MAX_VIDEO_DURATION)
        );

        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-ss", "00:00:00",
                "-to", convertSecondsToTime(MAX_VIDEO_DURATION),
                "-i", videoPath.toString(),
                "-threads", Byte.toString(MAX_THREAD_COUNT),
                "-b:v", Integer.toString(VIDEO_BITRATE * KB_TO_BYTE),
                "-b:a", Integer.toString(AUDIO_BITRATE * KB_TO_BYTE),
                "-c:a", AUDIO_CODEC,
                "-c:v", VIDEO_CODEC,
                "-lavfi", String.format("[0:v]scale=%d:%d,setsar=1,boxblur=luma_radius=min(h\\,w)/20:luma_power=1:chroma_radius=min(cw\\,ch)/20:chroma_power=1[bg];[bg][0:v]overlay=(W-w)/2:(H-h)/2", width, height),
                "-loglevel", "quiet",
                outFile.getPath()
        );

        handleProcessBuilder(processBuilder, PROCESS_STARTED, SUCCESS_MSG_COMPOSE_VIDEO, ERROR_MSG_COMPOSE_VIDEO);

        LOGGER.info("Process took - " + ((System.currentTimeMillis() - StartTime) / 1000) + " Seconds");

        return outFile;
    }

    /**
     * This method makes target video fit into all specifications, except aspect ratio and size.
     * If you want to upscale a video, please check {@link #convertVideoWithoutResizing(Path, Path)}
     *
     * @param videoPath video path
     * @param outPath   output path
     * @return edited video
     * @throws IOException          if unable to perform reading/writing
     * @throws InterruptedException if the current thread is interrupted by another thread while it is waiting
     */
    public File convertVideoWithoutResizing(Path videoPath, Path outPath) throws IOException, InterruptedException {

        File outFile = new File(outPath.toString() + "/" + System.currentTimeMillis() + "." + VIDEO_FORMAT);

        LOGGER.info("Filename - " + outFile.getName() +
                ", threads - " + MAX_THREAD_COUNT +
                ", Video Bitrate - " + VIDEO_BITRATE +
                "k, Audio Bitrate - " + AUDIO_BITRATE +
                "k, Audio Codec - " + AUDIO_CODEC +
                ", Video Codec - " + VIDEO_CODEC +
                ", Max Duration " + convertSecondsToTime(MAX_VIDEO_DURATION)
        );

        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-ss", "00:00:00",
                "-to", convertSecondsToTime(MAX_VIDEO_DURATION),
                "-i", videoPath.toString(),
                "-threads", Byte.toString(MAX_THREAD_COUNT),
                "-b:v", Integer.toString(VIDEO_BITRATE * KB_TO_BYTE),
                "-b:a", Integer.toString(AUDIO_BITRATE * KB_TO_BYTE),
                "-c:a", AUDIO_CODEC,
                "-c:v", VIDEO_CODEC,
                "-loglevel", "quiet",
                outFile.getPath()
        );

        handleProcessBuilder(processBuilder, PROCESS_STARTED, SUCCESS_MSG_COMPOSE_VIDEO, ERROR_MSG_COMPOSE_VIDEO);

        return outFile;
    }


    /**
     * Handles process execution
     *
     * @param processBuilder process you want to start
     * @param startMsg       process start message
     * @param successMsg     success message
     * @param errorMsg       error message
     * @throws IOException          if unable to perform reading/writing
     * @throws InterruptedException if the current thread is interrupted by another thread while it is waiting
     */
    private void handleProcessBuilder(ProcessBuilder processBuilder, String startMsg, String successMsg, String errorMsg)
            throws IOException, InterruptedException {
        LOGGER.info(startMsg);

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            LOGGER.info(successMsg);
        } else {
            throw new IOException(errorMsg);
        }

        process.destroy();
    }

    /**
     * @param seconds seconds
     * @return string time format like 00:10:11
     */
    public static String convertSecondsToTime(long seconds) {
        long hours = seconds / 3600;
        long remainingSeconds = seconds % 3600;
        long minutes = remainingSeconds / 60;
        long remainingSeconds2 = remainingSeconds % 60;

        // Use String.format to format the time with leading zeros
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds2);
    }
}
