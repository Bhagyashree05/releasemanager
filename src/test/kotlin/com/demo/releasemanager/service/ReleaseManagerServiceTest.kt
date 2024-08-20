package com.demo.releasemanager.service

import com.demo.releasemanager.dto.DeployServiceRequest
import com.demo.releasemanager.entity.ServiceDeployments
import com.demo.releasemanager.entity.SystemVersion
import com.demo.releasemanager.exception.DeploymentException
import com.demo.releasemanager.repository.ServiceDeploymentRepository
import com.demo.releasemanager.repository.SystemVersionRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.argThat
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ReleaseManagerServiceTest {

    @Mock
    private lateinit var systemVersionRepository: SystemVersionRepository

    @Mock
    private lateinit var serviceDeploymentRepository: ServiceDeploymentRepository

    @InjectMocks
    private lateinit var releaseManagerService: ReleaseManagerService

    @Test
    fun `should create a new system version when service version changes`() {

        val serviceName = "Service A"
        val serviceVersion = 2
        val existingSystemVersion = SystemVersion(1, 1)
        val deployServiceRequest = DeployServiceRequest(serviceName = serviceName, serviceVersion = serviceVersion)


        `when`(systemVersionRepository.findTopByOrderByVersionNumberDesc()).thenReturn(existingSystemVersion)
        `when`(serviceDeploymentRepository.findByServiceNameAndServiceVersion(serviceName, serviceVersion)).thenReturn(
            null
        )
        `when`(systemVersionRepository.save(any(SystemVersion::class.java))).thenAnswer { it.arguments[0] as SystemVersion }

        val result = releaseManagerService.deployService(deployServiceRequest)

        assertEquals(2, result)
        verify(systemVersionRepository).save(argThat {
            it.versionNumber == 2
        })
        verify(serviceDeploymentRepository).save(argThat {
            it.serviceName == serviceName && it.serviceVersion == serviceVersion && it.systemVersion.versionNumber == 2
        })
    }

    @Test
    fun `should reuse existing system version when service version has not changed`() {
        val serviceName = "Service A"
        val serviceVersion = 1
        val deployServiceRequest = DeployServiceRequest(serviceName = serviceName, serviceVersion = serviceVersion)
        val existingSystemVersion = SystemVersion(id = 1, versionNumber = 1)
        val existingDeployment = ServiceDeployments(
            serviceName = serviceName,
            serviceVersion = serviceVersion,
            systemVersion = existingSystemVersion
        )

        `when`(serviceDeploymentRepository.findByServiceNameAndServiceVersion(serviceName, serviceVersion)).thenReturn(
            existingDeployment
        )

        val result = releaseManagerService.deployService(deployServiceRequest)

        assertEquals(1, result)
    }

    @Test
    fun `test deployService - invalid input`() {
        val serviceName = ""
        val serviceVersion = -1
        val deployServiceRequest = DeployServiceRequest(serviceName = serviceName, serviceVersion = serviceVersion)

        assertThrows(DeploymentException::class.java) {
            releaseManagerService.deployService(deployServiceRequest)
        }
    }


}