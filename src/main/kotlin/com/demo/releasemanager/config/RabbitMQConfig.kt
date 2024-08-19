package com.demo.releasemanager.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
class RabbitMQConfig {

    @Bean
    fun deploymentQueue(): Queue {
        return Queue("deploymentQueue", false)
    }
}