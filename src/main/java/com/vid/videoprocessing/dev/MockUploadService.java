package com.vid.videoprocessing.dev;

import com.vid.videoprocessing.amazon.interfaces.Upload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class MockUploadService implements Upload {

    @Value("${dev.video_output_path}")
    private String OUTPUT_PATH;
    /**
     * @param path file path
     * @return url/path of uploaded file
     */
    @Override
    public String upload(String path) throws IOException {

        File newFile = new File(OUTPUT_PATH + System.currentTimeMillis() + ".mp4");

        OutputStream fileOutputStream = new FileOutputStream(newFile);

        Files.copy(Paths.get(path), fileOutputStream);

        return newFile.getPath();
    }
}
