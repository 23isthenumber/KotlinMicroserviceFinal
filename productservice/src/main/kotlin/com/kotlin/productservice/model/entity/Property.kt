package com.kotlin.productservice.model.entity

import jakarta.persistence.*

@Entity
class Property(
    var property: String,
    var propertyValue: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
)

//TODO: many things are now not null. Check in the future