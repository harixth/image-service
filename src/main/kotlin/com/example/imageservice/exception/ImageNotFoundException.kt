package com.example.imageservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.NOT_FOUND)
class ImageNotFoundException(message: String?) : RuntimeException(message)