package com.example.imageservice.image.repositories

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.*
import java.util.Timer
import kotlin.concurrent.schedule
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.io.*

@Repository
@Slf4j
class S3Repository(private val amazonS3Client: AmazonS3) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun listBuckets(): List<Bucket> {
        return amazonS3Client.listBuckets()
    }

    fun getObject(bucketName: String, path: String): S3Object? {
        return try {
            logger.debug("BUCKET: {}, PATH: {}", bucketName, path)
            amazonS3Client.getObject(bucketName, path)
        } catch (ex: Exception) {
            null
        }
    }

    @Throws(IOException::class)
    fun putObject(
        bucketName: String, path: String, file: File
    ) {
        val putObjectRequest =
            PutObjectRequest(bucketName, path, file).withCannedAcl(CannedAccessControlList.PublicRead)
        try {
            amazonS3Client.putObject(putObjectRequest)
        } catch (e: Exception) {
            logger.warn("Failed to upload file to S3 on First Attempt: {}", e.message)
            retryUpload(putObjectRequest)
        }
    }

    @Throws(IOException::class)
    fun retryUpload(putObjectRequest: PutObjectRequest) {
        try {
            Timer().schedule(200) {
                amazonS3Client.putObject(putObjectRequest)
            }
        } catch (e: Exception) {
            logger.error("Upload failed again: {}", e.message)
        }
    }

    fun listObjects(bucketName: String): List<S3ObjectSummary> {
        val objectListing = amazonS3Client.listObjects(bucketName)
        return objectListing.objectSummaries
    }

    fun deleteObject(bucketName: String, path: String) {
        try {
            val deleteObjectRequest = DeleteObjectRequest(bucketName, path)
            amazonS3Client.deleteObject(deleteObjectRequest)
        } catch (e: Exception) {
            logger.error("File deletion failed: {}", e.message)
        }
    }
}