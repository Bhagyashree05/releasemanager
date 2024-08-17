package com.demo.releasemanager.dto

data class DeployServiceRequest(
    val serviceName: String,
    val serviceVersion: Int
)
