package com.example.imageservice.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.imageservice.image.entities.ImageType
import com.example.imageservice.image.entities.PredefinedType;
import com.example.imageservice.image.entities.ScaleType
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PredefinedTypeConfig {

    fun THUMBNAIL(): PredefinedType {
        return PredefinedType(
            100, 100, 72, ScaleType.CROP, "",
            ImageType.JPG, ""
        )
    }

    fun DETAILLARGE(): PredefinedType {
        return PredefinedType(
            250, 250, 128, ScaleType.SKEW, "",
            ImageType.JPG, ""
        )
    }

}
