package com.demo.releasemanager.controller

import com.demo.releasemanager.dto.DeployServiceRequest
import com.demo.releasemanager.dto.DeployServiceResponse
import com.demo.releasemanager.dto.ServiceResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ReleaseManagerController {

    @PostMapping("/deploy")
    fun deployService(@RequestBody deployServiceRequest: DeployServiceRequest): ResponseEntity<DeployServiceResponse> {
        // ToDO
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/services")
    fun getServices(@RequestParam("systemVersion") systemVersion: Int): ResponseEntity<List<ServiceResponse>> {
        // TODO
        return ResponseEntity(HttpStatus.OK)
    }
}