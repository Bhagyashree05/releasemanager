package com.demo.releasemanager.entity

import jakarta.persistence.*

@Entity
@Table(name = "system_versions")
data class SystemVersion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0,

    @Column(name = "version_number", nullable = false, unique = true)
    val versionNumber: Int

)
