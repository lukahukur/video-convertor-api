package com.vid.videoprocessing.api.controllers;

import com.vid.videoprocessing.api.dto.VideoEncodingParametersDto;
import com.vid.videoprocessing.api.services.VideoConvertorService;
import com.vid.videoprocessing.video.platformadapter.config.PlatformNamingsAndErrorMessages;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ws.schild.jave.EncoderException;

import java.io.IOException;

/**
 * Video conversion controller
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@RestController
@RequestMapping(value = "api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class VideoConvertorController {

    public static final Logger LOGGER =
            LogManager.getLogger(VideoConvertorController.class);

    private final PlatformNamingsAndErrorMessages namings;
    private final VideoConvertorService videoConvertorService;

    /**
     * info message - user requested to convert video
     */
    public static final String USER_REQUESTED_SERVICE = "user requested video conversion service, format - ";

    /**
     * error message - user file is invalid
     */
    public static final String USER_FILE_INVALID_ERR_MESSAGE = "User video is corrupted or link is invalid";

    /**
     * error message - user params or file is invalid
     */
    public static final String USER_FILE_OR_PARAMS_INVALID_ERR_MESSAGE = "User video or parameters are invalid";


    @GetMapping(value = "/")
    public String healthCheck() {
        return "OK (საამაყო პულსი)";
    }

    /**
     * handles video conversion to facebook format
     *
     * @param reqURL object that contains url of the video
     * @return status code 200 (ok) if video is converted and uploaded to s3bucket <br>
     * status code 400 - if video or link is invalid
     */
    @PostMapping(value = "/facebook", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> toFacebook(@RequestBody RequestBodyURL reqURL) {
        try {
            LOGGER.info(USER_REQUESTED_SERVICE + namings.FACEBOOK);

            return ResponseEntity.ok()
                    .body(videoConvertorService.convertToFacebookFormat(reqURL.url));
        } catch (IOException | EncoderException | IllegalArgumentException | InterruptedException err) {
            LOGGER.error(err);
            return ResponseEntity.badRequest().body(USER_FILE_INVALID_ERR_MESSAGE);
        }
    }


    /**
     * handles video conversion to twitter format
     *
     * @param reqURL object that contains url of the video
     * @return status code 200 (ok) if video is converted and uploaded to s3bucket <br>
     * status code 400 - if video or link is invalid
     */
    @PostMapping(value = "/twitter", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> toTwitter(@RequestBody RequestBodyURL reqURL) {
        try {
            LOGGER.info(USER_REQUESTED_SERVICE + namings.TWITTER);

            return ResponseEntity.ok()
                    .body(videoConvertorService.convertToTwitterFormat(reqURL.url));
        } catch (IOException | EncoderException | IllegalArgumentException | InterruptedException err) {
            LOGGER.error(err);
            return ResponseEntity.badRequest().body(USER_FILE_INVALID_ERR_MESSAGE);
        }
    }


    /**
     * handles video conversion to tiktok format
     *
     * @param reqURL object that contains url of the video
     * @return status code 200 (ok) if video is converted and uploaded to s3bucket <br>
     * status code 400 - if video or link is invalid
     */
    @PostMapping(value = "/tiktok", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> toTikTok(@RequestBody RequestBodyURL reqURL) {
        try {
            LOGGER.info(USER_REQUESTED_SERVICE + namings.TIK_TOK);

            return ResponseEntity.ok()
                    .body(videoConvertorService.convertToTikTokFormat(reqURL.url));
        } catch (IOException | EncoderException | IllegalArgumentException | InterruptedException err) {
            LOGGER.error(err);
            return ResponseEntity.badRequest().body(USER_FILE_INVALID_ERR_MESSAGE);
        }
    }

    /**
     * handles video conversion to tiktok format
     *
     * @param reqBody object that contains url and specifications of the video
     * @return status code 200 (ok) if video is converted and uploaded to s3bucket <br>
     * status code 400 - if video or link is invalid
     */
    @PostMapping(value = "/custom", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> toCustom(@RequestBody RequestBodyCustom reqBody) {
        try {
            LOGGER.info(USER_REQUESTED_SERVICE + "Custom");

            return ResponseEntity.ok()
                    .body(videoConvertorService.convertToCustomFormat(reqBody.url, reqBody.videoSpecifications));
        } catch (IOException | EncoderException | InterruptedException err) {
            LOGGER.error(err);
            return ResponseEntity.badRequest().body(USER_FILE_OR_PARAMS_INVALID_ERR_MESSAGE);
        } catch (IllegalArgumentException err) {
            LOGGER.error(err);
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }

    /**
     * Request body - only url
     */
    public static class RequestBodyURL {

        /**
         * Video URL
         */
        public String url;
    }

    /**
     * Request body - url and Video specs
     */
    public static class RequestBodyCustom {

        /**
         * Video URL
         */
        public String url;

        /**
         * Target video specs
         */
        public VideoEncodingParametersDto videoSpecifications;
    }
}
