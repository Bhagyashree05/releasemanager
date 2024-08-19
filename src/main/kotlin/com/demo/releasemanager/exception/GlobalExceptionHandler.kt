package com.demo.releasemanager.exception

import com.demo.releasemanager.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DeploymentException::class)
    fun handleDeploymentException(ex: DeploymentException) : ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(message = ex.message ?: "Deployment Error")
        return ResponseEntity(errorResponse, HttpStatus.OK)
    }
}