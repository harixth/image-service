package com.example.imageservice.utils

import com.example.imageservice.image.entities.ImageType
import org.junit.jupiter.api.Test
import org.springframework.test.util.AssertionErrors.*

class helperTests() {

    @Test
    fun generateS3path_LessThanFour() {
        val result = generateS3path("thumbnail", "abc.jpg");
        assertEquals("", result, "thumbnail/abc.jpg")
    }

    @Test
    fun generateS3path_LessThanEight() {
        val result = generateS3path("thumbnail", "abcdef.jpg");
        assertEquals("", result, "thumbnail/abcd/abcdef.jpg")
    }

    @Test
    fun generateS3path_MoreThanEight() {
        val result = generateS3path("thumbnail", "abcdefghij.jpg");
        assertEquals("", result, "thumbnail/abcd/efgh/abcdefghij.jpg")
    }

    @Test
    fun generateS3path_WithSlash() {
        val result = generateS3path("thumbnail", "/somedir/anotherdir/abcdef.jpg");
        assertEquals("", result, "thumbnail/_som/edir/_somedir_anotherdir_abcdef.jpg")
    }

    @Test
    fun getMediaType_PNG() {
        val result = getMediaType(ImageType.PNG.toString())
        assertEquals("", result.toString(), "image/png")
    }

    @Test
    fun getMediaType_JPEG() {
        val result = getMediaType(ImageType.JPG.toString())
        assertEquals("", result.toString(), "image/jpeg")
    }
}