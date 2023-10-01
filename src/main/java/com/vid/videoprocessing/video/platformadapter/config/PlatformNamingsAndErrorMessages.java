package com.vid.videoprocessing.video.platformadapter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class encapsulates names and
 * error messages for each supported
 * social network
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Component
public class PlatformNamingsAndErrorMessages {


    /**
     * Twitter name
     */
    @Value("${socials.twitter}")
    public String TWITTER;


    /**
     * Facebook name
     */
    @Value("${socials.facebook}")
    public String FACEBOOK;

    /**
     * tiktok name
     */
    @Value("${socials.tiktok}")
    public String TIK_TOK;

    /**
     * not found specifications for twitter
     */
    @Value("${socials.twitter.paramsNotFound}")
    public String TWITTER_SPECS_NOT_FOUND;

    /**
     * not found specifications for facebook
     */
    @Value("${socials.facebook.paramsNotFound}")
    public String FACEBOOK_SPECS_NOT_FOUND;

    /**
     * not found specifications for tiktok
     */
    @Value("${socials.tiktok.paramsNotFound}")
    public String TIK_TOK_SPECS_NOT_FOUND;
}



