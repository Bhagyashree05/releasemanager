package com.demo.releasemanager.events

import com.demo.releasemanager.dto.DeployServiceRequest
import com.demo.releasemanager.service.ReleaseManagerService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify

@ExtendWith(MockitoExtension::class)
class DeploymentMessageListenerTest {

    @Mock
    private lateinit var releaseManagerService: ReleaseManagerService

    @InjectMocks
    private lateinit var deploymentMessageListener: DeploymentMessageListener

    @Test
    fun `should process deployment message successfully`() {

        val request = DeployServiceRequest("Service A", 2)

        deploymentMessageListener.receiveMessage(request)

        verify(releaseManagerService).deployService(request)
    }

    @Test
    fun `should log error when processing fails`() {
        val request = DeployServiceRequest("Service A", 2)

        doThrow(RuntimeException("Deployment failed")).`when`(releaseManagerService).deployService(request)

        deploymentMessageListener.receiveMessage(request)

        verify(releaseManagerService).deployService(request)
    }
}