package com.kotlin.secondservice.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.kotlin.productservice.model.entity.Price
import com.kotlin.productservice.model.entity.Property
import com.kotlin.productservice.model.enums.ProductStatus
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated


data class ProductDTO(
    @JsonProperty("id")
    var id: Long?,
    @JsonProperty("name")
    var name: String,
    @JsonProperty("description")
    var description: String,
    @JsonProperty("price")
    var price: Price?,
    @JsonProperty("property")
    var property: List<Property>,
    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    var status: ProductStatus?
)

