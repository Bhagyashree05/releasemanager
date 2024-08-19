package com.demo.releasemanager.controller

import com.demo.releasemanager.dto.DeployServiceRequest
import com.demo.releasemanager.entity.ServiceDeployments
import com.demo.releasemanager.entity.SystemVersion
import com.demo.releasemanager.service.ReleaseManagerService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(ReleaseManagerController::class)
class ReleaseManagerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var releaseManagerService: ReleaseManagerService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `test deployService`() {
        val serviceName = "Service A"
        val serviceVersion = 2
        val systemVersion = 2
        val request = DeployServiceRequest(serviceName, serviceVersion)
        doReturn(systemVersion).`when`(releaseManagerService).deployService(request)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/deploy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.systemVersionNumber").value(systemVersion))

        verify(releaseManagerService).deployService(request)
    }

    @Test
    fun `test getServices`() {
        val systemVersion = 1
        val serviceName = "Service A"
        val serviceVersion = 1
        val services = listOf(ServiceDeployments(1L, serviceName, serviceVersion, SystemVersion(1L, systemVersion)))
        doReturn(services).`when`(releaseManagerService).getServicesBySystemVersion(systemVersion)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/services")
                .param("systemVersion", systemVersion.toString())
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].serviceName").value(serviceName))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].serviceVersion").value(serviceVersion))

        verify(releaseManagerService).getServicesBySystemVersion(systemVersion)
    }

}