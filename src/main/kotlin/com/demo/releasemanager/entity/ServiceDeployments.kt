package com.demo.releasemanager.entity

import jakarta.persistence.*

@Entity
@Table(name = "service_deployments")
data class ServiceDeployments(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0,

    @Column(name = "service_name", nullable = false)
    val serviceName: String,

    @Column(name = "service_version", nullable = false)
    val serviceVersion: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_version_id", nullable = false)
    val systemVersion: SystemVersion

)
