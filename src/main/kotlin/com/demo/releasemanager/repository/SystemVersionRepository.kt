package com.demo.releasemanager.repository

import com.demo.releasemanager.entity.SystemVersion
import org.springframework.data.jpa.repository.JpaRepository

interface SystemVersionRepository : JpaRepository<SystemVersion, Long> {

    fun findTopByOrderByVersionNumberDesc(): SystemVersion?

}