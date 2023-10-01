package com.vid.videoprocessing.video.platformadapter.impl.services;

import com.vid.videoprocessing.video.platformadapter.config.PlatformNamingsAndErrorMessages;
import com.vid.videoprocessing.video.platformadapter.repository.SocialNetworkVideoSpecsRepository;
import com.vid.videoprocessing.video.platformadapter.data.SocialNetworkVideoSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



/**
 * This class allows user to get required ad video specs from the database for each social network
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class RequiredVideoSpecsFromDatabase {

    private final PlatformNamingsAndErrorMessages namingsAndErrorMessages;

    private final SocialNetworkVideoSpecsRepository socialNetworkVideoSpecsRepository;

    /**
     * @return required video specs for Twitter.
     */
    public SocialNetworkVideoSpecs getTwitterVideoRequiredSpecs() {
        return getSocialNetworkRequiredVideoSpecsOrThrowError(namingsAndErrorMessages.TWITTER, namingsAndErrorMessages.TWITTER_SPECS_NOT_FOUND);
    }

    /**
     * @return required video specs for Facebook.
     */
    public SocialNetworkVideoSpecs getFacebookVideoRequiredSpecs() {
        return getSocialNetworkRequiredVideoSpecsOrThrowError(namingsAndErrorMessages.FACEBOOK,namingsAndErrorMessages.FACEBOOK_SPECS_NOT_FOUND);
    }

    /**
     * @return required video specs for TikTok.
     */
    public SocialNetworkVideoSpecs getTikTokVideoRequiredSpecs() {
        return getSocialNetworkRequiredVideoSpecsOrThrowError(namingsAndErrorMessages.TIK_TOK, namingsAndErrorMessages.TIK_TOK_SPECS_NOT_FOUND);
    }

    /**
     * @param socialNetwork TikTok, Facebook, Twitter...
     * @param errorMessage  error description
     * @return required video specs for specific social network.
     * We take them from mongoDB
     */
    private SocialNetworkVideoSpecs
    getSocialNetworkRequiredVideoSpecsOrThrowError(String socialNetwork, String errorMessage) {
        var videoSpecs = socialNetworkVideoSpecsRepository
                .getSocialNetworkVideoSpecsBySocialNetwork(socialNetwork);

        if (videoSpecs.isPresent()) {
            return videoSpecs.get();
        }

        throw new Error(errorMessage);
    }
}
