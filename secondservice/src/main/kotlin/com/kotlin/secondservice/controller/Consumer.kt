package com.kotlin.secondservice.controller

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class Consumer {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["product}"], groupId = "ppr")
    fun listenGroupFoo(consumerRecord: ConsumerRecord<Any, Any>, ack: Acknowledgment) {
        logger.info("Message received {}", consumerRecord)
        println("THIS: " + consumerRecord.value().toString())
        ack.acknowledge()
    }
}