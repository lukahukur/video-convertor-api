package com.vid.videoprocessing.amazon.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vid.videoprocessing.amazon.interfaces.Upload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * this class helps with communication to AWS s3 bucket
 *
 * @author Lukacho Donadze
 * @version 1.0
 */
@Service
public class AwsService implements Upload {

    public static final Logger LOGGER =
            LogManager.getLogger(AwsService.class);
    /**
     *  AWS base url
     */
    @Value("${aws.url}")
    private String AMAZON_URL;

    /**
     * bucket access key
     */
    @Value("${aws.accessKeyId}")
    private String ACCESS_KEY;

    /**
     * secret key
     */
    @Value("${aws.secretKey}")
    private  String SECRET_KEY;

    /**
     * s3 bucket name
     */
    @Value("${aws.bucketName}")
    private String BUCKET_NAME;

    /**
     * name of the folder
     */
    @Value("${aws.folder}")
    private String FOLDER_NAME;

    /**
     * server region
     */
    @Value("${aws.region}")
    private String REGION;
    public static final String SUCCESS_MESSAGE = "Video uploaded to S3 successfully!";

    /**
     *
     * @param multipartFile file to upload
     * @param accessKeyId amazon access key
     * @param secretAccessKey amazon secret key
     * @param bucketName storage bucket name
     * @param objectKey folder name + separator + filename
     */
    private void uploadVideoToS3(MultipartFile multipartFile, String accessKeyId, String secretAccessKey, String bucketName, String objectKey) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(REGION)
                .build();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, multipartFile.getInputStream(), metadata);
            s3Client.putObject(request);

            LOGGER.info(SUCCESS_MESSAGE);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    /**
     *
     * @param localPath local path to a file
     * @return uploaded image url
     * @throws IOException if unable to read data from path
     */
    @Override
    public String upload(String localPath) throws IOException {
        Path path = Paths.get(localPath);

        byte[] bytes = Files.readAllBytes(path);

        final String NAME = path.getFileName().toString();

        MultipartFile multipartFile = new MockMultipartFile("Upload", bytes);

        uploadVideoToS3(multipartFile, ACCESS_KEY, SECRET_KEY, BUCKET_NAME, FOLDER_NAME + "/" + NAME);

        return getImageUrl(NAME);
    }

    /**
     *
     * @param multipartFile file to upload
     * @return uploaded image url
     */
    public String upload(MultipartFile multipartFile) {
        final String NAME = multipartFile.getName();
        uploadVideoToS3(multipartFile, ACCESS_KEY, SECRET_KEY, BUCKET_NAME, FOLDER_NAME + "/" + NAME);

        return getImageUrl(NAME);
    }

    /**
     * this method concatenates base url, bucket, folder and file name to get image url
     *
     * @param filename name of the file you want to get
     * @return full image URL
     */
    private String getImageUrl(String filename) {
        return AMAZON_URL + BUCKET_NAME + "/" + FOLDER_NAME + "/" + filename;
    }

}

