package com.kotlin.productservice.model.dto

import com.kotlin.productservice.model.entity.Price
import com.kotlin.productservice.model.entity.Product
import com.kotlin.productservice.model.entity.Property
import com.kotlin.productservice.model.enums.ProductStatus
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDate


data class ProductDTO(
    var id: Long?,
    var name: String,
    var description: String,
    var price: Price,
    var property: List<Property>,
    @Enumerated(EnumType.STRING)
    var status: ProductStatus?
)

fun Product.toDTO() = ProductDTO(
    id = id,
    name = name,
    description = description,
    price = findActualPrice(price, id),
    property = property,
    status = status
)

private fun findActualPrice(prices: List<Price>, id: Long?): Price {
   // log.info(">> Finding current price for product with id: {}", id)
    return prices.stream()
        .filter { p -> p.effectiveDate.isBefore(LocalDate.now().plusDays(1)) }
        .max(Comparator.comparing(Price::effectiveDate))
            //TODO: .orElseThrow { PriceNotFoundException(id) }
        .orElseThrow()
}