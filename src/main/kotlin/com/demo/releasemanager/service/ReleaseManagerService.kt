package com.demo.releasemanager.service

import com.demo.releasemanager.dto.DeployServiceRequest
import com.demo.releasemanager.entity.ServiceDeployments
import com.demo.releasemanager.entity.SystemVersion
import com.demo.releasemanager.exception.DeploymentException
import com.demo.releasemanager.repository.ServiceDeploymentRepository
import com.demo.releasemanager.repository.SystemVersionRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class ReleaseManagerService(
    private val serviceDeploymentRepository: ServiceDeploymentRepository,
    private val systemVersionRepository: SystemVersionRepository
) {

    fun deployService(deployServiceRequest: DeployServiceRequest): Int {
        validateInput(deployServiceRequest)

        val existingDeployment =
            findExistingDeployment(deployServiceRequest.serviceName, deployServiceRequest.serviceVersion)

        return existingDeployment?.systemVersion?.versionNumber
            ?: createNewSystemVersion(deployServiceRequest.serviceName, deployServiceRequest.serviceVersion)

    }

    private fun validateInput(deployServiceRequest: DeployServiceRequest) {
        if (deployServiceRequest.serviceName.isBlank()) {
            throw DeploymentException("Service name cannot be blank")
        }

        if (deployServiceRequest.serviceVersion <= 0) {
            throw DeploymentException("Service version must be a positive integer")
        }
    }

    private fun findExistingDeployment(serviceName: String, serviceVersion: Int): ServiceDeployments? {
        return serviceDeploymentRepository.findByServiceNameAndServiceVersion(serviceName, serviceVersion)?.also {
            logger.info("Service '$serviceName' with version '$serviceVersion' already deployed. Returning existing system version: ${it.systemVersion.versionNumber}")
        }
    }

    private fun createNewSystemVersion(serviceName: String, serviceVersion: Int): Int {
        val newSystemVersion = generateNewSystemVersion()
        linkExistingServicesToNewVersion(newSystemVersion)
        saveNewServiceDeployment(serviceName, serviceVersion, newSystemVersion)
        return newSystemVersion.versionNumber
    }

    private fun generateNewSystemVersion(): SystemVersion {
        val latestSystemVersion = systemVersionRepository.findTopByOrderByVersionNumberDesc()
        val newSystemVersionNumber = (latestSystemVersion?.versionNumber ?: 0) + 1
        return SystemVersion(versionNumber = newSystemVersionNumber).also {
            systemVersionRepository.save(it)
            logger.info("Created new system version: ${it.versionNumber}")
        }
    }

    private fun linkExistingServicesToNewVersion(newSystemVersion: SystemVersion) {
        serviceDeploymentRepository.findAll().forEach {
            serviceDeploymentRepository.save(it.copy(systemVersion = newSystemVersion))
        }
    }

    private fun saveNewServiceDeployment(serviceName: String, serviceVersion: Int, newSystemVersion: SystemVersion) {
        val serviceDeployment = ServiceDeployments(
            serviceName = serviceName,
            serviceVersion = serviceVersion,
            systemVersion = newSystemVersion
        )
        serviceDeploymentRepository.save(serviceDeployment)
        logger.info("Deployed service '$serviceName' with version '$serviceVersion' under system version: ${newSystemVersion.versionNumber}")
    }

    fun getServicesBySystemVersion(systemVersionNumber: Int): List<ServiceDeployments> {
        return serviceDeploymentRepository.findBySystemVersionVersionNumber(systemVersionNumber)
    }

}