package com.example.imageservice.configuration

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AWSConfig(private val appConfig: AppConfig) {
    fun credentials(): AWSCredentials {
        return BasicAWSCredentials(
             appConfig.awsAccesskey,
            appConfig.awsSecretkey
        )
    }

    @Bean
    fun amazonS3(): AmazonS3 {
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials()))
            .withRegion(Regions.AP_SOUTHEAST_1)
            .build()
    }
}