package com.kotlin.productservice.repository

import com.kotlin.productservice.model.entity.Property
import org.springframework.data.jpa.repository.JpaRepository

import org.springframework.stereotype.Repository

@Repository
interface PropertyRepository : JpaRepository<Property, Long>{
}