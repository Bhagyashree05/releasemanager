package com.demo.releasemanager.controller

import com.demo.releasemanager.dto.DeployServiceRequest
import com.demo.releasemanager.dto.DeployServiceResponse
import com.demo.releasemanager.dto.ServiceResponse
import com.demo.releasemanager.service.ReleaseManagerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ReleaseManagerController (
    private val releaseManagerService: ReleaseManagerService
){

    @PostMapping("/deploy")
    fun deployService(@RequestBody deployServiceRequest: DeployServiceRequest): ResponseEntity<DeployServiceResponse> {
        val systemVersion = releaseManagerService.deployService(deployServiceRequest)
        return ResponseEntity.ok(DeployServiceResponse(systemVersion))
    }

    @GetMapping("/services")
    fun getServices(@RequestParam("systemVersion") systemVersion: Int): ResponseEntity<List<ServiceResponse>> {
        val services = releaseManagerService.getServicesBySystemVersion(systemVersion)
        val responses = services.map {
            ServiceResponse(serviceName = it.serviceName, serviceVersion = it.serviceVersion)
        }
        return ResponseEntity.ok(responses)
    }
}