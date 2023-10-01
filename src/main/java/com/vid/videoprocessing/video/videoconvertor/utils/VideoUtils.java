package com.vid.videoprocessing.video.videoconvertor.utils;

import com.vid.videoprocessing.video.videoconvertor.models.VideoMetadata;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ws.schild.jave.AudioInfo;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * This class contains frequently used methods for video processing
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
public class VideoUtils {

    public static final Logger LOGGER =
            LogManager.getLogger(VideoUtils.class);
    /**
     * multiplier
     */
    public static final float BYTE_TO_MB_MULTIPLIER = 0.00000095367432f;
    /**
     * is used when provided file isn't video
     */
    public static String FILE_ISNT_VIDEO_MESSAGE = "This file isn't in a vide format";

    /**
     * Common video formats
     */
    public static List<String> videoFormats = List.of("mov", "webm", "mkv", "flv", "ogv", "vob", "mp4", "mpeg", "3gp", "avi");


    /**
     * @param file any file
     * @return extension of a file (if no returns empty string)
     */
    public static String getFileExtension(@NonNull File file) {
        String name = file.getName();

        int lastIndexOf = name.lastIndexOf(".");

        if (lastIndexOf == -1) {
            return "";
        }

        return name.substring(lastIndexOf);
    }

    /**
     * @param name filename
     * @return extension of a file (if no returns empty string)
     */
    public static String getFileExtension(@NonNull String name) {

        int lastIndexOf = name.lastIndexOf(".");

        if (lastIndexOf == -1) {
            return "";
        }

        return name.substring(lastIndexOf);
    }


    /**
     * @param file file that could have video format
     * @return true if file is a video
     */
    public static boolean isFileVideo(File file) {
        String extension = getFileExtension(file);
        String fileType;

        if (extension.isEmpty()) return false;

        fileType = extension.substring(1);


        return videoFormats.stream().anyMatch(x -> Objects.equals(x, fileType));
    }

    /**
     * @param video file
     * @return VideoMetadata object filled with video metadata
     * @throws EncoderException if file isn't video
     */
    public static VideoMetadata getVideoMetadata(File video) throws EncoderException, IllegalArgumentException {
        if (!isFileVideo(video)) {
            throw new IllegalArgumentException(FILE_ISNT_VIDEO_MESSAGE);
        }


        MultimediaInfo mediaInfo = new MultimediaObject(video).getInfo();
        ws.schild.jave.VideoInfo videoInfo = mediaInfo.getVideo();
        AudioInfo audioInfo = mediaInfo.getAudio();

        return VideoMetadata.builder()
                .videoDecoder(videoInfo.getDecoder())
                .videoBitRate(videoInfo.getBitRate())
                .frameRate(videoInfo.getFrameRate())
                .videoSize(videoInfo.getSize())
                .duration(mediaInfo.getDuration())
                .format(mediaInfo.getFormat())
                .audioBitRate(audioInfo != null ? audioInfo.getBitRate() : 0)
                .channels(audioInfo != null ? audioInfo.getChannels() : 0)
                .audioDecoder(audioInfo != null ? audioInfo.getDecoder() : null)
                .samplingRate(audioInfo != null ? audioInfo.getSamplingRate() : 0)
                .sizeInMegaBytes((double) video.length() / (1024 * 1024))
                .build();
    }

    public static float getFileSizeInMb(File file) {
        return file.length() * BYTE_TO_MB_MULTIPLIER;
    }

    /**
     * @param file any file
     * @return filename without extension
     */
    public static String getFileName(File file) {
        int substr = getFileExtension(file).length();
        return file.getName().substring(0, file.getName().length() - substr);
    }

    /**
     * @param files list of temporary files that should be removed
     *              <p>
     *              I put this process into parallel thread to
     *              prevent main thread from blocking
     */
    public static void cleanUpFiles(File[] files) {
        new Thread(() -> {
            for (File file : files) {
                file.delete();
            }
            LOGGER.info("Files were cleaned up");
        }).start();
    }

    /**
     * This method tells whether video has audio or not
     *
     * @param video video file
     * @return true if video has audio
     * @throws EncoderException if unable to read file
     */
    public static boolean videoHasAudio(File video) throws EncoderException {
        MultimediaInfo mediaInfo = new MultimediaObject(video).getInfo();

        return mediaInfo.getAudio() != null;
    }
}
