package com.kotlin.productservice.queue_broker
import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.productservice.model.dto.ProductDTO
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Serializer
import org.slf4j.LoggerFactory

class ProductSerializer : Serializer<ProductDTO> {
    private val objectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    override fun serialize(topic: String?, data: ProductDTO?): ByteArray? {
        log.info("Serializing...")
        return objectMapper.writeValueAsBytes(
            data ?: throw SerializationException("Error when serializing Product to ByteArray[]")
        )
    }

    override fun close() {}
}