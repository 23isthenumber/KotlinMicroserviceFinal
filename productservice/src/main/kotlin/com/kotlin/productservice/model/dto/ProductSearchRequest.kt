package com.kotlin.productservice.model.dto

import com.kotlin.productservice.model.enums.ProductStatus

data class ProductSearchRequest(
    val name: String,
    val description: String,
    val productStatus: ProductStatus,
    val pageNumber: Int,
    val pageSize: Int
)

