package com.vid.videoprocessing;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class VideoProcessingApplication {


    public static void main(String[] args) {
        SpringApplication.run(VideoProcessingApplication.class, args);
    }

}
