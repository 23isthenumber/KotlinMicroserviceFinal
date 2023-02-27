package com.kotlin.productservice.model.entity

import com.kotlin.productservice.model.enums.ProductStatus
import jakarta.persistence.*

@Entity
class Product(
    var name: String,
    var description: String,
    @Enumerated(EnumType.STRING)
    var status: ProductStatus,
    @OneToMany(cascade = [CascadeType.ALL])
    var price: MutableList<Price>,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var property: MutableList<Property>,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)