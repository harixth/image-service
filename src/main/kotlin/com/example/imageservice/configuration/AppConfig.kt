package com.example.imageservice.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppConfig {
    @Value("\${app.source.root.url}")
    lateinit var sourceRootUrl: String

    @Value("\${app.aws.accesskey}")
    lateinit var awsAccesskey: String

    @Value("\${app.aws.secretkey}")
    lateinit var awsSecretkey: String

    /*@Value("\${app.aws.s3.endpoint}")
    lateinit var awsS3Endpoint: String*/
}