package com.vid.videoprocessing.video.videoconvertor.impl.services;


import com.vid.videoprocessing.video.common.base.ImageConfig;

/**
 * Used to validate images, according to their requirements.
 *
 * @author Lukacho Donadze
 * @version v1.0
 */
public class ImageService {

    /**
     *
     * Read about those properties in {@link ImageConfig}
     */
    private final int MIN_WIDTH;
    private final int MIN_HEIGHT;
    private final int MAX_WIDTH;
    private final float IMAGE_RATIO;
    private final float CORRECTION;

    /**
     * constructor, accepts image parameters according to social media requirements.
     *
     * @param optimalImageRatio accepted image ratio
     */
    public ImageService(ImageConfig imageConfig, float optimalImageRatio) {
        this.IMAGE_RATIO = optimalImageRatio;
        this.MIN_WIDTH = imageConfig.getImageMinWidth();
        this.MAX_WIDTH = imageConfig.getImageMaxWidth();
        this.MIN_HEIGHT = imageConfig.getImageMinHeight();
        this.CORRECTION = imageConfig.getCorrection();
    }


    /**
     * checks if the ratio is correct.
     *
     * @param width         image width
     * @param height        image height
     * @param acceptedRatio accepted image ratio (imageRatioSmall or imageRatioBig)
     * @return true if ratio is correct, false - otherwise.
     */
    public boolean isCorrectRatio(double width, double height, double acceptedRatio) {
        double sizeCorrectionLimit = width * CORRECTION;
        boolean lessThanCorrectionLimit = width > (height * acceptedRatio - sizeCorrectionLimit); // satisfies small image ratio requirements
        boolean moreThanCorrectionLimit = width < (height * acceptedRatio + sizeCorrectionLimit); // satisfies big image ratio requirements

        return lessThanCorrectionLimit && moreThanCorrectionLimit && width >= MIN_WIDTH && width <= MAX_WIDTH && height >= MIN_HEIGHT;
    }


    /**
     * calculates and returns optimal image size according to requirements.
     *
     * @param width  image width
     * @param height image height
     * @return array of optimal size: width - arr[0], height - arr[1]
     */
    public int[] calculateOptimalImageSize(int width, int height) {
        if (IMAGE_RATIO >= 1) {
            if (height < MIN_HEIGHT || width < MIN_WIDTH) {
                if (width < MIN_HEIGHT * IMAGE_RATIO) {
                    width = Math.round(MIN_WIDTH * IMAGE_RATIO);
                    height = Math.round(width / IMAGE_RATIO);
                } else {
                    height = Math.round(MIN_WIDTH * IMAGE_RATIO);
                }
            }
        } else {
            if (width < MIN_WIDTH || height < MIN_HEIGHT) {
                if (height < MIN_WIDTH / IMAGE_RATIO) {
                    height = Math.round(MIN_HEIGHT / IMAGE_RATIO);
                    width = Math.round(height * IMAGE_RATIO);
                } else {
                    width = Math.round(MIN_HEIGHT / IMAGE_RATIO);
                }
            }
        }

        if ((double) width / height < IMAGE_RATIO) {
            width = Math.round(height * IMAGE_RATIO);
        } else {
            height = Math.round(width / IMAGE_RATIO);
        }

        return new int[]{width, height};
    }

}
