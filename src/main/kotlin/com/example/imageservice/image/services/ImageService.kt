package com.example.imageservice.image.services

import com.amazonaws.util.IOUtils
import com.example.imageservice.image.entities.PredefinedType
import com.example.imageservice.image.repositories.S3Repository
import com.example.imageservice.utils.BUCKET_NAME
import com.example.imageservice.utils.generateS3path
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO


@Service
@Slf4j
class ImageService(private val s3Repository: S3Repository) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun findImage(predefinedTypeName: String, filename: String): ByteArray? {
        val img = s3Repository.getObject(BUCKET_NAME, generateS3path(predefinedTypeName, filename)) ?: return null;
        logger.info("Found $filename in S3")
        return IOUtils.toByteArray(img.objectContent)
    }

    fun optimizeImage(
        inputStream: InputStream?,
        predefinedType: PredefinedType,
        predefinedTypeName: String,
        filename: String
    ): ByteArray {
        val bufferedImage = ImageIO.read(inputStream);
        // TODO: modify bufferedImage for optimization
        logger.info("Successfully optimized image. Uploading to S3...")
        val byteArrayOutputStream = ByteArrayOutputStream()
        val file = File(filename)
        val fileType = predefinedType.imageType.toString().lowercase()
        ImageIO.write(bufferedImage, fileType, file)
        ImageIO.write(bufferedImage, fileType, byteArrayOutputStream)
        s3Repository.putObject(BUCKET_NAME, generateS3path(predefinedTypeName, filename), file)
        return byteArrayOutputStream.toByteArray()
    }

    fun deleteImage(predefinedTypeName: String, filename: String) {
        if (predefinedTypeName == "original") {
            // TODO: Delete all images
            /*PredefinedTypeName.values()
                .forEach { it -> s3Repository.deleteObject(BUCKET_NAME, generateS3path(it.toString(), filename)) }*/
        } else {
            s3Repository.deleteObject(BUCKET_NAME, generateS3path(predefinedTypeName, filename))
            logger.info("$filename deleted in S3...")
        }
    }
}