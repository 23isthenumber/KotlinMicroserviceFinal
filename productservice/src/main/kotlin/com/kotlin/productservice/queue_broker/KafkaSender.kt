package com.kotlin.productservice.queue_broker

import com.kotlin.productservice.model.entity.Product
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class KafkaSender(
    @Autowired
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : QueueBrokerSender {

    val topic: String = "product"
    private val log = LoggerFactory.getLogger(javaClass)
    override fun sendMessage( objectToSend : Any){
        log.info("Receiving product request")
        log.info("Sending message to Kafka {}", objectToSend)
        val message: Message<Any> = MessageBuilder
            .withPayload(objectToSend)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader("X-Custom-Header", "Custom header here")
            .build()
        kafkaTemplate.send(message)
        log.info("Message sent with success")
    }
}