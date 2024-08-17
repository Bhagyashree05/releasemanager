package com.demo.releasemanager.entity

import jakarta.persistence.*

@Entity
@Table(name = "service_deployments")
data class ServiceDeployments(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    @Column(name = "service_name", nullable = false)
    private val serviceName: String,

    @Column(name = "service_version", nullable = false)
    private val serviceVersion: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_version_id", nullable = false)
    private val systemVersion: SystemVersion

)
