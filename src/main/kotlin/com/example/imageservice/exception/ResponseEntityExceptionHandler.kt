package com.example.imageservice.exception

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import java.io.IOException
import java.util.*


@ControllerAdvice
@RestController
class ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exRes = ExceptionResponse(Date(), ex.message!!, request.getDescription(false))
        return ResponseEntity(exRes, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundExceptions(ex: NotFoundException, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exRes = ExceptionResponse(
            Date(), ex.message ?: "Not Found",
            request.getDescription(false)
        )
        return ResponseEntity(exRes, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IOException::class)
    fun handleIOException(ex: Exception, request: WebRequest): ResponseEntity<ExceptionResponse> {
        val exRes = ExceptionResponse(Date(), ex.message!!, request.getDescription(false))
        return ResponseEntity(exRes, HttpStatus.NOT_FOUND)
    }
}