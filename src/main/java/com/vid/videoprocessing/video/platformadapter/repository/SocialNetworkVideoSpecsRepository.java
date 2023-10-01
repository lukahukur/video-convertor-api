package com.vid.videoprocessing.video.platformadapter.repository;

import com.vid.videoprocessing.video.platformadapter.data.SocialNetworkVideoSpecs;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SocialNetworkVideoSpecsRepository extends MongoRepository<SocialNetworkVideoSpecs,String> {
    Optional<SocialNetworkVideoSpecs> getSocialNetworkVideoSpecsBySocialNetwork(String network);
}
