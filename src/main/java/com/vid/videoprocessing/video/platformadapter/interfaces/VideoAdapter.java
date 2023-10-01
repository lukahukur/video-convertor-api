package com.vid.videoprocessing.video.platformadapter.interfaces;

import java.io.File;
import java.util.concurrent.CompletableFuture;

/**
 * This interface forces user to implement async methods of
 * video conversion for each supported social network
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
public interface VideoAdapter {
    /**
     * Facebook <h2>Accepted formats</h2>
     * <ul>
     * <li>FileType:        MP4, MOV</li>
     * <li>MinWidth:        120 px</li>
     * <li>MinimumHeight:   120 px</li>
     * <li>VideoRatio:      4:5 or 1:1</li>
     * <li>AudioCodec:      AAC 128 Kbps</li>
     * <li>FileSize:        1GB max (Facebook accepts up to 4GB)</li>
     * <li>MinimumDuration: 1 second</li>
     * <li>MaximumDuration: 240 seconds</li>
     * </ul>
     *
     * @return File that satisfies all the requirements
     */
    public File toFacebookSupportedAdVideoFormat(File video) throws Exception;

    /**
     * Twitter(now X)
     * <h2>Accepted formats</h2>
     * <ul>
     * <li>FileType: MP4, MOV</li>
     * <li>MinWidth: 120 px</li>
     * <li>MinimumHeight: 120 px</li>
     * <li>Bitrate: 6k - 10k (kb)</li>
     * <li>VideoRatio: 4:5 or 1:1</li>
     * <li>AudioCodec: AAC LC</li>
     * <li>VideoCodec: H264, Baseline, Main, or High Profile with a 4:2:0 color space.</li>
     * <li>FileSize: 4GB max</li>
     * <li>FrameRate: 30FPS - 60FPS</li>
     * <li>MinimumDuration: 1 second</li>
     * <li>MaximumDuration: 140 seconds</li>
     * </ul>
     *
     * @return File that satisfies all the requirements
     */
    public File toTwitterSupportedAdVideoFormat(File video) throws Exception;

    /**
     * TikTok
     * <h2>Accepted formats</h2>
     * <ul>
     * <li>FileType: MP4, MOV, MPEG, 3GP, AVI</li>
     * <li>MinWidth: 540 px, 640 px, 960 px</li>
     * <li>MinimumHeight: 960 px, 640px, 540 px</li>
     * <li>Bitrate: ≥516 kbps</li>
     * <li>VideoRatio: 9:16, 16:9 or 1:1</li>
     * <li>AudioCodec: AAC LC</li>
     * <li>VideoCodec: H264, Baseline, Main, or High Profile with a 4:2:0 color space.</li>
     * <li>FileSize: 500MB max</li>
     * <li>FrameRate: 30FPS - 60FPS</li>
     * <li>MinimumDuration: 1 second</li>
     * <li>MaximumDuration: 60 seconds</li>
     * </ul>
     *
     * @return File that satisfies all the requirements
     */
    public File toTikTokSupportedAdVideoFormat(File video) throws Exception;
}
