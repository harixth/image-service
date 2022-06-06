package com.example.imageservice.utils

import org.springframework.http.MediaType


fun generateS3path(predefinedTypeName: String, fileName: String): String {
    val modifiedFileName = fileName.replace("/", "_")
    val name = fileName.split(".")[0]
    return if (name.length > 8) {
        "$predefinedTypeName/${modifiedFileName.substring(0, 4)}/${modifiedFileName.substring(4, 8)}/$modifiedFileName"
    } else if (name.length in 5..7) {
        "$predefinedTypeName/${modifiedFileName.substring(0, 4)}/$modifiedFileName"
    } else {
        "$predefinedTypeName/$modifiedFileName"
    }
}

fun getMediaType(imageType: String): MediaType {
    return if (imageType == "PNG") {
        println(imageType)
        MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE);
    } else {
        MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE);
    }
}