package com.vid.videoprocessing.video.common.base;

import lombok.Getter;

/**
 * Stores common social media image parameters for SocialImage.
 *
 * @author Lukacho Donadze
 * @version v1.0
 */
@Getter()
public abstract class ImageConfig {


    /**
     *  forgivable inaccuracy in resolutions
     */
    private final float correction = 0.02f;

    /**
     *  Original image opacity
     */
    private final float originalOverlayOpacity = 1;

    /**
     * unlimited max width
     */
    private final int unlimitedMaxWidth = 0;


    /**
     * @return image min width
     */
    public abstract int getImageMinWidth();

    /**
     * @return image min height
     */
    public abstract int getImageMinHeight();

    /**
     * @return image max width
     */
    public abstract int getImageMaxWidth();

}