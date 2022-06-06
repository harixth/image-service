package com.example.imageservice.image.controllers

import com.example.imageservice.configuration.AppConfig
import com.example.imageservice.exception.ImageNotFoundException
import com.example.imageservice.image.services.ImageService
import com.example.imageservice.image.services.PredefinedTypeService
import com.example.imageservice.utils.getMediaType
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URL


@RestController
@RequestMapping("/image")
class ImageController(
    private val imageService: ImageService,
    private val predefinedTypeService: PredefinedTypeService,
    private val appConfig: AppConfig
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping(
        "/show/{predefinedTypeName}",
        "/show/{predefinedTypeName}/{seoName}",
    )
    fun getImage(
        @PathVariable("predefinedTypeName") predefinedTypeName: String,
        @RequestParam("reference") imageFilename: String
    ): ResponseEntity<Any> {
        return try {
            val predefinedType = predefinedTypeService.findPredefinedType(predefinedTypeName)

            var imageFromS3 = imageService.findImage(predefinedTypeName, imageFilename);

            if (imageFromS3 == null) {
                logger.info("$imageFilename is not in S3. Downloading...")
                val url = URL("${appConfig.sourceRootUrl}$imageFilename")
                val inputStream = url.openStream();
                if (inputStream == null) {
                    logger.info("There is no image $imageFilename on the source url")
                    throw ImageNotFoundException("$imageFilename not available")
                }
                val modifiedImage = imageService.optimizeImage(
                    inputStream, predefinedType,
                    predefinedTypeName, imageFilename
                );
                logger.info("Newly optimized $imageFilename is served")
                imageFromS3 = modifiedImage
            }

            return ResponseEntity.ok().contentType(getMediaType(predefinedType.imageType.toString()))
                /*.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${imageFilename}\"")*/
                .body(imageFromS3)
        } catch (error: Exception) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping(
        "/flush/{predefinedTypeName}",
    )
    fun DeleteImages(
        @PathVariable("predefinedTypeName") predefinedTypeName: String,
        @RequestParam("reference") imageFilename: String
    ): ResponseEntity<Any> {
        return try {
            imageService.deleteImage(predefinedTypeName, imageFilename)
            ResponseEntity.ok().build()
        } catch (error: Exception) {
            ResponseEntity.noContent().build()
        }
    }
}


