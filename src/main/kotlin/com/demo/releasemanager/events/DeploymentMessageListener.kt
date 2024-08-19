package com.demo.releasemanager.events

import com.demo.releasemanager.dto.DeployServiceRequest
import com.demo.releasemanager.service.ReleaseManagerService
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class DeploymentMessageListener(
    private val releaseManagerService: ReleaseManagerService
) {

    @RabbitListener(queues = ["deploymentQueue"])
    fun receiveMessage(request: DeployServiceRequest) {
        try {
            logger.info("Received deployment message: $request")
            val systemVersionNumber = releaseManagerService.deployService(request)
            logger.info("Updated system version: $systemVersionNumber")
        } catch (e: Exception) {
            logger.error("Failed to process deployment message", e)
        }
    }
}