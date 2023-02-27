package com.kotlin.secondservice.queue_broker
import com.fasterxml.jackson.databind.ObjectMapper
import com.kotlin.secondservice.model.dto.ProductDTO
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory
import kotlin.text.Charsets.UTF_8

class ProductDeserializer : Deserializer<ProductDTO> {
    private val objectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    override fun deserialize(topic: String?, data: ByteArray?): ProductDTO? {
        log.info("Deserializing...")
        return objectMapper.readValue(
            String(
                data ?: throw SerializationException("Error when deserializing byte[] to Product"), UTF_8
            ), ProductDTO::class.java
        )
    }

    override fun close() {}

}