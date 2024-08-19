package com.demo.releasemanager.repository

import com.demo.releasemanager.entity.ServiceDeployments
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceDeploymentRepository : JpaRepository<ServiceDeployments, Long> {

    fun findByServiceNameAndServiceVersion(serviceName: String, serviceVersion: Int) : ServiceDeployments?

    fun findBySystemVersionVersionNumber(versionNumber: Int) : List<ServiceDeployments>
}