package com.kotlin.productservice.repository

import com.kotlin.productservice.model.entity.Price
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PriceRepository : JpaRepository<Price, Long> {
}