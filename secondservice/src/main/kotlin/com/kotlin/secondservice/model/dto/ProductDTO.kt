package com.kotlin.productservice.model.dto

import com.kotlin.productservice.model.entity.Price
import com.kotlin.productservice.model.entity.Property
import com.kotlin.productservice.model.enums.ProductStatus
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated


data class ProductDTO(
    var id: Long?,
    var name: String,
    var description: String,
    var price: Price,
    var property: List<Property>,
    @Enumerated(EnumType.STRING)
    var status: ProductStatus?
)

